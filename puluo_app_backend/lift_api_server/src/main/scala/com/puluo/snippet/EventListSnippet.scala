package com.puluo.snippet

import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
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
import com.puluo.entity.impl.PuluoEventAttendee
import com.puluo.api.event.EventPromotionFactory
import com.puluo.api.event.EventPromotionFactory
import com.puluo.api.event.EventSearchAPI
import com.puluo.enumeration.EventSortType
import com.puluo.enumeration.EventStatus
import com.puluo.enumeration.PuluoEventCategory
import com.puluo.enumeration.SortDirection
import com.puluo.entity.PuluoEventPoster

object EventListSnippet extends PuluoSnippetUtil with Loggable {



  def render = {
    val now = DateTime.now()
    val fromDate = now//.minusDays(now.dayOfWeek().get -1)
    val toDate = now.plusDays(7-now.dayOfWeek().get)
    val api = new EventSearchAPI(
         fromDate, 
         toDate,
		 null,null,
		 EventSortType.Time,
		 SortDirection.Asc, 
		 0,0,0, EventStatus.Open,
		 null,100,0)
    api.execute()
    ".list *" #> api.searchedEvents.map(renderOneEvent)
  }

  private def renderOneEvent(event:PuluoEvent) = {
    val info = event.eventInfo()
    val thumbnail = info.poster().headOption.map(_.thumbnailURL()).getOrElse(Configurations.emptyImage)
    "#event-link [href]" #> s"/event?uuid=${event.eventUUID()}" &
    "#event-thumbnail [src]" #> thumbnail &
    "#event-name" #> info.name() &
    "#event-time" #> TimeUtils.formatDate(event.eventTime()) &
    "#event-coach-name" #> info.coachName() &
    "#event-price" #> Strs.prettyDouble(event.price(), 1) &
    "#event-registered-users" #> event.attendees().size()
  }
}