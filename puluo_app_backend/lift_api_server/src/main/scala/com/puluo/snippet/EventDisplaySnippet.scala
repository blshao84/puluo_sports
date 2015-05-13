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

object EventDisplaySnippet extends PuluoSnippetUtil with Loggable {
  object mobile extends RequestVar[Option[String]](None)
  def render = {
    val eventUUID = S.param("uuid").getOrElse("")
    val event = DaoApi.getInstance().eventDao().getEventByUUID(eventUUID)
    if (event == null) {
      "#event" #> "课程不存在"
    } else {
      val registered = event.registeredUsers()
      val info = event.eventInfo()
      val loc = event.eventLocation()
      val pic = info.poster().headOption.map(_.imageURL()).getOrElse("empty.jpg")
      "#pic [src]" #> s"${pic}!small" &
        "#name *" #> info.name() &
        "#time *" #> TimeUtils.formatDate(event.eventTime()) &
        "#loc *" #> loc.address() &
        "#coach *" #> info.coachName() &
        "#duration *" #> info.duration() &
        (if (event.discount() < 1) {
          "#price *" #> Strs.prettyDouble(event.price(), 2) &
            "#discount *" #> Strs.prettyDouble(event.discountedPrice(), 2)
        } else {
          "#price *" #> Strs.prettyDouble(event.price(), 2) &
            "#discountDiv" #> "" &
            "#priceDiv [style]" #> ""
        }) &
        "#registered *" #> registered &
        "#remaining *" #> (event.capatcity() - registered) &
        "#desc *" #> info.description() &
        "#mobile" #> renderText(mobile) &
        "#reserve" #> SHtml.ajaxButton("预定", () => {
          if (mobile.isDefined) {
            val user = DaoApi.getInstance().userDao().getByMobile(mobile.get.get)
            if (user != null) {
              val api = new EventRegistrationAPI(event.eventUUID(), user.userUUID(), false)
              api.execute()
              if (api.error == null) {
                if (event.price() == 0) {
                  JsCmds.Alert("恭喜您成功预订课程！")
                } else {
                  val link = api.paymentLink
                  JsCmds.RedirectTo(link)
                }
              } else JsCmds.Alert("注册课程时发生异常，请稍后再试")
            } else {
              JsCmds.Alert("您还没有注册，请您通过微信公众号一键注册！")
            }
          } else JsCmds.Alert("请输入您的电话")
        })
    }

  }
}