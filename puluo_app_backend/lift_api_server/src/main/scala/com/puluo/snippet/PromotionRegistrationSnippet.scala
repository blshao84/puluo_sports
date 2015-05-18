package com.puluo.snippet

import java.util.UUID
import org.joda.time.DateTime
import com.puluo.dao.impl.DaoApi
import com.puluo.entity.PuluoUser
import com.puluo.entity.impl.PuluoCouponImpl
import com.puluo.enumeration.CouponType
import com.puluo.snippet.util.PuluoSnippetUtil
import com.puluo.util.PuluoAuthCodeSender
import com.puluo.util.Strs
import net.liftweb.common.Box.box2Option
import net.liftweb.common.Loggable
import net.liftweb.http.RequestVar
import net.liftweb.http.S
import net.liftweb.http.SHtml
import net.liftweb.http.js.JsCmds
import net.liftweb.util.AnyVar.whatVarIs
import net.liftweb.util.Helpers.strToCssBindPromoter
import com.puluo.entity.impl.PuluoRegistrationInvitationImpl
import com.puluo.config.Configurations

object PromotionRegistrationSnippet extends PuluoSnippetUtil with PuluoAuthCodeSender with Loggable {
  val mock = false

  object mobile extends RequestVar[Option[String]](None)
  object authCode extends RequestVar[Option[String]](None)
  object uuid extends RequestVar[Option[String]](None)
  
  def getAuthMobile = mobile

  def setAuthCode(ac: String) = authCode(Some(ac))

  def getAuthCode = authCode

  def render = {

    renderSendAuthCode &
      "#mobile" #> renderText(mobile) &
      renderRegister

  }

  private def renderRegister = {
    uuid(S.param("uuid"))
    val userDao = DaoApi.getInstance().userDao()
    "#register" #> SHtml.ajaxButton("预定", () => {
      if (mobile.isDefined) {
        verifyAuthCodeForRegistration(
          onNoInput = () => {
            JsCmds.Alert("请点击\"发送验证码\"一步完成注册")
          },
          onSuccess = (newUser: PuluoUser) => {
            saveCoupons(newUser)
          },
          onFailure = () => {
            JsCmds.Alert("抱歉，您输入的验证码不正确")
          })
      } else JsCmds.Alert("请输入您的电话")
    })
  }

  private def saveCoupons(newUser: PuluoUser) = {
    val fromUUID = uuid.getOrElse("")
    val fromMobile = if (Strs.isEmpty(fromUUID)) {
      logger.warn(s"Missing the user who recomments ${newUser.mobile}, save empty instead")
      ""
    } else {
      val fromUser = DaoApi.getInstance().userDao().getByUUID(fromUUID)
      if (fromUser == null) {
        logger.warn(s"Missing the user who recomments ${newUser.mobile}, save empty instead")
        ""
      } else {
        fromUser.mobile()
      }
    }
    val couponDao = DaoApi.getInstance().couponDao()
    couponDao.insertCoupon(new PuluoCouponImpl(
      UUID.randomUUID().toString(),
      CouponType.Deduction,
      Configurations.registrationAwardAmount,
      newUser.userUUID(),
      null,
      DateTime.now.plusDays(30)))

    val invitationDao = DaoApi.getInstance().invitationDao()
    invitationDao.insertInvitation(new PuluoRegistrationInvitationImpl(
        UUID.randomUUID().toString(),
        fromUUID,
        newUser.userUUID(),
        null,null,null));
    JsCmds.Alert(s"恭喜，注册成功！普罗运动已向您和您的好友 ${fromMobile} 的账户中各存人价值${Configurations.registrationAwardAmount}元的普罗运动免费体验券一张" +
      "\n赶快关注普罗运动公众号，约上小伙伴一起动起来吧！")

  }
}