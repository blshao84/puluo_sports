package com.puluo.snippet.util

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

trait PuluoSnippetUtil {
  def renderText(v: RequestVar[Option[String]]) = {
    SHtml.ajaxText(v.getOrElse(""), s => {
      v(Some(s.trim()))
      JsCmds.Noop
    })
  }

  def renderDate(v: RequestVar[Option[String]]) = {
    SHtml.ajaxText(v.getOrElse(""), s => {
      val dateStr = s.trim()
      val date = TimeUtils.parseDate(dateStr)
      if (date != null) {
        v(Some(dateStr))
        JsCmds.Noop
      } else {
        JsCmds.Alert("时间格式错误，请重新输入")
      }
    })
  }
}