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
import net.liftweb.util.PassThru
import com.puluo.entity.PuluoUser
import net.liftweb.http.SessionVar
import com.puluo.dao.impl.DaoApi
import com.puluo.util.TimeUtils
import com.puluo.util.Location

trait PuluoSnippetUtil {
  def hidDiv(id: String)(cond: => Boolean) = {
    val hide = if (cond) "display: none;" else ""
    s"#${id} [style]" #> hide
  }
  def renderText(v: RequestVar[Option[String]]) = {
    SHtml.ajaxText(v.getOrElse(""), s => {
      v(Some(s.trim()))
      JsCmds.Noop
    })
  }

  def renderInt(v: RequestVar[Option[Int]]) = {
    SHtml.ajaxText(v.map(_.toString).getOrElse("0"), s => {
      try {
        v(Some(s.toInt))
        JsCmds.Noop
      } catch {
        case e: Exception => JsCmds.Alert("请输入数字")
      }
    })
  }
  
  def renderDouble(v: RequestVar[Option[Double]]) = {
    SHtml.ajaxText(v.map(_.toString).getOrElse("0"), s => {
      try {
        v(Some(s.toDouble))
        JsCmds.Noop
      } catch {
        case e: Exception => JsCmds.Alert("请输入数字")
      }
    })
  }

  def renderTextArea(v: RequestVar[Option[String]]) = {
    SHtml.ajaxTextarea(v.getOrElse(""), s => {
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

  def renderSimpleSelect(
    options: Seq[String],
    sel: RequestVar[Option[String]]) = {
    val target = sel.get.getOrElse("")
    val unselectedOpts = options.filter( _ != target)
    val newOptions = if(unselectedOpts.contains(target)) options else target :: unselectedOpts.toList
    SHtml.ajaxSelect(
      options.map(s => (s, s)),
      sel.get,
      s => { sel(Some(s)); JsCmds.Noop })

  }

  def renderAllStatesAndCities(state: RequestVar[Option[String]], city: RequestVar[Option[String]]) = {
    val states = Location.states
    renderStateAndCity(states, Seq(), state, city)
  }
  def renderStateAndCity(states: Seq[String], cities: Seq[String], state: RequestVar[Option[String]], city: RequestVar[Option[String]]) = {
    "#state" #> SHtml.ajaxSelect(states.map(s => (s, s)).toSeq, state.get, { s =>
      state(Some(s))
      replace(state.getOrElse(""))
    }) &
      "#city" #> SHtml.ajaxUntrustedSelect(cities.map(s => (s, s)).toSeq, city.get, { s =>
        city(Some(s))
        JsCmds.Noop
      }, ("id" -> "city"))
  }
  
  def parseFileName(fileName:String):(String,String) = {
     val lastDot = fileName.lastIndexOf('.')
    if (lastDot > 0) {
      val fileType = fileName.subSequence(lastDot+1, fileName.length()).toString
      val name = fileName.subSequence(0, lastDot).toString
      (name, fileType)
    } else {
     (fileName,"")
    }
  }
  private def replace(s: String): JsCmd = {
    val cities = Location.getCity(s)
    val first = cities.headOption.getOrElse("")
    ReplaceOptions("city", cities.map(s => (s, s)).toList, Full(first))
  }
}