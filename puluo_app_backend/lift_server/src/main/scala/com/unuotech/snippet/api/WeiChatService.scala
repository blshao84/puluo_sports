/*package com.puluo.snippet.api

import net.liftweb.http.rest.RestHelper
import net.liftweb.util.Helpers._
import net.liftweb.http._
import com.puluo.util.UserHelper
import net.liftweb.common.Full
import net.liftweb.common.Loggable
import com.puluo.model.user.Customer
import net.liftweb.db.DB
import net.liftweb.db.DefaultConnectionIdentifier
import net.liftweb.json.Serialization
import net.liftweb.json.DefaultFormats
import net.liftweb.json._
import com.puluo.model.user.CustomerAddressDetail
import com.puluo.model.user.CustomerAddress
import com.puluo.model.sales.BehaviourTrackingValue
import com.puluo.model.sales.BehaviourTrackingRecord
import java.util.Date
import org.joda.time.LocalDate
import com.puluo.config.Configurations
import org.apache.commons.codec.digest.DigestUtils
import net.liftweb.util.PCDataXmlParser
import scala.xml.NodeSeq
import com.puluo.weichat.WeiChatUtil
import com.puluo.model.entity.RetailerWeiChatToken
import com.puluo.weichat.ReplyService
import org.dom4j.io.SAXReader
import scala.collection.JavaConversions._
import org.dom4j.Element
import com.puluo.model.entity.UserWeiChatBinding
import net.liftweb.mapper._
import scala.util.Random
import com.puluo.util.JuheSMSClient
import com.puluo.util.StringUtil
import com.puluo.model.user.Order
import com.puluo.model.sales.Product
import com.puluo.util.DateUtil
import net.liftweb.mapper.MaxRows
import net.liftweb.mapper.OrderBy
import net.liftweb.mapper.Descending
import com.puluo.util.ImageUtil
import com.puluo.model.UserImage
import com.puluo.model.ImageUsageType
import com.puluo.model.sales.OneClickOrder
import com.puluo.model.sales.SalesRecord
import com.puluo.model.user.OrderStatus
import com.puluo.model.user.OrderStateMachine
import com.puluo.model.user.OrderSuccessfulPayment
import com.puluo.snippet.update.OrderPaymentManualConfirmationEvent
import net.liftweb.util.FieldError
import com.puluo.config.WeiChatButton
import com.puluo.config.ButtonKeyType
import com.puluo.model.sales.StoreEvent
import com.puluo.model.PromotionImage
import com.puluo.model.PromotionType
import com.puluo.snippet.util.StockSearchUtil
import com.puluo.model.sales.BonusRecord
import java.util.UUID
import com.puluo.model.UserSubscription
import com.puluo.model.user.WeiChatCheckIn
import com.puluo.model.sales.Stock
import com.puluo.model.user.CustomerPoints
import com.puluo.weichat.CheckInCalculator
import org.joda.time.LocalDateTime

trait WeiChatReplyMessage {
  def params: Map[String, String]
  def toUserName = params("FromUserName")
  def fromUserName = params("ToUserName")
  def createTime = params("CreateTime")
  def xmlResponse: XmlResponse
}

case class Bonus(orderValue: Double, comfirmedValue: Double, bonusValue: Double)

case class WeiChatTextMessage(params: Map[String, String], val textContent: String) extends WeiChatReplyMessage with Loggable {
  val msgType = "text"
  def xmlResponse = {
    val xml = <xml>
                <ToUserName>{ toUserName }</ToUserName>
                <FromUserName>{ fromUserName }</FromUserName>
                <CreateTime>{ createTime }</CreateTime>
                <MsgType>{ msgType }</MsgType>
                <Content>{ textContent }</Content>
              </xml>
    logger.info("回复给微信的response：\n%s".format(xml))
    XmlResponse(xml)
  }
}

case class WeiChatNewsMessage(params: Map[String, String],
  val articles: Seq[WeiChatArticleMessage]) extends WeiChatReplyMessage with Loggable {
  val msgType = "news"
  def articleCount = articles.size
  def xmlResponse = {
    val xml = <xml>
                <ToUserName>{ toUserName }</ToUserName>
                <FromUserName>{ fromUserName }</FromUserName>
                <CreateTime>{ createTime }</CreateTime>
                <MsgType>{ msgType }</MsgType>
                <ArticleCount>{ articleCount }</ArticleCount>
                <Articles>{ articles.map(_.xmlResponse) }</Articles>
              </xml>
    logger.info("回复给微信的response：\n%s".format(xml))
    XmlResponse(xml)
  }
}

case class WeiChatArticleMessage(val title: String, val description: String, imgUrl: String, pageUrl: String) {
  def xmlResponse =
    <item>
      <Title>{ title }</Title>
      <Description>{ description }</Description>
      <PicUrl>{ imgUrl }</PicUrl>
      <Url>{ pageUrl }</Url>
    </item>
}

object WeiChatService extends RestHelper with OrderStateMachine with Loggable {
  object LotteryPool{
    lazy val lotteryPool = Seq(10477L,3037L,3039L).map(Stock.findByKey(_)).flatten
    
    def get:Stock = {
      val random = Random.nextInt
      val size = lotteryPool.size
      val index = random % size
      lotteryPool(index)
    }
  }
  
  implicit def replyMessageToResponse(msg: WeiChatReplyMessage): XmlResponse = msg.xmlResponse

  def eventMap = Map(
    OrderSuccessfulPayment -> Set(OrderPaymentManualConfirmationEvent))

  serve {
    case "sns" :: "weichat" :: "create" :: Nil Get _ => createButton
    case "sns" :: "weichat" :: Nil Get _ => processReq
    case "sns" :: "weichat" :: Nil Post _ => processReq
  }

  def createButton = {
    if (S.loggedIn_? && Customer.currentUser.get.superUser.get) {
      val button = WeiChatButton.buttons.toJsonString
      val token = RetailerWeiChatToken.getOrUpdate
      logger.info("向微信发送新建button的请求:\n" + button)
      val success = WeiChatUtil.createButton(token, button)
      PlainTextResponse("success=" + success)
    } else PlainTextResponse("您没登陆或者没有权限")
  }

  def processReq = {
    (S.param("signature"), S.param("timestamp"), S.param("nonce"), S.param("echostr")) match {
      case (Full(sig), Full(timestamp), Full(nonce), Full(echostr)) => verify(sig, timestamp, nonce, echostr)
      case _ => {
        val params = parseXml(S.request.get)
        (params.get("ToUserName"), params.get("FromUserName"), params.get("CreateTime"), params.get("MsgType")) match {
          case (Some(toUser), Some(fromUser), Some(creatAt), Some(msgType)) => {
            msgType match {
              case "text" => processTextReq(params)
              case "image" => processImageReq(params)
              case "event" => processButtonReq(params)
              case _ => {
                logger.error("暂时不支持微信msgType=%s的请求".format(msgType))
                PlainTextResponse("error")
              }
            }
          }
          case _ => {
            logger.error("无法获取微信发送的验证数据")
            PlainTextResponse("error")
          }
        }
      }
    }
  }

  def processButtonReq(params: Map[String, String]): LiftResponse = {
    (params.get("Event"), params.get("EventKey")) match {
      case (Some(event), Some(key)) if event == "CLICK" =>
        ButtonKeyType.withName(key) match {
          case ButtonKeyType.CHECKIN => createStoreCheckin(params)
          case ButtonKeyType.LOTTERY => createStoreLottery(params)
          case ButtonKeyType.STORE_EVENT => createStoreEvent(params)
          case ButtonKeyType.STORE_PROMOTION => createStorePromotion(params)
          case ButtonKeyType.CUSTOMER_SERVICE => WeiChatTextMessage(params,
            """服务电话：
              新玛特总服务台电话：6296316
              新玛特售后投诉电话：6296315 6296167
              新玛特各楼层办公咨询电话：
              超市——6296095
              名品——6296269（珠宝、黄铂金、名表、化妆品请联系我~）
              靴鞋——6296210
              男装——6296278
              女装——6296220
              服饰——6296210（羊绒羊毛、女士箱包、内衣请联系我~）
              家电——6296280
              家居——6296299
              运动——6296298
              餐饮娱乐部——6296418
              会员及团购中心——6296488""")
          case ButtonKeyType.FLOOR_PLAN => WeiChatTextMessage(params,
            """楼层信息：
              1F 国际名品广场 
              2F 流行服饰广场
              3F 家居休闲生活馆
              4F 餐饮娱乐皮草广场""")
          case ButtonKeyType.OPENING_TIME => WeiChatTextMessage(params,
            """新玛特营业时间：09:00——21:00注：逢周五、六、日超市提前1小时开业""")
        }
      case (Some(event), _) if event == "VIEW" => PlainTextResponse("ignore")
      case _ =>
        logger.error("无法获取微信发送的验证数据")
        PlainTextResponse("error")

    }
  }

  def createStoreCheckin(params: Map[String, String]): XmlResponse = {
    verifyBinding(params) { binding: UserWeiChatBinding =>
      val date = LocalDate.now().toDate
      val retailer = Configurations.currentRetailer.id.get
      val checkin = WeiChatCheckIn.findLatest(binding.user.get, retailer)
      val allowCheckin = checkin.map(ck => {
        val now = LocalDateTime.now.toDate
        val last = ck.createdAt.get
        val diffInHour = DateUtil.diffInHours(now, last)
        logger.info("上次最近checkin时间为：%s,现在:%s,相差小时:%s".format(DateUtil.formatDateTime(last), DateUtil.formatDateTime(now), diffInHour))
        diffInHour > Configurations.weiChatCheckinIntervalInHour
      }).getOrElse(true)
      if (allowCheckin) {
        WeiChatCheckIn.create.date(date).retailer(retailer).user(binding.user.get).saveMe
        logger.info("保存%s的签到".format(DateUtil.formatSimpleDate(date)))
        val points = CustomerPoints.findByUserAndRetailer(binding.user.get, retailer).getOrElse {
          CustomerPoints.create.customer(binding.user.get).points(0).retailer(retailer).saveMe
        }
        logger.info("用户%s当前积分为%s".format(binding.user.get, points.points.get))
        val historicalCheckins = WeiChatCheckIn.findAll(By(WeiChatCheckIn.user, binding.user.get)).sortBy(_.date.get)
        logger.info("用户%s的签到历史为:\n%s".format(binding.user.get,
          historicalCheckins.map(c => DateUtil.formatSimpleDate(c.date.get)).mkString("\n")))
        val checkinIntervals = historicalCheckins.zipWithIndex.map {
          case (checkin, index) =>
            if (index == historicalCheckins.size - 1) None else {
              val nextCheckin = historicalCheckins(index + 1)
              Some(DateUtil.diffInDays(nextCheckin.date.get, checkin.date.get))
            }
        }.flatten
        logger.info("用户%s的签到间隔为:%s".format(binding.user.get, checkinIntervals))
        val consecutiveCheckins = 1 + checkinIntervals.size - (checkinIntervals.reverse.dropWhile(_ == 1).size)
        val pts = Configurations.weiChatCheckinPoints * CheckInCalculator.calculateCustomerPoints(consecutiveCheckins)
        val newPoints = points.addPoints(pts).saveMe
        val msg = "您已经连续签到%s天，本次签到获得%s个积分，目前您已累计%s积分".format(consecutiveCheckins, pts, StringUtil.prettyDouble(newPoints.points.get,2))
        logger.info(msg)
        WeiChatTextMessage(params, msg)
      } else {
        val points = CustomerPoints.findByUserAndRetailer(binding.user.get, retailer).getOrElse {
          CustomerPoints.create.customer(binding.user.get).points(0).retailer(retailer).saveMe
        }
        WeiChatTextMessage(params, "亲，您刚才已经签到过了，当前积分为%s,8小时只能签到一次呦，晚点再过来吧".format(StringUtil.prettyDouble(points.points.get,2)))
      }
    }
  }

  def createStoreLottery(params: Map[String, String]): XmlResponse = {
    verifyBinding(params) { binding: UserWeiChatBinding =>
      val date = DateUtil.formatSimpleDate(LocalDate.now().toDate)
      val retailer = Configurations.currentRetailer.id.get
      val pointsOpt = CustomerPoints.findByUserAndRetailer(binding.user.get, retailer)
      val pts = pointsOpt.map(_.points.get).getOrElse(0.0)
      if (pts >= Configurations.weiChatLotteryCost) {
        val points = pointsOpt.get
        val newPoints = points.addPoints(-Configurations.weiChatLotteryCost).saveMe
        logger.info("积分%s足够进行该次抽奖".format(pts))
        //TODO: customized lottery rate
        val win = ((Random.nextInt % 2) == 1)
        if (win) {
          val freeStock = LotteryPool.get//Stock.findByKey(67).get
          val articles = Seq(createArticalMessage(freeStock))
          logger.info("中奖奖品id=%s,扣除积分后剩余%s".format(freeStock.id, newPoints.points.get))
          WeiChatNewsMessage(params, articles)
        } else {
          WeiChatTextMessage(params, """哎呀，您这次运气不佳，当前积分%s。去<a href="www.dqxmt.com">网上商城</a>逛下再来试试吧""".format(StringUtil.prettyDouble(newPoints.points.get,2)))
        }
      } else WeiChatTextMessage(params, "您的积分不足了，亲！签到，登陆网上商城都可以赚积分的，赶快行动吧，然后明天再来试试您的手气！")
    }
  }

  def createStoreEvent(params: Map[String, String]): XmlResponse = {
    verifyBinding(params) { binding: UserWeiChatBinding =>
      val events = StoreEvent.findOnDisplayEventsAndAnnouncement(binding.retailer.get).take(3)
      if (events.isEmpty) WeiChatTextMessage(params, "敬请期待")
      else {
        val articles = events.map(e => {
          val img = Configurations.uploadImageLoc + e.uuid.toString
          val link = Configurations.webServerURL + "/footers/promotion_one?id=" + e.id.get
          WeiChatArticleMessage(e.title.toString, e.desc.toString, img, link)
        })
        WeiChatNewsMessage(params, articles)
      }
    }
  }

  def createStorePromotion(params: Map[String, String]): XmlResponse = {
    val promotions = PromotionImage.findOnDisplayPromotions(PromotionType.Pos1).
      sortBy(_.updatedAt.get).reverse.
      map(p => (p, PromotionImage.findStockFromLink(p.promotionLink.get))).filter(_._2.isDefined).take(3)
    if (promotions.isEmpty) WeiChatTextMessage(params, "敬请期待")
    else {
      val articles = promotions.map {
        case (img, s) =>
          val stock = s.get
          val imgLink = Configurations.uploadImageLoc + ImageUtil.renameToResizedImage(img.imageUUID, "medium")
          val product = stock.product.obj.get
          val code = UUID.randomUUID().toString()
          val stockLink = Configurations.webServerURL + "/stock_mobile?id=" + stock.id.get + "&wck=" + code
          WeiChatArticleMessage(product.name.toString, product.description.toString, imgLink, stockLink)
      }
      WeiChatNewsMessage(params, articles)
    }
  }

  def processImageReq(params: Map[String, String]): LiftResponse = {
    (params.get("FromUserName"), params.get("PicUrl"), params.get("MediaId")) match {
      case (Some(fromUsr), Some(picUrl), Some(mediaId)) => {
        val binding = UserWeiChatBinding.find(By(UserWeiChatBinding.openId, fromUsr),
          By(UserWeiChatBinding.retailer, Configurations.currentRetailer.id.get))
        if (binding.isDefined && binding.get.verified.get) {
          val token = RetailerWeiChatToken.getOrUpdate
          val user = binding.get.user.obj.get
          val url = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID".
            replace("ACCESS_TOKEN", token).replace("MEDIA_ID", mediaId)
          //FIXME: should use url
          val img = ImageUtil.saveImageFromUrl(picUrl)
          val imgName = params("MsgId") + "." + img.mimeType
          if (false) { //user.isAdminRetailer || user.isAdminSupplier) {
            UserImage.create.name(imgName).user(user).uuid(img.uuid).usage(ImageUsageType.StockDisplay).saveMe
            WeiChatTextMessage(params, "成功上传图片")
          } else {
            UserSubscription.create.name(imgName).user(user).uuid(img.uuid).saveMe
            WeiChatTextMessage(params, "恭喜您成功订阅该商品，一旦打折的话我们会第一时间短信通知您哦！")
          }

        } else WeiChatTextMessage(params, "您的微信账号还没有同网上商城绑定，发送'bd+手机号'进行绑定")
      }
      case _ => {
        logger.error("微信‘图片’请求中没有包含fromUsr,picUrl,mediaId信息")
        logger.error(params)
        PlainTextResponse("error")
      }
    }
  }
  def processTextReq(params: Map[String, String]): LiftResponse = {
    params.get("Content") match {
      case Some(content) => {
        val (hd :: tl) = content.split('+').toList
        (hd.toLowerCase(), tl.toArray) match {
          *//**
           * 一般用户功能
           *//*
          case ("绑定", Array(mobile)) => bindWeiChatStep1(params, mobile.trim)
          case ("bd", Array(mobile)) => bindWeiChatStep1(params, mobile.trim)
          case ("绑定", Array(mobile, verifyCode)) => bindWeiChatStep2(params, mobile.trim, verifyCode.trim)
          case ("bd", Array(mobile, verifyCode)) => bindWeiChatStep2(params, mobile.trim, verifyCode.trim)
          case ("jf", Array()) => getCustomerPoints(params)
          case ("积分", Array()) => getCustomerPoints(params)
          case ("o", Array()) => getAllOrderDetail(params)
          case ("订单", Array()) => getAllOrderDetail(params)
          case ("o", Array(id)) => getOrderDetail(params, id.trim)
          case ("订单", Array(id)) => getOrderDetail(params, id.trim)
          case ("pw", Array(password)) => updatePassword(params, password.trim)
          case ("密码", Array(password)) => updatePassword(params, password.trim)
          *//**
           * 商场管理员功能
           *//*
          case ("领奖", Array()) => cashOutBonus(params)
          case ("领奖", Array(bonusRecordID)) => confirmBonus(params, bonusRecordID)
          case ("jl", Array(orderID, authCode)) => saveSalesRecord(params, orderID.trim, authCode.trim)
          case ("奖励", Array(orderID, authCode)) => saveSalesRecord(params, orderID.trim, authCode.trim)
          case ("jl", Array()) => showSalesRecord(params)
          case ("奖励", Array()) => showSalesRecord(params)
          case ("co", Array(id)) => comfirmOrder(params, id.trim)
          case ("完成", Array(id)) => comfirmOrder(params, id.trim)
          case ("h", _) => helpResponse(params)
          case ("帮助", _) => helpResponse(params)
          case _ => stockSearch(params, content)
        }
      }
      case None => {
        logger.error("微信‘文本消息’请求中没有包含content信息")
        PlainTextResponse("error")
      }
    }
  }

  def confirmBonus(params: Map[String, String], bonusID: String) = {
    verifyBinding(params) { binding: UserWeiChatBinding =>
      val user = binding.user.obj.get
      if (user.isAdminRetailer) {
        BonusRecord.find(By(BonusRecord.user, user.id.get), By(BonusRecord.code, bonusID)) match {
          case Full(bonus) => {
            if (!bonus.isCashedOut.get) {
              bonus.isCashedOut(true).saveMe
              WeiChatTextMessage(params, "成功确认用户%s代码为%s的奖金记录".format(user.id.toString, bonus.code))
            } else WeiChatTextMessage(params, "用户%s已经领取了代码为%s的奖金记录".format(user.id.toString, bonus.code))
          }
          case _ => WeiChatTextMessage(params, "没有找到用户%s的领奖代码为%s的奖金记录".format(user.id.toString, bonusID))
        }
      } else WeiChatTextMessage(params, "您还没有管理员权限进行此操作")
    }
  }
  def cashOutBonus(params: Map[String, String]) = {
    verifyBinding(params) { binding: UserWeiChatBinding =>
      val user = binding.user.obj.get
      if (user.isAdminSupplier || user.isAdminRetailer) {
        val newFromDate = BonusRecord.lastBonusDate(user.id.get)
        val newToDate = DateUtil.today
        val records = SalesRecord.findRecord(user.id.get, newFromDate, newFromDate)
        if (records.isEmpty) WeiChatTextMessage(params, "您在%s至%s之间没有新的销售记录".
          format(DateUtil.formatSimpleDate(newFromDate), DateUtil.formatSimpleDate(newToDate)))
        else {
          val orders = records.map(_.order.get)
          val bonus = calculateBonus(records)
          val bonusRecord = BonusRecord.createWithOrders(orders).user(user).
            fromDate(newFromDate).toDate(newToDate).isCashedOut(true).bonus(bonus.bonusValue).saveMe
          WeiChatTextMessage(params, "已为您生成从%s至%s的奖金记录，总计%s元，领奖密码%s".format(
            DateUtil.formatSimpleDate(newFromDate), DateUtil.formatSimpleDate(newToDate), bonus.bonusValue, bonusRecord.code))
        }
      } else WeiChatTextMessage(params, "无法为您计算奖金因为您还没有管理员权限")
    }
  }
  def stockSearch(params: Map[String, String], keyword: String) = {
    StockSearchUtil.searchStock(keyword).toList match {
      case Nil => defaultResponse(params)
      case hd :: tl => {
        val stocks = hd :: tl.take(4)
        val resp = stocks.map(s => {
          createArticalMessage(s)
        })
        WeiChatNewsMessage(params, resp)
      }
    }
  }

  def updatePassword(params: Map[String, String], password: String) = {
    verifyBinding(params) { binding: UserWeiChatBinding =>
      {
        val user = binding.user.obj.get
        val updatedUser = user.password(password)

        Customer.doValidate(updatedUser)(() => { //success
          updatedUser.saveMe
          WeiChatTextMessage(params, "成功更新您网上商城的密码！新密码为%s，请妥善保管".format(password))
        }, errors => { //failure
          val errorMsg = "无法更新密码，原因可能为:\n%s".format(errors.map(_.msg).mkString("\n"))
          WeiChatTextMessage(params, errorMsg)
        })
      }
    }
  }
  def saveSalesRecord(params: Map[String, String], orderID: String, authCode: String) = {
    verifyBinding(params) { binding: UserWeiChatBinding =>
      val user = binding.user.get
      verifyOrder(params, orderID, false) { order =>
        OneClickOrder.find(By(OneClickOrder.order, order.id.get)) match {
          case Full(oneClickOrder) => {
            if (oneClickOrder.authCode.toString == authCode) {
              SalesRecord.find(By(SalesRecord.order, order.id.get)) match {
                case Full(r) => {
                  val userName = if (r.sales.get == user) "您" else "其他用户"
                  WeiChatTextMessage(params, "该订单已经被统计到%s的账户中，请与商场联系".format(userName))
                }
                case _ => {
                  SalesRecord.create.sales(user.toString).order(order.id.get).saveMe
                  val msg = salesRecordMsg(user, params)
                  WeiChatTextMessage(params, "成功提交您的交易记录，%s".format(msg))
                }
              }
            } else WeiChatTextMessage(params, "您提交的订单验证码错误")
          }
          case _ => WeiChatTextMessage(params, "您只能提交快速预定的订单")
        }
      }
    }
  }
  def showSalesRecord(params: Map[String, String]) = {
    verifyBinding(params) { binding: UserWeiChatBinding =>
      val user = binding.user.get
      val msg = salesRecordMsg(user, params)
      WeiChatTextMessage(params, msg)

    }
  }

  private def salesRecordMsg(user: Long, params: Map[String, String]) = {
    val statStart = BonusRecord.lastBonusDate(user)
    val sales = SalesRecord.findAll(By(SalesRecord.sales, user.toString), By_>=(SalesRecord.createdAt, statStart))
    logger.info("找到用户%s从%s开始的%s条SalesRecord".format(user.toString, DateUtil.formatSimpleDate(statStart), sales.size))
    val bonus = calculateBonus(sales)
    "其中已完成%s元,本月累计奖金%s元".format(bonus.comfirmedValue, bonus.bonusValue)
  }

  private def calculateBonus(salesRecords: Seq[SalesRecord]) = {
    val os: Seq[Long] = salesRecords.map(_.order.get)
    val allOrders = Order.findAll(ByList(Order.id, os)).toSeq
    val sales = allOrders.map(_.value).sum
    val completeOrders = allOrders.filter(o => OrderStatus.paidStatus.contains(o.status.get))
    logger.info("所有订单:%s,已完成订单:%s".format(allOrders.size, completeOrders.size))
    val completeSales = completeOrders.map(_.value).sum
    val bonus = BonusCalculator.bonus(completeOrders)
    Bonus(sales, completeSales, bonus)
  }
  def getCustomerPoints(params: Map[String, String]) = {
    verifyBinding(params) { binding: UserWeiChatBinding =>
      val retailer = Configurations.currentRetailer.id.get
      val points = CustomerPoints.findByUserAndRetailer(binding.user.get, retailer).map(_.points.get).getOrElse(0.0)
      if (points > 0.0)
        WeiChatTextMessage(params, "您当前积分为:%s".format(points))
      else
        WeiChatTextMessage(params, "您还没有累计任何积分，不过您可以通过微信签到、登陆新玛特网站并下单购物来赚取积分呦！赶快行动吧！")
    }
  }
  def getAllOrderDetail(params: Map[String, String]) = {
    verifyBinding(params) { binding: UserWeiChatBinding =>
      {
        val user = binding.user.get
        val orders = Order.findAll(By(Order.customer, user), MaxRows(3), OrderBy(Order.createdAt, Descending))
        if (orders.isEmpty) WeiChatTextMessage(params, """您暂时没有任何订单，赶快登陆<a href="www.dqxmt.com">网上商城</a>那里有很多惊喜等着您哟！""")
        else {
          val userId = Customer.findByKey(user).map(_.email.toString).getOrElse("未找到")
          val orderInfo = orders.zipWithIndex.map(tmp => {
            val (order, index) = tmp
            val prodName = Product.findByKey(order.product.get).get.name.toString
            "%s.\n\t商品名:%s\n\t尺寸:%s\n\t颜色:%s\n\t数量:%s\n\t价格:%s\n\t时间:%s\n\t状态:%s".format(
              index + 1, prodName, order.size.toString, order.color.toString, order.quantity.toString,
              StringUtil.prettyDouble(order.value, 2), DateUtil.formatSimpleDate(order.updatedAt.get), order.getStatus)
          }).mkString("\n")
          WeiChatTextMessage(params, orderInfo)
        }
      }
    }
  }

  def getOrderDetail(params: Map[String, String], id: String) = {
    verifyOrder(params, id, true) { order: Order =>
      {
        val orderInfo = createOrderInfo(order)
        WeiChatTextMessage(params, orderInfo)
      }
    }
  }

  def comfirmOrder(params: Map[String, String], id: String) = {
    verifyOrder(params, id, false) { order: Order =>
      {
        nextStatesOption(order).find(_ == OrderPaymentManualConfirmationEvent) match {
          case Some(event) => {
            event.update(order)
            val orderInfo = "成功更新订单状态，该订单最新信息:\n%s".format(createOrderInfo(order.reload))
            WeiChatTextMessage(params, orderInfo)
          }
          case _ => WeiChatTextMessage(params, "该订单当前状态为:%s,无法完成确认".format(order.status.get))
        }

      }
    }
  }

  def helpResponse(params: Map[String, String]) = {
    val helpMsg = """
      欢迎使用网上商城微信客服，您可以通过以下方式让我们为您服务：
      1. 账号绑定：发送"绑定+电话"或者"bd+电话"，例如：'绑定+18648888888'。只有将您网上商城账号与微信绑定后您才可以其他微信服务
      2. 订单详细信息查询：
    		2.1 单个订单记录:发送"订单+订单号"或者字母"o+订单号",例如：'订单+123'。
    		2.2 最近3条记录:发送字母"o"或者"订单"，例如:'o'。
      3. 修改密码:发送"密码+新密码"或者"pw+新密码",例如:'密码+123456'
      4. 帮助：发送"帮助"或者"h"。
      """
    WeiChatTextMessage(params, helpMsg)
  }

  def quickFixMessage(params: Map[String, String]) = {
    val msg = """
      http://diditaxi.com.cn/activity/hongbao/op_redpacket/home?channel=820d795260f2908415b70d17a8f10e5a这个链接地址即可，动作快一些，红包等着你!
      """
    WeiChatTextMessage(params, msg)
  }
  def bindWeiChatStep1(params: Map[String, String], mobile: String): XmlResponse = {
    def doBind(binding: UserWeiChatBinding) = {
      val random = scala.math.abs(Random.nextInt % 1000000)
      val code = if (random < 100000) 999999 - random else random
      val success = JuheSMSClient.sendVerificationCode(mobile, code.toString)
      if (success.error_code == 0) {
        binding.authCode(code.toString).mobile(mobile).verified(false).
          openId(params("FromUserName")).retailer(Configurations.currentRetailer.id.get).saveMe
        WeiChatTextMessage(params, "成功发送验证码。发送微信‘bd+%s+您手机收到的验证码’完成最后绑定".format(mobile))
      } else {
        WeiChatTextMessage(params, "我们无法向您的电话%s发送验证码，请稍后再试".format(mobile))
      }
    }

    val fromUser = params("FromUserName")
    UserWeiChatBinding.find(By(UserWeiChatBinding.mobile, mobile), By(UserWeiChatBinding.openId, fromUser),
      By(UserWeiChatBinding.retailer, Configurations.currentRetailer.id.get)) match {
        case Full(binding) if (binding.verified == true) => WeiChatTextMessage(params, "您的电话%s已经与该微信公众号绑定".format(mobile))
        case Full(binding) if (binding.verified == false) => doBind(binding)
        case _ => doBind(UserWeiChatBinding.create)
      }
  }

  def bindWeiChatStep2(params: Map[String, String], mobile: String, verifyCode: String): XmlResponse = {
    def finishBinding(binding: UserWeiChatBinding, user: Customer, msg: String): XmlResponse = {
      binding.user(user).verified(true).saveMe
      user.validated(true).saveMe
      WeiChatTextMessage(params, msg)
    }
    UserWeiChatBinding.find(By(UserWeiChatBinding.mobile, mobile),
      By(UserWeiChatBinding.retailer, Configurations.currentRetailer.id.get)) match {
        case Full(binding) if (binding.verified == true) => WeiChatTextMessage(params, "您的电话%s已经与该微信公众号绑定".format(mobile))
        case Full(binding) if (binding.verified == false) => {
          if (verifyCode == binding.authCode.get) { //TODO:检查openID？也就是说我们应该允许其他用户帮助绑定？
            DB.use(DefaultConnectionIdentifier) { connection =>
              Customer.find(By(Customer.email, mobile)) match {
                case Full(user) => finishBinding(binding, user, "恭喜，您的微信已经与网上商城(账号:%s)绑定，现在就发送微信'h'看看我们为您提供了哪些功能".format(user.email.toString))
                case _ => {
                  val user = Customer.create.email(mobile).password(verifyCode).validated(true).saveMe
                  finishBinding(binding, user, ("您的电话还没有在网上商城注册过，不过我们已经为您创建了网上商城账号%s以及初始密码%s，" +
                    "并且将该账号与您的微信绑定。现在就发送微信'h'看看我们为您提供了哪些功能").format(mobile, verifyCode))
                }
              }
            }
          } else WeiChatTextMessage(params, "您发送的验证码错误，请重新验证或者发送微信‘bd+%s'，我们将重新向您发送验证码".format(mobile))
        }
        case _ => bindWeiChatStep1(params, mobile)
      }
  }

  def defaultResponse(params: Map[String, String]) = WeiChatTextMessage(params,
    """您的消息我们已经收到，只是暂时不太明白您的要求，发送'h'看看我们现在都有哪些功能。
      与此同时我们正在玩命的将<a href="www.dqxmt.com">网上商城</a>集成到微信中，希望您持续关注我们！""")

  def verify(sig: String, timestamp: String, nonce: String, echostr: String) = {
    val tmpArr = Seq(Configurations.weichatToken, timestamp, nonce).sorted
    val tmpStr = tmpArr.mkString
    val tmpStrHash = DigestUtils.shaHex(tmpStr)
    if (tmpStrHash == sig) {
      PlainTextResponse(echostr)
    } else {
      logger.error("server签名与weichat不符:signature=%s,timestamp=%s,nonce=%s,tmpArr=%s,tmpStr=%s,tmpHash=%s".
        format(sig, timestamp, nonce, tmpArr, tmpStr, tmpStrHash))
      PlainTextResponse("error")
    }
  }

  private def parseXml(req: Req): Map[String, String] = {
    val inputStream = req.rawInputStream.getOrElse(throw new Exception("微信发送的请求不包含xml数据"))
    // 读取输入流
    val reader = new SAXReader()
    val document = reader.read(inputStream)
    inputStream.close()
    // 得到xml根元素
    val root = document.getRootElement()
    val elementList = root.elements
    // 得到根元素的所有子节点
    val result = elementList.map(_ match {
      case e: Element => Some((e.getName(), e.getText()))
      case e: Any => {
        logger.error("解析微信数据错误，expect dom4j.Element, but find %s".format(e.getClass))
        None
      }
    }).flatten
    logger.info("解析微信数据结果:\n" + result.mkString("\n"))
    result.toMap
  }

  def checkOrderViewEntitlement(order: Order, user: Customer, mobile: String): Boolean = {
    if (user.isAdminRetailer) true else {
      val brands = user.adminBrandIds
      logger.info("用户拥有权限的品牌:%s,订单品牌:%s".format(brands, order.brand.toString))
      if (brands.contains(order.brand.get)) true
      else if (user.id.get == order.customer.get) true
      else {
        val mobilesFromUsers = order.customer.obj.map(u => {
          u.mobile.toString :: u.addresses.map(_.mobile.toString).toList
        }).getOrElse(List.empty)
        logger.info("订单%s的用户%s曾经使用过的电话:%s".format(order.id, order.customer, mobilesFromUsers))
        mobilesFromUsers.contains(mobile)
      }
    }
  }
  def checkOrderConfirmationEntitlement(order: Order, user: Customer, mobile: String): Boolean = {
    if (user.isAdminRetailer) true else {
      val brands = user.adminBrandIds
      logger.info("用户拥有权限的品牌:%s,订单品牌:%s".format(brands, order.brand.toString))
      brands.contains(order.brand.get)
    }
  }

  private def verifyBinding(params: Map[String, String])(f: UserWeiChatBinding => XmlResponse): XmlResponse = {
    val fromUser = params("FromUserName")
    logger.info("通过fromUserName=%s查询UserWeiChatBinding".format(fromUser))
    UserWeiChatBinding.find(By(UserWeiChatBinding.openId, fromUser)) match {
      case Full(binding) if (binding.verified.get == true) => f(binding)
      case _ => WeiChatTextMessage(params, "您的微信账号还没有同网上商城绑定，发送'bd+手机号'进行绑定")
    }
  }

  private def verifyOrder(params: Map[String, String], id: String, checkViewPermission: Boolean)(process: Order => XmlResponse) = {
    if (StringUtil.isLong(id)) {
      val oid = id.toLong
      verifyBinding(params) { binding: UserWeiChatBinding =>
        {
          val user = binding.user.obj.get
          Order.findByKey(oid) match {
            case Full(order) => {
              val status =
                if (checkViewPermission)
                  checkOrderViewEntitlement(order, user, binding.mobile.toString)
                else checkOrderConfirmationEntitlement(order, user, binding.mobile.toString)
              status match {
                case true => process(order)
                case false => WeiChatTextMessage(params, "您没有查看该订单信息的权限")
              }
            }
            case _ => WeiChatTextMessage(params, "您查询的订单不存在")
          }
        }
      }
    } else WeiChatTextMessage(params, "订单号必须是数字").xmlResponse
  }

  private def createOrderInfo(order: Order) = {
    val prodName = Product.findByKey(order.product.get).get.name.toString
    val userId = Customer.findByKey(order.customer.obj.map(_.id.get).getOrElse(0)).map(_.email.toString).getOrElse("未找到")
    "下单用户:%s\n商品名:%s\n尺寸:%s\n颜色:%s\n数量:%s\n价格:%s\n时间:%s\n状态:%s".format(
      userId, prodName, order.size.toString, order.color.toString, order.quantity.toString,
      StringUtil.prettyDouble(order.value, 2), DateUtil.formatSimpleDate(order.updatedAt.get), order.getStatus)
  }

  private def createArticalMessage(s: Stock) = {
    val name = new StringBuilder().append(s.productName).
      append("(").append(s.productISSN).append(")").toString
    val code = UUID.randomUUID().toString()
    val prod = Product.findByKey(s.product.get).get
    val uuid = if(prod.isSpecial) "&uuid="+s.uuid.toString else ""
    val link = Configurations.webServerURL + "stock_mobile?id=" + s.id.get + "&wck=" + code + uuid
    WeiChatArticleMessage(name, "", "", link)
  }
}
*/