package com.puluo.snippet
import scala.collection.JavaConversions._
import java.util.UUID
import net.liftweb.common.Full
import net.liftweb.db.DB
import net.liftweb.db.DB1.db1ToDb
import net.liftweb.db.DefaultConnectionIdentifier
import net.liftweb.http.SHtml
import net.liftweb.http.RequestVar
import net.liftweb.http.js.JsCmds
import net.liftweb.http.js.JsCmds.Alert
import net.liftweb.mapper.By
import net.liftweb.util.AnyVar.whatVarIs
import net.liftweb.util.PassThru
import net.liftweb.common.Loggable
import net.liftweb.http.js.JsCmd
import net.liftweb.common.Empty
import net.liftweb.http.S
import net.liftweb.http.js.JsCmds.ReplaceOptions
import net.liftweb.util.Helpers._
import net.liftweb.mapper.NotBy
import net.liftweb.http.js.JE
import java.io.File
import com.puluo.config.Configurations
import com.puluo.entity.UserImage
import com.puluo.util.Strs
import com.puluo.entity.PuluoAdmin
import com.puluo.snippet.util.SortedChineseMapperPaginator
import com.puluo.service.PuluoService
import com.puluo.snippet.util.PuluoSnippetUtil
import com.puluo.entity.PuluoEvent
import net.liftweb.http.SessionVar
import com.puluo.api.event.EventSearchAPI
import com.puluo.enumeration.EventSortType
import com.puluo.enumeration.SortDirection
import com.puluo.enumeration.EventStatus
import com.puluo.util.TimeUtils
import com.puluo.enumeration.PuluoEventCategory

class EventSearchSnippet extends PuluoSnippetUtil with Loggable {
  object keyword extends RequestVar[Option[String]](None)
  object events extends SessionVar[Seq[PuluoEvent]](Seq.empty)

  def render = {
    "#keyword" #> renderText(keyword) &
      "#search" #> SHtml.ajaxButton("搜索", () => {
        doSearch
        JsCmds.Reload
      }) &
      ".event-results *" #> events.get.map(renderEvent(_))
  }

  private def renderEvent(event: PuluoEvent) = {
    val info = event.eventInfo()
    "#event-uuid *" #> event.eventUUID() &
      "#event-name *" #> info.name() &
      "#event-desc *" #> info.description() &
      "#event_time *" #> TimeUtils.formatDate(event.eventTime()) &
       "#update [href]" #> s"/event?uuid=${event.eventUUID()}"
  }

  private def doSearch: Unit = {
    events(Seq())
    if (keyword.get.isDefined) {
      val keys = keyword.get.get
      val resultSet = keys.trim.split(' ').map(searchOneWord(_))
      if (!resultSet.isEmpty) {
        val first = resultSet.head
        val tail = resultSet.tail
        val allEvents = resultSet.foldLeft(first)((res, next) => res.intersect(next))
        logger.info(s"searched ${allEvents.size}")
        events(allEvents)
      }
    }
  }

  private def searchOneWord(word: String): Seq[PuluoEvent] = {
    val api = new EventSearchAPI(null, null, word, null, EventSortType.Time,
      SortDirection.Desc, 0.0, 0.0, 0.0, EventStatus.Open, null,5,0)
    api.execute()
    val res = api.searchedEvents.toSeq
    logger.info(s"search ${word} returns ${res.size}")
    res
  }
}