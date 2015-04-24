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
import com.puluo.entity.impl.PuluoEventInfoImpl
import com.puluo.enumeration.PuluoEventCategory
import com.puluo.enumeration.PuluoEventLevel
import com.puluo.entity.PuluoUser
import com.puluo.dao.PuluoDSI
import com.puluo.entity.impl.PuluoEventPosterImpl

class EventInfoUpdateSnippet extends PuluoSnippetUtil with Loggable {
  object uuid extends RequestVar[Option[String]](None)
  object info extends SessionVar[Option[PuluoEventInfo]](None)
  object name extends RequestVar[Option[String]](None)
  object desc extends RequestVar[Option[String]](None)
  object coachUUID extends RequestVar[Option[String]](None)
  object details extends RequestVar[Option[String]](None)
  object duration extends RequestVar[Option[Int]](None)
  object level extends RequestVar[Option[String]](None)
  object infoType extends RequestVar[Option[String]](None)

  object poster1 extends RequestVar[Option[String]](None)
  object poster2 extends RequestVar[Option[String]](None)
  object poster3 extends RequestVar[Option[String]](None)
  object poster4 extends RequestVar[Option[String]](None)
  object poster5 extends RequestVar[Option[String]](None)

  def render = {
    val allTypes = PuluoEventCategory.values.map(_.name()).toSeq
    val allLevels = PuluoEventLevel.values.map(_.name()).toSeq
    val uuidFromURL = S.param("uuid")
    val (infoUUID, coachName, coachThumb) =
      if (!info.get.isDefined) {
        ("", "", "")
      } else {
        val i = info.get.get
        (i.eventInfoUUID(), i.coachName(), i.coachThumbnail())
      }
    loadInfo
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
        !info.isDefined
      } &
      "#id *" #> infoUUID &
      "#name" #> renderText(name) &
      "#desc" #> renderText(desc) &
      "#coach-uuid" #> renderText(coachUUID) &
      "#coach-name *" #> coachName &
      "#coach-thumbnail [src]" #> coachThumb &
      "#details" #> renderTextArea(details) &
      "#duration" #> renderInt(duration) &
      "#type" #> renderSimpleSelect(allTypes, infoType) &
      "#level" #> renderSimpleSelect(allLevels, level) &
      "#poster1" #> renderText(poster1) &
      "#poster2" #> renderText(poster2) &
      "#poster3" #> renderText(poster3) &
      "#poster4" #> renderText(poster4) &
      "#poster5" #> renderText(poster5) &
      "#update" #> SHtml.ajaxButton("更新", () => doUpdate)
  }

  private def savePoster(eventInfoUUID: String, poster: RequestVar[Option[String]]): Boolean = {
    val posterDao = DaoApi.getInstance().eventPosterDao()
    if (poster.isDefined) {
      val posterUUID = poster.get.get
      val ui = UserImage.findByUUID(posterUUID)
      if (ui.isDefined) {
        val imageName = ui.get.imageUUID
        val imageUUID = UUID.randomUUID().toString()
        val newEventPoster = new PuluoEventPosterImpl(imageUUID, imageName, "", eventInfoUUID)
        //we always save a new image link for event info
        posterDao.saveEventPhoto(newEventPoster)
      } else true
    } else true
  }

  private def doUpdate = {
    val dsi = DaoApi.getInstance();
    val (coachName, coachThumb) = if (coachUUID.get.isDefined) {
      val user = dsi.userDao().getByUUID(coachUUID.get.get)
      if (user != null) {
        (user.name(), user.thumbnail())
      } else ("", "")
    } else ("", "")
    val t = try {
      infoType.get.map(PuluoEventCategory.valueOf(_)).getOrElse(PuluoEventCategory.Others)
    } catch {
      case e: Exception => PuluoEventCategory.Others
    }
    val l = try {
      level.get.map(PuluoEventLevel.valueOf(_)).getOrElse(PuluoEventLevel.Level1)
    } catch {
      case e: Exception => PuluoEventLevel.Level1
    }
    val infoUUID = info.get.get.eventInfoUUID()
    val newInfo = new PuluoEventInfoImpl(
      infoUUID,
      name.getOrElse(""),
      desc.getOrElse(""),
      coachName,
      coachUUID.getOrElse(""),
      coachThumb,
      details.getOrElse(""),
      duration.getOrElse(0),
      l, t);
    val success = dsi.eventInfoDao().upsertEventInfo(newInfo)
    val msg = if (success) {
      val successSavePoster = (
        savePoster(infoUUID, poster1) &&
        savePoster(infoUUID, poster2) &&
        savePoster(infoUUID, poster3) &&
        savePoster(infoUUID, poster4) &&
        savePoster(infoUUID, poster5))
      if (successSavePoster) {
        "成功更新活动信息"
      } else "成功更新活动信息，但保存活动官方图片时出错"
    } else "更新活动时发生错误，请检查您的输入"
    JsCmds.Alert(msg)
  }
  private def loadInfo = {
    if (info.get.isDefined) {
      val theInfo = info.get.get
      if (theInfo.name() != null) name(Some(theInfo.name()))
      if (theInfo.description() != null) desc(Some(theInfo.description()))
      if (theInfo.coachUUID() != null) coachUUID(Some(theInfo.coachUUID()))
      if (theInfo.details() != null) details(Some(theInfo.description()))
      if (theInfo.duration() != 0) duration(Some(theInfo.duration()))
      if (theInfo.level() != null) level(Some(theInfo.level().name()))
      if (theInfo.eventType() != null) infoType(Some(theInfo.eventType().name()))
    }
  }

  private def doAddEvent = {
    val newUUID = UUID.randomUUID().toString()
    info(Some(new PuluoEventInfoImpl(newUUID)))
  }
  private def doSearch = {
    val infoUUID = uuid.get.getOrElse(S.param("uuid").getOrElse(""))
    info(None)
    if (!Strs.isEmpty(infoUUID)) {
      val i = DaoApi.getInstance().eventInfoDao().getEventInfoByUUID(infoUUID)
      if (i != null) {
        info(Some(i))
      }
    }
  }
}