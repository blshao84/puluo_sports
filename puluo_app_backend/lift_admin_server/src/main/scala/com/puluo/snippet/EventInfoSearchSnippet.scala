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
import com.puluo.entity.PuluoEventInfo
import com.puluo.dao.impl.DaoApi

class EventInfoSearchSnippet extends PuluoSnippetUtil with Loggable {
  object keyword extends RequestVar[Option[String]](None)
  object eventInfos extends SessionVar[Seq[PuluoEventInfo]](Seq.empty)

  def render = {
    "#keyword" #> renderText(keyword) &
      "#search" #> SHtml.ajaxButton("搜索", () => {
        doSearch
        JsCmds.Reload
      }) &
      ".event-results *" #> eventInfos.get.map(renderEventInfo(_))
  }

  private def renderEventInfo(info: PuluoEventInfo) = {
    "#info-uuid *" #> info.eventInfoUUID() &
      "#info-name *" #> info.name() &
      "#info-desc *" #> info.description() &
      "#info-coach *" #> info.coachName()
  }

  private def doSearch: Unit = {
    eventInfos(Seq())
    if (keyword.get.isDefined) {
      val keys = keyword.get.get
      val resultSet = keys.trim.split(' ').map(searchOneWord(_))
      if (!resultSet.isEmpty) {
        val first = resultSet.head
        val tail = resultSet.tail
        val allEvents = resultSet.foldLeft(first)((res, next) => res.intersect(next))
        logger.info(s"searched ${allEvents.size}")
        eventInfos(allEvents)
      }
    }
  }

  private def searchOneWord(word: String): Seq[PuluoEventInfo] = {
    val res = DaoApi.getInstance().eventInfoDao().findEventInfo(word)
    logger.info(s"search ${word} for event info returns ${res.size}")
    res
  }
}