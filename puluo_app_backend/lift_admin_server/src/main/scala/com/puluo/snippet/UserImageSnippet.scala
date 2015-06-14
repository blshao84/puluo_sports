package com.puluo.snippet
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
import com.puluo.dao.impl.DaoApi
import com.puluo.entity.impl.PuluoEventInfoImpl
import com.puluo.entity.impl.PuluoEventPosterImpl
import com.puluo.enumeration.PuluoEventPosterType
import org.joda.time.DateTime
import com.puluo.entity.impl.PuluoEventMemoryImpl

object ImageUsage extends Enumeration {
  type ImageUsage = Value
  val UserIcon = Value("用户头像")
  val EventIcon = Value("课程ICON")
  val EventPoster = Value("课程精选")
  val EventMemory = Value("课程集锦")

  val usageOptions = ("", "") :: values.map(v => (v.toString(), v.toString())).toList
}
class UserImageSnippet extends Loggable {
  object usages extends RequestVar[Map[String, ImageUsage.ImageUsage]](Map.empty)
  object usageIDs extends RequestVar[Map[String, String]](Map.empty)
  def paginator = {
    val user = PuluoAdmin.currentUser.get
    val col = UserImage.createdAt
    val p = new SortedChineseMapperPaginator(UserImage, col, (col.dbColumnName, col)) {
      override def isAscending = false
      override def itemsPerPage = 30
    }
    p.constantParams = Seq(By(UserImage.mobile, user.mobile.get))
    p
  }

  def paginate = paginator.paginate _

  def render = {
    val imagesToRender: Seq[UserImage] = paginator.page
    ".image-results *" #> imagesToRender.zipWithIndex.map(em => {
      val (image, index) = em
      val imageUUID = image.imageUUID
      val currentUsage = usages.get.get(imageUUID)
      val currentUsageID = usageIDs.get.get(imageUUID)
      "#image-link [src]" #> Configurations.imgHttpLink(image.imageUUID, "!small") &
        "#image-name *" #> image.name.get &
        "#image-uuid *" #> imageUUID &
        ".image-usage-id *" #> SHtml.ajaxText(currentUsageID.getOrElse(""), id => {
          if (!id.trim().isEmpty()) {
            val newUsageIDs = usageIDs.get ++ Map(imageUUID -> id)
            usageIDs(newUsageIDs)
          }
          JsCmds.Noop
        }) &
        ".image-usage *" #> SHtml.ajaxSelect(ImageUsage.usageOptions, Empty, s => {
          if (!s.isEmpty()) {
            val newUsages = usages.get ++ Map(imageUUID -> ImageUsage.withName(s))
            usages(newUsages)
          }
          JsCmds.Noop
        }) &
        "#image-update" #> SHtml.ajaxButton("更新用途", () => updateImageUsage(imageUUID)) &
        "#image-delete" #> SHtml.ajaxButton("删除", () => {
          if (image.id.get >= 0) {
            image.delete_!
            val res = PuluoService.image.deleteImage(image.imageUUID);
            if (!res.isSuccess()) {
              logger.error(s"image ${image.imageUUID} is not deleted from the server")
            }
          }
          JsCmds.Reload
        })
    })
  }

  private def updateImageUsage(imgUUID: String) =
    (usages.get.get(imgUUID), usageIDs.get.get(imgUUID)) match {
      case (Some(usage), Some(usageID)) => JsCmds.Noop //doUpdateImage(usage, usageID, imgUUID)
      case _ => JsCmds.Noop
    }

  private def doUpdateImage(usage: ImageUsage.ImageUsage, usageID: String, imgUUID: String) = {
    val dsi = DaoApi.getInstance()
    usage match {
      case ImageUsage.UserIcon => {
        val user = dsi.userDao().getByUUID(usageID)
        if (user == null) JsCmds.Alert(s"更新用户${usageID}不存在") else {
          val newUser = dsi.userDao().updateProfile(user, null, null, imgUUID, null, null, null, null, null, null, null, null)
          if (newUser == null) JsCmds.Alert("更新用户头像失败") else JsCmds.Alert("成功更新用户头像")
        }
      }
      case ImageUsage.EventIcon => {
        val info = dsi.eventInfoDao().getEventInfoByUUID(usageID)
        if (info == null) JsCmds.Alert(s"更新的活动信息不存在") else {
          val newEventInfo = new PuluoEventInfoImpl(
            info.eventInfoUUID(),
            info.name(),
            info.description,
            info.coachName(),
            info.coachUUID(),
            imgUUID,
            info.details,
            info.duration, info.level, info.eventType());
          val success = dsi.eventInfoDao().updateEventInfo(newEventInfo)
          if (success) JsCmds.Alert("成功更新用户头像") else JsCmds.Alert("更新用户头像失败")
        }
      }
      case ImageUsage.EventPoster => {
        val info = dsi.eventInfoDao().getEventInfoByUUID(usageID)
        if (info == null) JsCmds.Alert(s"更新的活动信息不存在") else {
          val poster = new PuluoEventPosterImpl(
            UUID.randomUUID().toString(),
            imgUUID,
            usageID, DateTime.now, 
            PuluoEventPosterType.POSTER,0)//FIXME: should fix this!!
          dsi.eventPosterDao().saveEventPhoto(poster)
        }
      }
      case ImageUsage.EventMemory => {
        val event = dsi.eventDao().getEventByUUID(usageID)
        if (event == null) JsCmds.Alert(s"更新的课程信息不存在") else {
          val mem = new PuluoEventMemoryImpl(
            UUID.randomUUID().toString(),
            imgUUID,
            usageID, "", "")
          dsi.eventMemoryDao().saveEventMemory(mem)
        }
      }
    }
  }
}