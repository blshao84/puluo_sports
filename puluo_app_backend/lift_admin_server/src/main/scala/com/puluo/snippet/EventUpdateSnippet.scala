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
import com.puluo.entity.impl.PuluoEventImpl

class EventUpdateSnippet extends PuluoSnippetUtil with Loggable {
  object uuid extends RequestVar[Option[String]](None)
  object event extends SessionVar[Option[PuluoEvent]](None)

  def render = {
    val uuidFromURL = S.param("uuid")
    (if (uuidFromURL.isDefined) {
      doSearch
      "#search-panel" #> ""
    } else {
      "#uuid" #> renderText(uuid) &
        "#search" #> SHtml.ajaxButton("搜索", () => {
          doSearch
          JsCmds.Reload
        })
    }) &
    "#add" #> SHtml.ajaxButton("添加", () => {
      doAddEvent
      JsCmds.Reload
    }) &
      "#body *" #> event.map(_.eventUUID()).getOrElse("not found")

  }

  private def doAddEvent = {
    val newUUID = UUID.randomUUID().toString()
    event(Some(new PuluoEventImpl(newUUID)))
  }
  
  private def doSearch = {
    event(None)
    val eventUUID = uuid.get.getOrElse(S.param("uuid").getOrElse(""))
    logger.info(s"准备查询event ${eventUUID}")
    if (!Strs.isEmpty(eventUUID)) {
      val i = DaoApi.getInstance().eventDao().getEventByUUID(eventUUID)
      if (i != null) {
        event(Some(i))
      } else logger.info(s"could not find event ${eventUUID}")
    } else logger.info(s"${eventUUID} is empty")
  }
}