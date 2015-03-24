package com.puluo.api.event
import net.liftweb.http.rest.RestHelper
import net.liftweb.http.JsonResponse
import com.puluo.api.util.PuluoSession
import com.puluo.api.util.SessionInfo
import net.liftweb.http.js.JsExp.strToJsExp
import com.puluo.api.util.PuluoResponseFactory
import com.puluo.api.result.EventRegistrationResult
import com.puluo.api.result.EventDetailResult
import com.puluo.api.result.EventMemoryResult
import com.puluo.api.result.EventSearchResult

object PuluoEventAPI extends RestHelper {
  serve {
    case "events" :: "payment" :: eventUUID :: Nil Get _ => {//FIXME: should be POST?
      PuluoResponseFactory.createDummyJSONResponse(EventRegistrationResult.dummy().toJson())
    }
    case "events" :: "detail" :: eventUUID :: Nil Get _ => {
      PuluoResponseFactory.createDummyJSONResponse(EventDetailResult.dummy().toJson())
    }
    case "events" :: "memory" :: Nil Post _ => {
      PuluoResponseFactory.createDummyJSONResponse(EventMemoryResult.dummy().toJson())
    }
    case "events" :: "search" :: Nil Post _ => {
      PuluoResponseFactory.createDummyJSONResponse(EventSearchResult.dummy().toJson())
    }
    
    
  }
}