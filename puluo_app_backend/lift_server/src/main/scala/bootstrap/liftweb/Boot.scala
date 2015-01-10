package bootstrap.liftweb

// framework imports
import net.liftweb.common._
import net.liftweb.util._
import net.liftweb.util.Helpers._
import net.liftweb.http._
import net.liftweb.sitemap._
import net.liftweb.sitemap.Loc._
import net.liftweb.mapper.{ DB, Schemifier, DefaultConnectionIdentifier, StandardDBVendor, MapperRules }
import com.unuotech.model.user.Customer
import com.unuotech.model.sales._
import net.liftweb.http.js.jquery.JQuery14Artifacts
import net.liftweb.http.js.jquery.JQueryArtifacts
import com.unuotech.snippet.api.OrderAPI
import com.unuotech.model.entity._
import com.unuotech.model.entity.LegalEntityEntitlement
import com.unuotech.model.entity.EntitlementRequest
import com.unuotech.model.entity.LegalEntityBrand
import com.unuotech.model.user.Order
import com.unuotech.model.StockImage
import com.unuotech.model.user._
import com.unuotech.model.LegalEntityDocument
import com.unuotech.model.ProductImage
import com.unuotech.model.BrandImage
import com.unuotech.model.PromotionImage
import com.unuotech.config.Configurations
import net.liftweb.db.DBLogEntry
import com.unuotech.snippet.proto.FileUpload
import com.unuotech.model.Feedback
import com.unuotech.config.Category
import com.unuotech.snippet.api.MessagingService
import com.unuotech.util.SMSClient
import net.liftweb.http.js.jquery.JQuery13Artifacts
import com.unuotech.snippet.common.CacheValue
import java.util.concurrent.atomic.AtomicInteger
import com.unuotech.model.LoginRecord
import com.unuotech.model.SignupRecord
import com.unuotech.model.LotteryRecord
import net.liftmodules.FoBo
import com.unuotech.model.UserImage
import com.unuotech.snippet.api.UploadManager
import com.unuotech.snippet.api.AliPaymentNotification
import net.liftweb.mapper.Mapper
import net.liftweb.mapper.MetaMapper
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException
import net.liftweb.http.js.JsCmds
import com.unuotech.snippet.api.ImportManager
import com.unuotech.model.FeedbackView
import com.unuotech.model.SizeClassification
import com.unuotech.model.FeedbackView
import com.unuotech.model.Feedback
import com.unuotech.snippet.api.UserAPI
import com.unuotech.snippet.api.UnionPaymentNotification
import com.unuotech.snippet.api.WeiChatService
import com.unuotech.snippet.api.UserAPI
import com.unuotech.model.UserSubscription
import com.unuotech.snippet.util.SMSAuthRecord

trait BootResult
case class DSIBootResult(val db: Option[Int]) extends BootResult
case class CustomFilter(test: Boolean) extends AnyLocParam

trait DSI {
  def createDSI: StandardDBVendor
  def setupDB: Int
}
object Boot {
  val sellerGroup = "seller"
  val retailerGroup = "retailer"
  val supplierGroup = "supplier"
  val testGroup = "test"
  val accountGroup = "account"
  val publicGroup = "public"
  val internalGroup = "internal"
  val adminGroup = "admin"

  def MustBeLoggedIn = Customer.loginFirst
}
class Boot extends DSI with Loggable {
  import Boot._

  lazy val dsi: StandardDBVendor = createDSI
  def createDSI = {
    val dsiUri = Configurations.dbUri + Configurations.dbServer + "/" + Configurations.dbName
    val dsiDriver = Configurations.dbDriver
    logger.info("dsiUri=%s,dsiDriver=%s".format(dsiUri, dsiDriver))
    new StandardDBVendor(dsiDriver, dsiUri, Configurations.dbUser, Configurations.dbPassword) {
      override def maxPoolSize = 500
      override def doNotExpandBeyond = 500
    }
  }

