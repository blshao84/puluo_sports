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
import org.joda.time.DateTime

class EventUpdateSnippet extends PuluoSnippetUtil with Loggable {
  object uuid extends RequestVar[Option[String]](None)
  object event extends SessionVar[Option[PuluoEvent]](None)

  object info extends RequestVar[Option[String]](None)
  object loc extends RequestVar[Option[String]](None)
  object status extends RequestVar[Option[String]](None)
  object capacity extends RequestVar[Option[Int]](None)
  object year extends RequestVar[Option[Int]](None)
  object month extends RequestVar[Option[Int]](None)
  object day extends RequestVar[Option[Int]](None)
  object hour extends RequestVar[Option[Int]](None)
  object price extends RequestVar[Option[Double]](None)
  object discount extends RequestVar[Option[Double]](None)
  object hottest extends RequestVar[Option[String]](None)

  def render = {
    val uuidFromURL = S.param("uuid")
    val eventUUID = event.map(_.eventUUID()).getOrElse("")
    val eventStatus = EventStatus.values.map(_.name)
    val hot = Seq("0", "1")
    loadEvent
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
      hidDiv("body") {
        !event.isDefined
      } &
      "#id *" #> eventUUID &
      "#info-uuid" #> renderText(info) &
      "#loc-uuid" #> renderText(loc) &
      "#capacity" #> renderInt(capacity) &
      "#year" #> renderInt(year) &
      "#month" #> renderInt(month) &
      "#day" #> renderInt(day) &
      "#hour" #> renderInt(hour) &
      "#price" #> renderDouble(price) &
      "#discount-price" #> renderDouble(discount) &
      "#status" #> renderSimpleSelect(eventStatus, status) &
      "#hottest" #> renderSimpleSelect(hot, hottest) &
      "#update" #> SHtml.ajaxButton("更新", () => doUpdate)

    //"#body *" #> event.map(_.eventUUID()).getOrElse("not found")

  }

  private def loadEvent = {
    if(event.get.isDefined){
      val e = event.get.get
      if(e.eventInfo()!=null) info(Some(e.eventInfo().eventInfoUUID()))
      if(e.eventLocation()!=null) loc(Some(e.eventLocation().locationId()))
      if(e.statusName()!=null) status(Some(e.statusName()))
      if(e.capatcity()!=0) capacity(Some(e.capatcity()))
      if(e.eventTime()!=null) {
        val dt = e.eventTime()
        year(Some(dt.getYear()))
        month(Some(dt.getMonthOfYear()))
        day(Some(dt.getDayOfMonth()))
        hour(Some(dt.getHourOfDay()))
      }
      if(e.price()!=0.0) price(Some(e.originalPrice()))
      if(e.discountedPrice()!=0.0) discount(Some(e.discountedPrice()))
      if(e.hottest()!=0) hottest(Some(e.hottest().toString))
    }
  }
  private def doUpdate = {

    val dsi = DaoApi.getInstance()
    val eventDao = dsi.eventDao()
    val infoDao = dsi.eventInfoDao()
    val locDao = dsi.eventLocationDao()
    val infoEntity = infoDao.getEventInfoByUUID(info.getOrElse(""))
    val locEntity = locDao.getEventLocationByUUID(loc.getOrElse(""))
    if (infoEntity != null) {
      if (locEntity != null) {
        if (year.isDefined && month.isDefined && day.isDefined && hour.isDefined) {
          val eventTime = TimeUtils.parseDateTime(
            s"${year.get.get}-${month.get.get}-${day.get.get} ${hour.get.get}:0:0")
          if (eventTime != null) {
            val eventStatus = EventStatus.valueOf(status.get.get)
            val eventHottest = hottest.map(_.toInt).getOrElse(0)
            val eventCapacity = capacity.getOrElse(0)
            val eventPrice:java.lang.Double = price.get.get
            val eventDiscount:java.lang.Double = discount.get.get
            val newEvent = new PuluoEventImpl(
                event.get.get.eventUUID(),
                eventTime,
                eventStatus,
                0,
                eventCapacity,
                eventPrice,
                eventDiscount,
                infoEntity.eventInfoUUID(),
                locEntity.locationId(),
                eventHottest)
            println(newEvent.toString())
            val success = eventDao.upsertEvent(newEvent)
            if(success){
              JsCmds.Alert("成功更新课程")
            } else JsCmds.Alert("保存课程时发生错误")
          } else JsCmds.Alert(s"课程时间格式有误")
        } else JsCmds.Alert(s"课程时间不完整")
      } else JsCmds.Alert(s"活动场地ID:${loc.getOrElse("")}不存在")
    } else JsCmds.Alert(s"活动信息ID:${info.getOrElse("")}不存现在")
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