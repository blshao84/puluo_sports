package com.puluo.api.test

import net.liftweb.http.rest.RestHelper
import net.liftweb.http.JsonResponse
import com.puluo.api.util.PuluoResponseFactory
import net.liftweb.http.S
import net.liftweb.json.Printer
import net.liftweb.json.JsonDSL
import net.liftweb.json.JsonAST
import com.puluo.api.util.PuluoAPIUtil

object DemoAPI extends RestHelper with PuluoAPIUtil{
  serve {
    case "ping" :: _ Get _ => {
      PuluoResponseFactory.createDummyJSONResponse("{\"msg\":\"hello world\"}")
    }
    case "test" :: _ Get _ => {
      val api = new com.puluo.api.DemoAPI("success")
      safeRun(api)
      PuluoResponseFactory.createJSONResponse(api)
    }
    case "test" :: _ Post _ => {
      val jmsg = S.request.get.json.get \ "msg"
      val msg = Printer.pretty(JsonAST.render(jmsg))
      val api = new com.puluo.api.DemoAPI(msg.toString)
      PuluoResponseFactory.createJSONResponse(api)
    }
  }
}