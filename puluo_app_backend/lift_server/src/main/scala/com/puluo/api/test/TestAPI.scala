package com.puluo.api.test

import net.liftweb.http.rest.RestHelper
import net.liftweb.http.JsonResponse
import com.puluo.api.ApiTest
import com.puluo.api.util.PuluoResponseFactory
import net.liftweb.http.S
import net.liftweb.json.Printer
import net.liftweb.json.JsonDSL
import net.liftweb.json.JsonAST

object TestAPI extends RestHelper {
  serve {
    case "test" :: _ Get _ => {
      val api = new ApiTest("success")
      PuluoResponseFactory.createJSONResponse(api)
    }
    case "test" :: _ Post _ => {
      val jmsg = S.request.get.json.get \ "msg"
      val msg = Printer.pretty(JsonAST.render(jmsg))
      val api = new ApiTest(msg.toString)
      PuluoResponseFactory.createJSONResponse(api)
    }
  }
}