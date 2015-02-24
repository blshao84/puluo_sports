package com.puluo.api.message
import net.liftweb.http.rest.RestHelper
import net.liftweb.http.JsonResponse
import com.puluo.api.util.PuluoSession
import com.puluo.api.util.SessionInfo
import net.liftweb.http.js.JsExp.strToJsExp
import com.puluo.api.util.PuluoResponseFactory
import com.puluo.api.result.SendMessageResult
import com.puluo.api.result.ListMessageResult

object PuluoMessageAPI extends RestHelper {
  serve {
    case "users" :: "message" :: "send" :: Nil Put _ => {
      PuluoResponseFactory.createDummyJSONResponse(SendMessageResult.dummy().toJson())
    }
    case "users" :: "messages" :: Nil Post _ => {
      PuluoResponseFactory.createDummyJSONResponse(ListMessageResult.dummy().toJson())
    }
    
        case "dummy" ::"users" :: "message" :: "send" :: Nil Put _ => {
      PuluoResponseFactory.createDummyJSONResponse(SendMessageResult.dummy().toJson())
    }
    case "dummy" ::"users" :: "messages" :: Nil Post _ => {
      PuluoResponseFactory.createDummyJSONResponse(ListMessageResult.dummy().toJson())
    }
  }
}