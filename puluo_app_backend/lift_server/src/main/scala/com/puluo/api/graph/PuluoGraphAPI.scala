package com.puluo.api.graph
import net.liftweb.http.rest.RestHelper
import net.liftweb.http.JsonResponse
import com.puluo.api.util.PuluoSession
import com.puluo.api.util.SessionInfo
import net.liftweb.http.js.JsExp.strToJsExp

object PuluoGraphAPI extends RestHelper {
  serve {
    case "users" :: "friends" :: Nil Get _ => {
      ???
    }
    case "users" :: "friends" :: "request" :: Nil Put _ => {
      ???
    }
    case "users" :: "friends" :: "delete" :: Nil Post _ => {
      ???
    }
    case "users" :: "friends" :: "deny" :: Nil Post _ => {
      ???
    }
    case "users" :: "friends" :: "approve" :: Nil Post _ => {
      ???
    }
  }
}