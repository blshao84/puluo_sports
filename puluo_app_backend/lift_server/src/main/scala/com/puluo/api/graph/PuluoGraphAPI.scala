package com.puluo.api.graph
import net.liftweb.http.rest.RestHelper
import net.liftweb.http.JsonResponse
import com.puluo.api.util.PuluoSession
import com.puluo.api.util.SessionInfo
import net.liftweb.http.js.JsExp.strToJsExp
import com.puluo.api.util.PuluoResponseFactory
import com.puluo.api.result.ListFriendsResult
import com.puluo.api.result.RequestFriendResult
import com.puluo.api.result.DeleteFriendResult
import com.puluo.api.result.DenyFriendResult
import com.puluo.api.result.ApproveFriendResult

object PuluoGraphAPI extends RestHelper {
  serve {
    case "users" :: "friends" :: Nil Get _ => {
      PuluoResponseFactory.createDummyJSONResponse(ListFriendsResult.dummy().toJson())
    }
    case "users" :: "friends" :: "request" :: Nil Put _ => {
      PuluoResponseFactory.createDummyJSONResponse(RequestFriendResult.dummy().toJson())
    }
    case "users" :: "friends" :: "delete" :: Nil Post _ => {
      PuluoResponseFactory.createDummyJSONResponse(DeleteFriendResult.dummy().toJson())
    }
    case "users" :: "friends" :: "deny" :: Nil Post _ => {
      PuluoResponseFactory.createDummyJSONResponse(DenyFriendResult.dummy().toJson())
    }
    case "users" :: "friends" :: "approve" :: Nil Post _ => {
      PuluoResponseFactory.createDummyJSONResponse(ApproveFriendResult.dummy().toJson())
    }
    
     
  }
}