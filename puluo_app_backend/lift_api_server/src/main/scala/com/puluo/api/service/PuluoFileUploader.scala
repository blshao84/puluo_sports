package com.puluo.api.service

import net.liftweb.http.rest.RestHelper
import net.liftweb.common.Loggable
import net.liftweb.http.JsonResponse
import com.puluo.service.PuluoService
import net.liftweb.http.js.JsExp.strToJsExp

object PuluoFileUploader extends RestHelper with Loggable {
  serve {
    case "services" :: "image" :: Nil Post req => {
      logger.info("receiving file ...")
      val results = req.uploadedFiles.map { fph =>
        PuluoService.image.saveImage(fph.file, fph.fileName)
      }
      val json = s"[${results.mkString(",")}]"
      logger.info(s"file upload results:\n$json")
      JsonResponse(json)
    }

  }

}