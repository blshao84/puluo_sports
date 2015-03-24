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
import com.puluo.api.social.ListFriendsAPI
import com.puluo.api.util.PuluoAPIUtil
import net.liftweb.common.Loggable
import com.puluo.api.util.ErrorResponseResult
import com.puluo.session.PuluoSessionManager
import com.puluo.api.social.RequestFriendAPI
import com.puluo.api.social.DeleteFriendAPI
import com.puluo.api.social.DenyFriendAPI
import com.puluo.api.social.ApproveFriendAPI

object PuluoGraphAPI extends RestHelper with PuluoAPIUtil with Loggable {
  serve {
    case "users" :: "friends" :: mobileOrUUID :: Nil Get _ => {
      val api = new ListFriendsAPI(mobileOrUUID)
      safeRun(api)
      PuluoResponseFactory.createJSONResponse(api)
    }
    case "users" :: "friends" :: "request" :: Nil Put _ => {
      callWithParam(Map(
        "user_uuid" -> ErrorResponseResult(15).copy(message = "user_uuid"),
        "token" -> ErrorResponseResult(15).copy(message = "token")))(doRequestFriend)
    }
    case "users" :: "friends" :: "delete" :: Nil Post _ => {
      callWithParam(Map(
        "user_uuid" -> ErrorResponseResult(15).copy(message = "user_uuid"),
        "token" -> ErrorResponseResult(15).copy(message = "token")))(doDeleteFriend)
    }
    case "users" :: "friends" :: "deny" :: Nil Post _ => {
      callWithParam(Map(
        "user_uuid" -> ErrorResponseResult(15).copy(message = "user_uuid"),
        "token" -> ErrorResponseResult(15).copy(message = "token")))(doDenyFriend)
    }
    case "users" :: "friends" :: "approve" :: Nil Post _ => {
      callWithParam(Map(
        "user_uuid" -> ErrorResponseResult(15).copy(message = "user_uuid"),
        "token" -> ErrorResponseResult(15).copy(message = "token")))(doApproveFriend)
    }
  }

  private def doFriendAPI(params: Map[String, String], apiType: String) = {
    val toUserUUID = params("user_uuid")
    val token = params("token")
    val session = PuluoSessionManager.getSession(token)
    val fromUserUUID = session.userUUID()
    logger.info(String.format(
      "create %sFriendAPI, token=%s,session=%s,to_user=%s,from_user=%s",
      apiType,token, session.sessionID(), toUserUUID, fromUserUUID))
    val (api, code) = apiType match {
      case "Request" => (new RequestFriendAPI(toUserUUID, fromUserUUID), 201)
      case "Delete" => (new DeleteFriendAPI(toUserUUID, fromUserUUID), 200)
      case "Deny" => (new DenyFriendAPI(toUserUUID, fromUserUUID), 200)
      case "Approve" => (new ApproveFriendAPI(toUserUUID, fromUserUUID), 200)
    }
    safeRun(api)
    PuluoResponseFactory.createJSONResponse(api, code)
  }

  private def doRequestFriend(params: Map[String, String]) = doFriendAPI(params, "Request")
  private def doDeleteFriend(params: Map[String, String]) = doFriendAPI(params, "Delete")
  private def doDenyFriend(params: Map[String, String]) = doFriendAPI(params, "Deny")
  private def doApproveFriend(params: Map[String, String]) = doFriendAPI(params, "Approve")
}