  def dbEntity = {
    /**** database settings ****/
    MapperRules.columnName = (_, name) => StringHelpers.snakify(name)
    MapperRules.tableName = (_, name) => StringHelpers.snakify(name)
    MapperRules.createForeignKeys_? = (_) => true

    List[List[MetaMapper[_]]](
      List(LotteryRecord, SignupRecord, LoginRecord, Feedback, CustomerAddress, EntitlementRequest, PageViewRecord),
      List(Customer),
      List(LegalEntity, CategoryEntity, PromotionImage, CustomerPoints, CashOutRecord),
      List(RetailerConfiguration, StoreEvent, Brand, Shop, StockRequest, RetailerMembershipCard,
        LegalEntityEntitlement, LegalEntityDocument),
      List(Contract, Product, CustomerMembership, LegalEntityBrand, BrandImage),
      List(ContractTransactionLog, Stock, ProductImage, UserImage),
      List(StockDetail, StockImage, CustomerFavoriate, Comment),
      List(PostOrder, Order),
      List(OrderTransactionLog, Invoice, ReturnOrderRequest, FeedbackView, OneClickOrder,
        BehaviourTrackingRecord, SizeClassification, RetailerWeiChatToken, UserWeiChatBinding,
        SalesRecord, PageTrackingRecord,BonusRecord,UserSubscription,SMSAuthRecord,WeiChatCheckIn)).flatten
  }

  def setupDB: Int = {
    logger.info("当前数据库连接:%s".format(DB.currentConnection.isDefined))

    val entities = dbEntity

    // handle JNDI not being avalible
    if (!DB.jndiJdbcConnAvailable_?) {
      DB.defineConnectionManager(DefaultConnectionIdentifier, dsi)
      // make sure cyote unloads database connections before shutting down
      LiftRules.unloadHooks.append(() => dsi.closeAllConnections_!())
    }
    // automatically create the tables
    Schemifier.schemify(true, Schemifier.infoF _, entities: _*)
    // setup the loan pattern
    S.addAround(DB.buildLoanWrapper)

    DB.addLogFunc((query, time) => {
      //logger.info(query.allEntries.size + " 数据库查询耗时 " + time + " milliseconds ")
      query.allEntries.foreach(e => {
        if (!e.statement.contains("SELECT") && !e.statement.contains("Closed")) {
          logger.info(
            e.statement + " 耗时 " + e.duration + "ms")
        } else if (e.duration > 100) {
          logger.info(e.statement + " 执行时间过长，" + e.duration + " ms")
        } else {
          logger.debug(e.statement + " 耗时 " + e.duration + "ms")
        }
      })
    })

    entities.size

  }

  def setupUX() = {
    /**** user experience settings ****/
    // set the time that notices should be displayed and then fadeout
    LiftRules.noticesAutoFadeOut.default.set((notices: NoticeType.Value) => Full(2 seconds, 2 seconds))
    LiftRules.loggedInTest = Full(() => Customer.loggedIn_?)
  }

  def setupPayment() = {
    /**** paypal settings ****/
  }

  def setupEmailServer() = {
    //email server
    System.setProperty("mail.smtp.host", Configurations.smtpHost)
    System.setProperty("mail.smtp.user", Configurations.smtpUser)
    System.setProperty("mail.smtp.auth", "true")
    System.setProperty("mail.smtp.from", Configurations.smtpSender)
    Mailer.authenticator = Full(new javax.mail.Authenticator {
      override def getPasswordAuthentication(): javax.mail.PasswordAuthentication =
        new javax.mail.PasswordAuthentication(Configurations.smtpUser, Configurations.smtpPassword)
    })
  }

