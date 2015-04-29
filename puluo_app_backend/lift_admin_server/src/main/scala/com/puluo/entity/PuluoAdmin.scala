package com.puluo.entity

import scala.xml.Elem
import scala.xml.Node
import scala.xml.NodeSeq
import scala.xml.TopScope
import net.liftweb.common.Box
import net.liftweb.common.Full
import net.liftweb.common.Loggable
import net.liftweb.http.S
import net.liftweb.mapper._
import net.liftweb.mapper.ManyToMany
import net.liftweb.sitemap.Loc._
import net.liftweb.util.BaseField
import org.h2.jdbc.JdbcSQLException
import net.liftweb.common.Empty
import net.liftweb.util.BindHelpers._
import net.liftweb.http.js.JsCmds._
import net.liftweb.http.SHtml
import scala.util.Random
import net.liftweb.http.js.JsCmds
import net.liftweb.http.SessionVar
import net.liftweb.http.js.JE
import net.liftweb.http.js.JsCmd
import net.liftweb.json.JsonAST.JValue
import org.joda.time.LocalDate
import net.liftweb.util.Mailer
import net.liftweb.util.Mailer._
import net.liftweb.util.Helpers.urlEncode
import net.liftweb.util.FieldError
import com.puluo.util.PasswordEncryptionUtil
import com.puluo.dao.impl.DaoApi

object PuluoAdmin extends PuluoAdmin
  with KeyedMetaMapper[Long, PuluoAdmin]
  with MetaMegaProtoUser[PuluoAdmin]
  with Loggable {

  object signupRedirect extends SessionVar[Box[String]](Empty)

  override protected def globalUserLocParams: List[LocParam[Unit]] = List(Hidden)
  override def dbTableName = "puluo_admin"
  // proto user
  override val basePath = "account" :: Nil
  override def homePage = "/"
  override def skipEmailValidation = true

  override def screenWrap = Full(<lift:surround with="bootstrap_base" at="content"><lift:bind/></lift:surround>)
  override def loginMenuLocParams = LocGroup("public") :: super.loginMenuLocParams
  override def logoutMenuLocParams = LocGroup("public") :: super.logoutMenuLocParams

  override protected def findUserByUserName(mobile: String): Box[PuluoAdmin] = find(By(PuluoAdmin.mobile,mobile))

  override def login = {
    if (S.post_?) {
      S.param("username").
        flatMap(username => findUserByUserName(username)) match {
          case Full(user) if verifyPassword(user.puluoUser, S.param("password").getOrElse("")) => {
            val preLoginState = capturePreLoginState()
            val redir = {
              loginRedirect.is match {
                case Full(url) =>
                  loginRedirect(Empty)
                  url
                case _ =>
                  homePage
              }
            }
            logUserIn(user, () => {
              S.notice(S.?("logged.in"))
              preLoginState()
              S.redirectTo(redir)
            })
          }
          case _ => S.error(S.?("invalid.credentials"))
        }
    }

    bind("user", loginXhtml,
      "email" -> (FocusOnLoad(<input id="email" type="text" name="username"/>)),
      "password" -> (<input id="password" type="password" name="password"/>),
      "code" -> (<input id="code" type="text" name="code"/>),
      "submit" -> loginSubmitButton(S.?("log.in")))
  }

  private def verifyPassword(user: PuluoUser, password: String): Boolean = {
    val encryptPassword = PasswordEncryptionUtil.encrypt(password)
    val originalPassword = user.password()
    if (encryptPassword != originalPassword) {
      logger.info(String.format("user.password=%s,encryptpassword=%s", originalPassword, encryptPassword))
    }
    originalPassword == encryptPassword
  }
}

class PuluoAdmin extends MegaProtoUser[PuluoAdmin] {
  def getSingleton = PuluoAdmin
  object mobile extends MappedString(this, 100)
  def puluoUser = DaoApi.getInstance().userDao().getByMobile(mobile.get)
}
