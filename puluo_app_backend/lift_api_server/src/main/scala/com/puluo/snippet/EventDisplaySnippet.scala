package com.puluo.snippet

import scala.collection.JavaConversions._
import net.liftweb.http.RequestVar
import net.liftweb.util.Helpers._
import net.liftweb.http.SHtml
import net.liftweb.http.js.JsCmds
import net.liftweb.common.Empty
import net.liftweb.http.js.JsCmd
import net.liftweb.http.SHtml.ChoiceHolder
import net.liftweb.http.js.JsCmds.ReplaceOptions
import net.liftweb.common.Full
import org.joda.time.LocalDate
import net.liftweb.http.S
import net.liftweb.common.Loggable
import net.liftweb.http.LiftRules
import net.liftweb.http.js.JsCmds._
import net.liftweb.sitemap.Loc.Value
import bootstrap.liftweb.Boot
import net.liftweb.util.PassThru
import com.puluo.dao.impl.DaoApi
import com.puluo.util.TimeUtils
import com.puluo.util.Strs
import com.puluo.snippet.util.PuluoSnippetUtil
import com.puluo.api.event.EventRegistrationAPI
import com.puluo.config.Configurations
import java.util.UUID
import com.puluo.util.AlipayLinkCache
import com.puluo.entity.PuluoCoupon
import com.puluo.entity.PuluoEvent
import com.puluo.util.EventPriceCalculator
import com.puluo.entity.PuluoUser
import com.puluo.entity.impl.PuluoCouponImpl
import com.puluo.api.auth.UserRegistrationAPI
import com.puluo.util.PasswordEncryptionUtil
import com.puluo.api.service.SMSServiceAPI
import com.puluo.enumeration.PuluoSMSType
import com.puluo.util.PuluoAuthCodeSender
import org.joda.time.DateTime
import com.puluo.enumeration.CouponType

object EventDisplaySnippet extends PuluoSnippetUtil with PuluoAuthCodeSender with Loggable {
  val mock = false

  object mobile extends RequestVar[Option[String]](None)
  object coupon extends RequestVar[Option[PuluoCoupon]](None)
  object authCode extends RequestVar[Option[String]](None)

  def getAuthMobile = mobile

  def setAuthCode(ac: String) = authCode(Some(ac))

  def getAuthCode = authCode

  def render = {
    val dsi = DaoApi.getInstance()
    val userDao = DaoApi.getInstance().userDao()
    val eventUUID = S.param("uuid").getOrElse("")
    val userUUID = S.param("user_uuid").getOrElse("")
    val userFromLink = userDao.getByUUID(userUUID)
    val event = dsi.eventDao().getEventByUUID(eventUUID)
    if (event == null || event.eventTime().isBefore(DateTime.now)) {
      "#event" #> "课程不存在"
    } else {
      //get valid coupons
      val coupons = dsi.couponDao().getByUserUUID(userUUID, true).filter(_.locationUUID() == event.eventLocationUUID())
      val couponOptions = coupons.sortBy(_.validUntil().getMillis()).
        map(c => (c.uuid(),
          s"优惠${Strs.prettyDouble(c.amount(), 1)}元,有效期至${c.validUntil().getYear()}年${c.validUntil().getMonthOfYear()}月${c.validUntil().getDayOfMonth()}日"))
      logger.info(s"load ${couponOptions.size} coupons")
      val registered = event.registeredUsers()
      val info = event.eventInfo()
      val loc = event.eventLocation()
      val address = if (loc == null) "" else loc.address()
      val pic = info.poster().headOption.map(_.imageURL()).getOrElse("empty.jpg")
      "#pic [src]" #> s"${pic}!small" &
        "#name *" #> info.name() &
        "#time *" #> TimeUtils.formatDate(event.eventTime()) &
        "#loc *" #> address &
        "#coach *" #> info.coachName() &
        "#duration *" #> info.duration() &
        renderPrice(event) &
        "#registered *" #> registered &
        "#remaining *" #> (event.capatcity() - registered) &
        "#desc *" #> info.description() &
        renderSendAuthCode &
        renderCoupons(couponOptions, event, userFromLink) &
        renderMobile(userFromLink) &
        renderReserve(userFromLink, event)
    }

  }