  def setupNewSiteMap() = {

    SiteMap.enforceUniqueLinks = false
    val allowInvoice = RetailerConfiguration.currentRetailerAllowInvoice
    val menus = List(
      /**
       * Test
       */
      Menu("profile") / accountGroup / "profile" >> LocGroup(testGroup) >> Hidden >> MustBeLoggedIn,
      Menu(testGroup) / "obsolete" / testGroup / "upload" >> LocGroup(testGroup) >> Hidden,
      Menu(testGroup) / "obsolete" / testGroup / testGroup >> LocGroup(testGroup) >> Hidden,
      Menu("edit_promotion") / "admin" / "edit" / "promotion" >> LocGroup(publicGroup) >> Hidden >> MustBeLoggedIn,
      Menu("supplier_import") / "admin" / "create" / "supplier_import" >> LocGroup(publicGroup) >> Hidden >> MustBeLoggedIn,
      Menu("test_bootstrap1") / "proto" / "menu" >> Hidden,
      Menu("test_bootstrap2") / "proto" / "test" >> Hidden,
      Menu("test_bootstrap3") / "proto" / "promotion" >> Hidden >> MustBeLoggedIn,
      Menu("test_bootstrap4") / "proto" / "cart" >> Hidden >> MustBeLoggedIn,
      Menu("test_bootstrap5") / "proto" / "category" >> Hidden >> MustBeLoggedIn,
      Menu("test_bootstrap6") / "proto" / "payment" >> Hidden >> MustBeLoggedIn,
      Menu("test_bootstrap7") / "payment" / "submit2" >> Hidden >> MustBeLoggedIn,
      Menu("test_bootstrap8") / "proto" / "jcrop" >> Hidden,
      Menu("test_bootstrap9") / "proto" / "sales_page" >> Hidden,

      /**
       * Documents
       */
      Menu("agreement") / accountGroup / "agreement" >> LocGroup("doc") >> Hidden,
      /**
       * Internal
       */
      Menu("notice") / "notice" >> LocGroup(internalGroup) >> Hidden,
      Menu("request_approve") / "admin" / "review" / "request_approve" >> LocGroup(internalGroup) >> Hidden >> MustBeLoggedIn,
      Menu("request_deny") / "admin" / "review" / "request_deny" >> LocGroup(internalGroup) >> Hidden >> MustBeLoggedIn,
      Menu("stock_item") / "admin" / "edit" / "stock_item" >> LocGroup(internalGroup) >> Hidden >> MustBeLoggedIn,
      Menu("image_preview") / "stat" / "image_preview" >> LocGroup(internalGroup) >> Hidden >> MustBeLoggedIn,
      Menu("删除库存") / "admin" / "delete" / "stock" >> LocGroup(internalGroup) >> Hidden >> MustBeLoggedIn,
      Menu("payment_return") / "payment" / "return" >> LocGroup(internalGroup) >> Hidden,
      Menu("delivery") / "payment" / "submit" >> LocGroup(internalGroup) >> Hidden >> MustBeLoggedIn,
      Menu("前往支付") / "payment" / "payment" >> LocGroup(internalGroup) >> Hidden >> MustBeLoggedIn,
      Menu("银联支付") / "payment" / "unionpay_return" >> LocGroup(internalGroup) >> Hidden,
      Menu("权限查询") / "stat" / "entitlement_stat" >> LocGroup(internalGroup) >> Hidden >> MustBeLoggedIn,
      Menu("收藏夹") / accountGroup / "favoriate" >> LocGroup(internalGroup) >> Hidden >> MustBeLoggedIn,
      Menu("购物车") / accountGroup / "cart" >> LocGroup(internalGroup) >> Hidden >> MustBeLoggedIn,
      Menu("退货") / "return" >> LocGroup(internalGroup) >> Hidden >> MustBeLoggedIn,
      Menu("发票") / "invoice" >> LocGroup(internalGroup) >> Hidden >> MustBeLoggedIn >> CustomFilter(allowInvoice),
      Menu("lottery") / "lottery" >> LocGroup(internalGroup) >> Hidden,

      Menu("订单客服") / "admin" / "console" / "order" >> LocGroup(adminGroup) >> Hidden >> MustBeLoggedIn,
      Menu("短信客服") / "admin" / "console" / "sms" >> LocGroup(adminGroup) >> Hidden >> MustBeLoggedIn,
      Menu("在线用户") / "admin" / "console" / "onlineusers" >> LocGroup(adminGroup) >> Hidden >> MustBeLoggedIn,
      Menu("数据统计") / "admin" / "console" / "dataviewer" >> LocGroup(adminGroup) >> Hidden >> MustBeLoggedIn,
      Menu("用户反馈") / "admin" / "console" / "feedbackviewer" >> LocGroup(adminGroup) >> Hidden >> MustBeLoggedIn,
      Menu("商场开户") / "admin" / "console" / "retailer_account" >> LocGroup(adminGroup) >> Hidden >> MustBeLoggedIn,
      Menu("页面统计") / "admin" / "console" / "page_stat" >> LocGroup(adminGroup) >> Hidden >> MustBeLoggedIn,
      Menu("其他高级管理功能") / "admin" / "console" / "util" >> LocGroup(adminGroup) >> Hidden >> MustBeLoggedIn,
      Menu("图片管理") / "admin" / "console" / "images" >> LocGroup(adminGroup) >> Hidden >> MustBeLoggedIn,

      Menu("库存管理") / "admin" / "edit" / "stock_auth" >> LocGroup(supplierGroup) >> Value(List((supplierGroup, 1))) >> Hidden >> MustBeLoggedIn,
      Menu("库存查询") / "stat" / "stock_lookup" >> LocGroup(supplierGroup) >> Value(List((supplierGroup, 2))) >> Hidden >> MustBeLoggedIn,
      Menu("库存申请记录") / "stat" / "stock_application" >> LocGroup(supplierGroup) >> Value(List((supplierGroup, 3))) >> Hidden >> MustBeLoggedIn,

      Menu("库存更新审核") / "stat" / "stock_review" >> LocGroup(retailerGroup) >> Value(List((retailerGroup, 1))) >> Hidden >> MustBeLoggedIn,
      Menu("库存下架审核") / "admin" / "review" / "stock_deletion" >> LocGroup(retailerGroup) >> Value(List((retailerGroup, 2))) >> Hidden >> MustBeLoggedIn,

      Menu("商品创建") / "admin" / "create" / "product" >> LocGroup(supplierGroup) >> Value(List((supplierGroup, 4))) >> Hidden >> MustBeLoggedIn,
      Menu("商品信息更新") / "admin" / "edit" / "product" >> LocGroup(supplierGroup) >> Value(List((supplierGroup, 5))) >> Hidden >> MustBeLoggedIn,
      Menu("商品查询") / "stat" / "product_stat" >> LocGroup(supplierGroup, retailerGroup) >> Value(List((supplierGroup, 6), (retailerGroup, 4))) >> Hidden >> MustBeLoggedIn,

      Menu("品牌创建") / "admin" / "create" / "brand" >> LocGroup(retailerGroup) >> Value(List((retailerGroup, 5))) >> Hidden >> MustBeLoggedIn,
      Menu("品牌信息更新") / "admin" / "edit" / "brand" >> LocGroup(retailerGroup) >> Value(List((retailerGroup, 6))) >> Hidden >> MustBeLoggedIn,
      // Menu("品牌查询") / "stat" / "brand_stat" >> /*LocGroup(supplierGroup, retailerGroup) >> Value(List((supplierGroup, 7), (retailerGroup, 7))) >>*/ Hidden >> MustBeLoggedIn,

      //Menu("供应商销售查询") / "stat" / "sales" >> LocGroup(supplierGroup) >> Value(List((supplierGroup, 8))) >> Hidden >> MustBeLoggedIn,
      Menu("销售查询") / "stat" / "sales" >> LocGroup(supplierGroup, retailerGroup) >> Value(List((supplierGroup, 12), (retailerGroup, 8))) >> Hidden >> MustBeLoggedIn,
      Menu("订单查询") / "admin" / "order" >> LocGroup(supplierGroup, retailerGroup) >> Value(List((supplierGroup, 9), (retailerGroup, 9))) >> Hidden >> MustBeLoggedIn,

      Menu("供应商信息更新") / "admin" / "edit" / "entity" >> LocGroup(supplierGroup) >> Value(List((supplierGroup, 10))) >> Hidden >> MustBeLoggedIn,
      Menu("合同信息更新") / "admin" / "edit" / "contract" >> LocGroup(retailerGroup) >> Value(List((retailerGroup, 10))) >> Hidden >> MustBeLoggedIn,
      Menu("合同审核") / "admin" / "review" / "contract" >> LocGroup(retailerGroup) >> Value(List((retailerGroup, 11))) >> Hidden >> MustBeLoggedIn,
      Menu("管理员审核") / "admin" / "review" / "entitlement_review" >> LocGroup(supplierGroup, retailerGroup) >> Value(List((supplierGroup, 11), (retailerGroup, 12))) >> Hidden >> MustBeLoggedIn,

      Menu("更新主页") / "admin" / "create" / "promotion" >> LocGroup(retailerGroup) >> Value(List((retailerGroup, 13))) >> Hidden >> MustBeLoggedIn,
      //Menu("更新商品订阅") / "admin" / "create" / "subscription" >> LocGroup(retailerGroup) >> Value(List((retailerGroup, 22))) >> Hidden >> MustBeLoggedIn,
      Menu("中奖查询") / "stat" / "lottery_stat" >> LocGroup(retailerGroup) >> Value(List((retailerGroup, 14))) >> Hidden >> MustBeLoggedIn,
      Menu("发票查询") / "stat" / "invoice_stat" >> LocGroup(retailerGroup) >> Value(List((retailerGroup, 15))) >> Hidden >> MustBeLoggedIn,
      Menu("供应商查询") / "stat" / "entity" >> LocGroup(retailerGroup) >> Value(List((retailerGroup, 16))) >> Hidden >> MustBeLoggedIn,
      Menu("费用管理") / "admin" / "edit" / "fee" >> LocGroup(retailerGroup) >> Value(List((retailerGroup, 17))) >> Hidden >> MustBeLoggedIn,
      Menu("活动管理") / "admin" / "create" / "event" >> LocGroup(retailerGroup) >> Value(List((retailerGroup, 18))) >> Hidden >> MustBeLoggedIn,
      Menu("积分兑换管理") / "stat" / "cashout_stat" >> LocGroup(retailerGroup) >> Value(List((retailerGroup, 19))) >> Hidden >> MustBeLoggedIn,
      Menu("商品热度统计") / "stat" / "trend" >> LocGroup(retailerGroup) >> Value(List((retailerGroup, 20))) >> Hidden >> MustBeLoggedIn,
      Menu("用户反馈") / "stat" / "feedbacks" >> LocGroup(retailerGroup) >> Value(List((retailerGroup, 21))) >> Hidden >> MustBeLoggedIn,

      Menu("个人信息") / accountGroup / "personal" >> LocGroup(accountGroup) >> Value(List((accountGroup, 1))) >> Hidden >> MustBeLoggedIn,
      Menu("导入会员信息") / accountGroup / "import" >> LocGroup(accountGroup, publicGroup) >> Value(List((accountGroup, 2))) >> Hidden,
      Menu("密码设置") / accountGroup / "security" >> LocGroup(accountGroup) >> Value(List((accountGroup, 3))) >> Hidden >> MustBeLoggedIn,
      Menu("收货地址管理") / accountGroup / "shipping" >> LocGroup(accountGroup) >> Value(List((accountGroup, 4))) >> Hidden >> MustBeLoggedIn,
      Menu("购买过的商品") / accountGroup / "order_history" >> LocGroup(accountGroup) >> Value(List((accountGroup, 5))) >> Hidden >> MustBeLoggedIn,
      Menu("供应商创建") / "admin" / "create" / "entity" >> LocGroup(sellerGroup) >> Value(List((sellerGroup, 6))) >> Hidden >> MustBeLoggedIn,
      Menu("权限管理") / "admin" / "create" / "entitlement_request" >> LocGroup(sellerGroup) >> Value(List((sellerGroup, 7))) >> Hidden >> MustBeLoggedIn,
      Menu("权限请求记录") / "stat" / "entitlement_history" >> LocGroup(sellerGroup) >> Value(List((sellerGroup, 8))) >> Hidden >> MustBeLoggedIn,
      Menu("补开发票") / accountGroup / "payment_history" >> LocGroup(accountGroup) >> Value(List((accountGroup, 9))) >> Hidden >> MustBeLoggedIn >> CustomFilter(allowInvoice),
      Menu("账户激活") / "account" / "activate" >> LocGroup(internalGroup) >> Hidden,
      Menu("图片上传") / "admin" / "create" / "image" >> LocGroup(sellerGroup) >> Value(List((sellerGroup, 10))) >> Hidden >> MustBeLoggedIn,

      Menu("页脚") / "footers" / ** >> LocGroup(publicGroup) >> Hidden,
      Menu("即将上线") / "comingsoon" / ** >> LocGroup(publicGroup) >> Hidden,
      Menu("使用文档") / "instructions" >> LocGroup(publicGroup) >> Hidden,
      Menu("反馈") / "feedback" >> LocGroup(publicGroup) >> Hidden,
      Menu("产品反馈") / "product_feedback" >> LocGroup(publicGroup) >> Hidden,
      Menu("评价") / "comment" >> LocGroup(publicGroup) >> Hidden >> MustBeLoggedIn,
      Menu("库存") / "stock" >> LocGroup(publicGroup) >> Hidden,
      Menu("库存2") / "stock_mobile" >> LocGroup(publicGroup) >> Hidden,
      Menu("库存3") / "stock_share" >> LocGroup(publicGroup) >> Hidden,
      Menu("预定2") / "reserve_mobile" >> LocGroup(publicGroup) >> Hidden,
      Menu("预定") / "reserve" >> LocGroup(publicGroup) >> Hidden,
      Menu("搜索") / "search" >> LocGroup(publicGroup) >> Hidden,
      Menu("分类展示") / "stock_listing" >> LocGroup(publicGroup) >> Hidden,
      Menu("主页") / "index" >> LocGroup(publicGroup)) ::: Category.menus ::: userMenus

    val filteredMenus = menus.filterNot(m => {
      val hideMenu = m.toMenu.loc.params.find(p => p.isInstanceOf[CustomFilter] && (!p.asInstanceOf[CustomFilter].test))
      if (hideMenu.isDefined) logger.info("menu(%s)被过滤".format(m.toMenu.loc.name))
      hideMenu.isDefined
    })
    LiftRules.setSiteMap(SiteMap(filteredMenus: _*))
  }

