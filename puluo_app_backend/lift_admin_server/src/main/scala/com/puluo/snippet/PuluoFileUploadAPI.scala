package com.puluo.snippet

import net.liftweb.http.rest.RestHelper
import net.liftweb.common.Loggable
import net.liftweb.http.JsonResponse
import com.puluo.service.PuluoService
import net.liftweb.http.js.JsExp.strToJsExp
import java.util.UUID
import com.puluo.util.Strs
import com.puluo.snippet.util.JQueryFileUploadResultSet
import com.puluo.snippet.util.JQueryFileUploadResult
import com.puluo.snippet.util.FileUploadSuccess
import com.puluo.entity.PuluoAdmin
import com.puluo.entity.UserImage

object PuluoFileUploadAPI extends RestHelper with Loggable {
  serve {
    case "upload" :: "image" :: Nil Post req => {
      val admin = PuluoAdmin.currentUser.get
      val userUUID = admin.puluoUser.userUUID()
      logger.info(s"user ${userUUID} receiving file ...")
      
      val results = req.uploadedFiles.flatMap { fph =>
        val uuid = UUID.randomUUID().toString()
        val newFileName = replaceName(uuid, fph.fileName)
        if (newFileName != fph.fileName) {
          val res = PuluoService.image.saveImage(fph.file, newFileName)
          if(res.isSuccess()){
          UserImage.create.mobile(admin.mobile.get).name(fph.fileName).uuid(newFileName).save
          Some((fph.fileName, res))
          } else None
        } else {
          logger.error(s"ignore uploading file ${fph.fileName}")
          None
        }
      } 
      
      val json = JQueryFileUploadResultSet(
          results.map { case (k, v) => JQueryFileUploadResult(k, v) } toSeq).jsonValue
      JsonResponse(json)
    }

  }

  private def replaceName(newName: String, oldName: String) = {
    val lastDot = oldName.lastIndexOf('.')
    if (lastDot > 0) {
      val fileType = oldName.subSequence(lastDot, oldName.length())
      Strs.join(newName, fileType)
    } else {
      logger.error(s"unexpected file name ${oldName}, doesn't replace it with uuid")
      oldName
    }
  }

}