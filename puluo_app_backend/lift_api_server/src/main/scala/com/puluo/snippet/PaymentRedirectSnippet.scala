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
import com.puluo.api.payment.AliPaymentNotification
import com.puluo.api.payment.PuluoAlipayAPI
import net.liftweb.http.Req
import scala.xml.XML
import java.util.HashMap
import com.puluo.util.AlipayLinkCache

object PaymentRedirectSnippet extends PuluoSnippetUtil with Loggable {
  def render = {
     val uuid = S.param("uuid").getOrElse("")
     val link = AlipayLinkCache.get(uuid)
     logger.info(s"get link from AlipayLinkCache:${uuid}=${link}")
     "#frame [src]" #> link
  }
}