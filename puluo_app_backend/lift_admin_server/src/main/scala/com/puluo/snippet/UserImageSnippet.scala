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

class UserImageSnippet extends Loggable {

  def paginator = {
    val user = PuluoAdmin.currentUser.get
    val col = UserImage.createdAt
    val p = new SortedChineseMapperPaginator(UserImage, col, (col.dbColumnName, col)) {
      override def isAscending = true
      override def itemsPerPage = 10
    }
    p.constantParams = Seq(By(UserImage.mobile, user.mobile.get))
    p
  }

 def paginate = paginator.paginate _

  def render = {
    val imagesToRender:Seq[UserImage] = paginator.page
    ".image-results *" #> imagesToRender.zipWithIndex.map(em => {
      val (image, index) = em
      "#image-link [src]" #> Strs.join(Configurations.imageServer,image.imageUUID,"!small") &
        "#image-name *" #> image.name.get &
        "#image-uuid *" #> image.imageUUID &
      "#image-delete" #> SHtml.ajaxButton("删除", () => {
        if (image.id.get >= 0) {
          image.delete_!
          val res = PuluoService.image.deleteImage(image.imageUUID);
          if(!res.isSuccess()){
            logger.error(s"image ${image.imageUUID} is not deleted from the server")
          }
        }
        JsCmds.Reload
      })
    })
  }
}