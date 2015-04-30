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
import com.puluo.api.social.ListFriendRequestsAPI

object PuluoGraphAPI extends RestHelper with PuluoAPIUtil with Loggable {
  serve {
    case "users" :: "friends" :: mobileOrUUID :: Nil Post _ => {
      val api = new ListFriendsAPI(mobileOrUUID)
      safeRun(api)
      PuluoResponseFactory.createJSONResponse(api)
    }

    case "users" :: "friends" :: "request" :: "send" :: Nil Post _ => {
      callWithAuthParam(Map(
        "user_uuid" -> ErrorResponseResult(15).copy(message = "user_uuid")))(doRequestFriend)
    }
    case "users" :: "friends" :: "request" :: "delete" :: Nil Post _ => {
      callWithAuthParam(Map(
        "user_uuid" -> ErrorResponseResult(15).copy(message = "user_uuid")))(doDeleteFriend)
    }
    case "users" :: "friends" :: "request" :: "deny" :: Nil Post _ => {
      callWithAuthParam(Map(
        "user_uuid" -> ErrorResponseResult(15).copy(message = "user_uuid")))(doDenyFriend)
    }
    case "users" :: "friends" :: "request" :: "approve" :: Nil Post _ => {
      callWithAuthParam(Map(
        "user_uuid" -> ErrorResponseResult(15).copy(message = "user_uuid")))(doApproveFriend)
    }
    case "users" :: "friends" :: "request" :: "get" :: Nil Post _ => doGetFriendRequest
  }
  
  private def doGetFriendRequest = {
    val params = PuluoResponseFactory.createParamMap(Seq("token"))
    val token = params("token")
    val session = PuluoSessionManager.getSession(token)
    val uuid = session.userUUID()
    val api = new ListFriendRequestsAPI(uuid)
    safeRun(api)
    PuluoResponseFactory.createJSONResponse(api)
  }

  private def doFriendAPI(params: Map[String, String], apiType: String) = {
    val toUserUUID = params("user_uuid")
    val token = params("token")
    val session = PuluoSessionManager.getSession(token)
    val fromUserUUID = session.userUUID()
    logger.info(String.format(
      "create %sFriendAPI, token=%s,session=%s,to_user=%s,from_user=%s",
      apiType, token, session.sessionID(), toUserUUID, fromUserUUID))
    val (api, code) = apiType match {
      case "Request" => (new RequestFriendAPI(toUserUUID, fromUserUUID), 201)
      case "Delete" => (new DeleteFriendAPI(toUserUUID, fromUserUUID), 200)
      case "Deny" => (new DenyFriendAPI(fromUserUUID, toUserUUID), 200)
      case "Approve" => (new ApproveFriendAPI(fromUserUUID, toUserUUID), 200)
    }
    safeRun(api)
    PuluoResponseFactory.createJSONResponse(api, code)
  }

  private def doRequestFriend(params: Map[String, String]) = doFriendAPI(params, "Request")
  private def doDeleteFriend(params: Map[String, String]) = doFriendAPI(params, "Delete")
  private def doDenyFriend(params: Map[String, String]) = doFriendAPI(params, "Deny")
  private def doApproveFriend(params: Map[String, String]) = doFriendAPI(params, "Approve")
}