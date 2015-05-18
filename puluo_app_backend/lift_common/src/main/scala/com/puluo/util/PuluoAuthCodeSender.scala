package com.puluo.util

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
import net.liftweb.util.PassThru
import net.liftweb.common.Loggable
import com.puluo.api.service.SMSServiceAPI
import com.puluo.enumeration.PuluoSMSType
import com.puluo.dao.impl.DaoApi
import com.puluo.api.auth.UserRegistrationAPI
import com.puluo.entity.PuluoUser

trait PuluoAuthCodeSender extends Loggable {
  def mock: Boolean
  def getAuthMobile: RequestVar[Option[String]]
  def setAuthCode(ac: String): Unit
  def getAuthCode: RequestVar[Option[String]]

  def renderSendAuthCode = {
    "#send_auth_code" #> SHtml.ajaxButton("发送验证码", () => {
      if (getAuthMobile.isDefined) {
        val api = new SMSServiceAPI(PuluoSMSType.UserRegistration,
          getAuthMobile.get.get, mock, Map.empty[String, String])
        api.execute()
        if (api.isSuccess()) {
          val ac = DaoApi.getInstance().authCodeRecordDao()
            .getRegistrationAuthCodeFromMobile(getAuthMobile.get.get).authCode()
          logger.info(s"Successfully load authCode=${ac} for mobile=${getAuthMobile.get.get}")
          setAuthCode(ac)
          JsCmds.Alert("成功发送验证码，请输入收到的验证码并点击\"预订\"一步完成课程预订")
        } else {
          JsCmds.Alert("发送手机号失败，请确认正确输入手机号")
        }

      } else {
        JsCmds.Alert("请输入手机号")
      }
    })
  }

  def verifyAuthCodeForRegistration(
    onNoInput: () => JsCmd,
    onSuccess: PuluoUser => JsCmd,
    onFailure: () => JsCmd) = {
    val existingUser = DaoApi.getInstance().userDao().getByMobile(getAuthMobile.get.get)
    if (existingUser == null) {
      val authDao = DaoApi.getInstance().authCodeRecordDao()
      val authRecord = authDao.getRegistrationAuthCodeFromMobile(getAuthMobile.get.get)
      if (authRecord == null || !getAuthCode.isDefined) {
        onNoInput()
      } else {
        if (authRecord.authCode() == getAuthCode.get.get) {
          val password = PasswordEncryptionUtil.encrypt(authRecord.authCode())
          val api = new UserRegistrationAPI(getAuthMobile.get.get, password, authRecord.authCode())
          api.execute()
          val newUserUUID = api.userUUID
          if (newUserUUID == null) {
            JsCmds.Alert("注册发生错误")
          } else {
            val newUser = DaoApi.getInstance().userDao().getByUUID(newUserUUID)
            onSuccess(newUser)
          }
        } else {
          onFailure()
        }
      }
    } else JsCmds.Alert("您已经是普罗体育的注册用户！")
  }
}