  def userMenus = List( //,editUserMenuLoc, changePasswordMenuLoc,
    Customer.resetPasswordMenuLoc, Customer.loginMenuLoc, Customer.createUserMenuLoc, Customer.lostPasswordMenuLoc,
    Customer.validateUserMenuLoc, Customer.logoutMenuLoc).flatten(a => a)

  def setupRequestConfig() = {
    // setup the 404 handler 
    LiftRules.uriNotFound.prepend(NamedPF("404handler") {
      case (req, failure) => NotFoundAsTemplate(ParsePath(List("404"), "html", false, false))
    })
    // make requests utf-8
    LiftRules.early.append(_.setCharacterEncoding("UTF-8"))
    LiftRules.dispatch.append(AliPaymentNotification)
    LiftRules.dispatch.append(OrderAPI)
    LiftRules.dispatch.append(UserAPI)
    LiftRules.dispatch.append(FileUpload)
    LiftRules.dispatch.append(MessagingService)
    LiftRules.dispatch.append(ImportManager)
    LiftRules.dispatch.append(UnionPaymentNotification)
    LiftRules.dispatch.append(WeiChatService)

    LiftRules.handleMimeFile = OnDiskFileParamHolder.apply
    LiftRules.addToPackages("com.unuotech.snippet.proto")
    LiftRules.addToPackages("com.unuotech")
    LiftRules.addToPackages("com.unuotech.snippet")
    LiftRules.addToPackages("com.unuotech.snippet.payment")
    LiftRules.addToPackages("com.unuotech.snippet.common")
    LiftRules.addToPackages("com.unuotech.snippet.delete")
    LiftRules.addToPackages("com.unuotech.snippet.query")
    LiftRules.addToPackages("com.unuotech.snippet.review")
    LiftRules.addToPackages("com.unuotech.snippet.update")

  }
  def setupJS() = {
    /*FoBo.InitParam.JQuery = FoBo.JQuery191
    FoBo.InitParam.ToolKit = FoBo.Bootstrap232
    FoBo.init()*/
    // set the JSArtifacts
    LiftRules.jsArtifacts = JQuery14Artifacts

    // make the furniture appear
    LiftRules.ajaxStart =
      Full(() => LiftRules.jsArtifacts.show("loading").cmd)

    // make the furniture go away when it ends
    LiftRules.ajaxEnd =
      Full(() => LiftRules.jsArtifacts.hide("loading").cmd)
  }

