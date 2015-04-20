package com.puluo.snippet

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
import com.puluo.entity.PuluoAdmin
import com.puluo.entity.PuluoUser
import net.liftweb.http.SessionVar
import com.puluo.dao.impl.DaoApi
import com.puluo.util.TimeUtils
import com.puluo.snippet.util.PuluoSnippetUtil
import com.puluo.entity.impl.PuluoUserType

object PuluoUserUpdateSnippet extends PuluoSnippetUtil{
  object searchUser extends SessionVar[Option[PuluoUser]](None)
  object inputMobile extends RequestVar[Option[String]](None)

  object uuid extends RequestVar[String]("")
  object mobile extends RequestVar[String]("")
  object thumbnail extends RequestVar[String]("")
  object created_at extends RequestVar[String]("")
  object updated_at extends RequestVar[String]("")

  object userType extends RequestVar[Option[String]](None)
  object banned extends RequestVar[Option[String]](None)
  object sex extends RequestVar[Option[String]](None)
  object state extends RequestVar[Option[String]](None)
  object city extends RequestVar[Option[String]](None)

  object email extends RequestVar[Option[String]](None)
  object last extends RequestVar[Option[String]](None)
  object first extends RequestVar[Option[String]](None)
  object birthday extends RequestVar[Option[String]](None)
  object occupation extends RequestVar[Option[String]](None)
  object address extends RequestVar[Option[String]](None)
  object zip extends RequestVar[Option[String]](None)
  object saying extends RequestVar[Option[String]](None)

  def render = {
    loadUser
    "#input_mobile" #> renderText(inputMobile) &
      "#search" #> SHtml.ajaxButton("搜索", () => {
        searchUser(None)
        if (inputMobile.get.isDefined) {
          val mobile = inputMobile.get.get
          val usr = DaoApi.getInstance().userDao().getByMobile(mobile)
          if (usr != null) {
            searchUser(Some(usr))
            JsCmds.Reload
          } else JsCmds.Alert(s"您查找的用户${mobile}不存在")
        } else JsCmds.Alert("请输入用户手机号")
      }) &
      "#uuid *" #> uuid.get &
      "#mobile *" #> mobile.get &
      "#thumbnail [src]" #> thumbnail.get &
      "#created_at *" #> created_at.get &
      "#updated_at *" #> updated_at.get &
      "#email" #> renderText(email) &
      "#last_name" #> renderText(last) &
      "#first_name" #> renderText(first) &
      "#birthday" #> renderDate(birthday) &
      "#occupation" #> renderText(occupation) &
      "#address" #> renderText(address) &
      "#zip" #> renderText(zip) &
      "#saying" #> renderText(saying) &
      (renderAllStatesAndCities(state,city)) &
      "#sex" #> renderSimpleSelect(Seq("M","F","男","女"),sex) &
      "#user_type" #> renderSimpleSelect(PuluoUserType.values.toSeq.map(_.toString),userType) &
      "#banned" #> renderSimpleSelect(Seq("true","false"),banned) &
      "#update" #> SHtml.ajaxButton("更新", () => {
        if (searchUser.isDefined) {
          val user = searchUser.get.get
          try {
            DaoApi.getInstance().userDao().updateProfile(user,
              first.getOrElse(""),
              last.getOrElse(""),
              "", //thumbnail
              "", //large_image
              saying.getOrElse(""),
              email.getOrElse(""),
              sex.getOrElse(""), //sex
              birthday.getOrElse(""),
              "", //country
              state.getOrElse(""), //state
              city.getOrElse(""), //city
              zip.getOrElse(""))
            JsCmds.Alert(s"成功更新用户${mobile}")
          } catch {
            case e: Exception => JsCmds.Alert(s"更新用户${mobile}失败")
          }
        } else JsCmds.Alert("您还没有搜索用户")
      })

  }

  private def loadUser = {
    if (searchUser.get.isDefined) {
      val user = searchUser.get.get
      uuid(user.userUUID())
      mobile(user.mobile())
      thumbnail(user.thumbnail())
      created_at(TimeUtils.formatDate(user.createdAt()))
      updated_at(TimeUtils.formatDate(user.updatedAt()))
      userType(Some(user.userType().toString()))
      banned(Some(user.banned().toString))
      sex(Some(user.sex().toString))
      state(Some(user.state()))
      city(Some(user.city()))
      email(Some(user.email()))
      last(Some(user.lastName()))
      first(Some(user.firstName()))
      birthday(Some(TimeUtils.formatBirthday(user.birthday())))
      occupation(Some(user.occupation()))
      address(Some(user.address()))
      zip(Some(user.zip()))
      saying(Some(user.saying()))
    } else {
      uuid("")
      mobile("")
      thumbnail("")
      created_at("")
      updated_at("")
      userType(None)
      banned(None)
      sex(None)
      state(None)
      city(None)
      email(None)
      last(None)
      first(None)
      birthday(None)
      occupation(None)
      address(None)
      zip(None)
      saying(None)
    }
  }
}