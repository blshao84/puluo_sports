package com.puluo.api

import net.liftweb.http.rest.RestHelper
import net.liftweb.common.Loggable
import net.liftweb.http.FileParamHolder
import net.liftweb.http.JsonResponse
import com.puluo.service.PuluoImageService

object PuluoFileUploader extends RestHelper with Loggable {
  serve {
    case "upload" :: "image" :: Nil Post req => {
      val results = req.uploadedFiles.map(fph => saveImage(fph))
      val json = s"[${results.mkString(",")}]"
      logger.info(s"上传文件结果:\n$json")
      JsonResponse(json)
    }
  }

  def saveImage(fph: FileParamHolder) = {
    logger.info("receiving file ...")
    PuluoImageService.saveImage(fph.file, fph.fileName);
  }
}