  def setupMisc() = {
    val url = LiftRules.defaultGetResource("/logback.xml").get
    Logger.setup = Full(Logback.withFile(url))
    SMSClient.init
  }
  def boot {
    val result = doBoot
    Unit
  }

  def doBoot: BootResult = {
    setupJS()
    val db = setupDB
    setupUX()
    setupPayment()
    setupEmailServer()
    //setupSiteMap()
    setupNewSiteMap()
    setupRequestConfig()
    setupErrorHandling()
    setupMisc()
    setupFileUpload

    DSIBootResult(Some(db))
  }

  def setupFileUpload = {
    LiftRules.maxMimeFileSize = 2000000L
    LiftRules.maxMimeSize = 2000000L
    LiftRules.dispatch.append(UploadManager)

    //Show the spinny image when an Ajax call starts
    LiftRules.ajaxStart =
      Full(() => LiftRules.jsArtifacts.show("ajax-loader").cmd)

    // Make the spinny image go away when it ends
    LiftRules.ajaxEnd =
      Full(() => LiftRules.jsArtifacts.hide("ajax-loader").cmd)
  }

  def setupErrorHandling() = {
    LiftRules.exceptionHandler.prepend {
      case (_, _, exception: SizeLimitExceededException) => {
        RedirectResponse("/error") //JavaScriptResponse(JsCmds.Alert("文件大小不能超过2M"))
      }
      case (Props.RunModes.Production, _, exception) => RedirectResponse("/error")
    }
  }
}