  private def renderReserve(userFromLink: PuluoUser, event: PuluoEvent) = {
    val userDao = DaoApi.getInstance().userDao()
    "#reserve" #> SHtml.ajaxButton("预定", () => {
      if (mobile.isDefined) {
        val user = if (userFromLink != null) userFromLink
        else userDao.getByMobile(mobile.get.get)
        if (user != null) {
          if (userFromLink != null) {
            doEventRegistration(user, event)
          } else {
            JsCmds.RedirectTo(s"/single_event?uuid=${event.eventUUID}&user_uuid=${user.userUUID()}")
          }
        } else {
          verifyAuthCodeForRegistration(
            onNoInput = () => {
              JsCmds.JsShowId("auth_code_row_name") &
                JsCmds.JsShowId("auth_code_row_value") &
                JsCmds.Alert("您还不是普罗体育的注册用户，请点击\"发送验证码\"一步完成认证")
            },
            onSuccess = (newUser: PuluoUser) => {
              val couponDao = DaoApi.getInstance().couponDao()
              couponDao.insertCoupon(new PuluoCouponImpl(
                UUID.randomUUID().toString(),
                CouponType.Deduction,
                Configurations.registrationAwardAmount,
                newUser.userUUID(),
                null,
                Configurations.puluoLocation.locationId(),
                DateTime.now.plusDays(30)))
              JsCmds.RedirectTo(s"/single_event?uuid=${event.eventUUID}&user_uuid=${newUser.userUUID()}")

            },
            onFailure = () => {
              JsCmds.Alert("抱歉，您输入的验证码不正确")
            })
        }
      } else JsCmds.Alert("请输入您的电话")
    })
  }

  private def doEventRegistration(user: PuluoUser, event: PuluoEvent) = {
    val couponUUID: String = coupon.map(_.uuid()).getOrElse(null)
    val api = new EventRegistrationAPI(event.eventUUID(), user.userUUID(), couponUUID, mock)
    api.execute()
    if (api.error == null) {
      val order = DaoApi.getInstance().paymentDao().getOrderByUUID(api.orderUUID)
      val amount = order.amount
      if (amount == 0) {
        JsCmds.Alert("恭喜您成功预订课程！")
      } else {
        val link = api.paymentLink
        val uuid = UUID.randomUUID().toString()
        val redirectLink = s"/payment?uuid=${uuid}"
        AlipayLinkCache.put(uuid, link)
        logger.info(s"jump to own iframe page ${uuid}=${redirectLink}")
        JsCmds.RedirectTo(redirectLink)
      }
    } else JsCmds.Alert("注册课程时发生异常，请稍后再试")
  }

  private def renderCoupons(couponOptions: Seq[(String, String)], event: PuluoEvent, user: PuluoUser) = {
    val dsi = DaoApi.getInstance()
    if (couponOptions.isEmpty) {
      ".couponDev" #> ""
    } else {
      val opts = ("", "") :: couponOptions.toList
      "#coupon" #> SHtml.ajaxSelect(opts, Empty, cid => {
        if (Strs.isEmpty(cid)) {
          JsCmds.Noop
        } else {
          val cp = dsi.couponDao().getByCouponUUID(cid)
          if (cp != null) {
            coupon(Some(cp))
            val finalPrice = EventPriceCalculator.calculate(event, cp, user)
            JsCmds.Alert(s"成功选择一个${cp.amount()}元优惠券，您只需实际支付${finalPrice}元")
          } else {
            JsCmds.Alert("对不起，载入优惠券发生异常，请稍后再试")
          }
        }
      })
    }
  }

  private def renderMobile(user: PuluoUser) = {
    if (user != null) {
      mobile(Some(user.mobile()))
      "#mobile" #> user.mobile()
    } else ("#mobile" #> renderText(mobile))
  }

  private def renderPrice(event: PuluoEvent) = {
    if (event.discount() < 1) {
      "#price *" #> Strs.prettyDouble(event.originalPrice(), 2) &
        "#discount *" #> Strs.prettyDouble(event.discountedPrice(), 2)
    } else {
      "#price *" #> Strs.prettyDouble(event.price(), 2) &
        ".discountDiv" #> "" &
        ".priceDiv [style]" #> ""
    }
  }
}