package com.puluo.api.util
import scala.collection.JavaConversions._
import net.liftweb.http.LiftResponse
import net.liftweb.json.JsonAST
import java.util.UUID
import net.liftweb.http.JsonResponse
import net.liftweb.json.JsonAST.JValue
import net.liftweb.json._
import com.puluo.api.PuluoAPI

object PuluoResponseFactory {

  implicit val formats = DefaultFormats

  def createErrorResponse(response: ErrorResponseResult): LiftResponse = {
    val jvalue = Serialization.write(response)
    JsonResponse(jvalue, requestHeader, JsonResponse.cookies, 200)
  }

  def createJSONResponse(api: PuluoAPI[_, _], code: Int = 200): LiftResponse = {
    val jvalue = parse(api.result)
    JsonResponse(jvalue, requestHeader, JsonResponse.cookies, code)
  }

  def createDummyJSONResponse(jresult: String, code: Int = 200): LiftResponse = {
    val jvalue = parse(jresult)
    JsonResponse(jvalue, requestHeader, JsonResponse.cookies, code)
  }

  private def requestHeader = requestIdHeader :: Nil//accessControlHeader

  private def requestIdHeader = {
    val uuid = UUID.randomUUID().toString()
    ("Request-ID", uuid)
  }

  private def accessControlHeader =
    List(
      ("Access-Control-Allow-Origin", "*"),
      ("Access-Control-Allow-Credentials", "true"),
      ("Access-Control-Allow-Methods", "GET, PUT, POST, DELETE, HEAD, OPTIONS"),
      ("Access-Control-Allow-Headers", "X-Requested-With,Expires,Set-Cookie,Request-ID,Content-Length,Cache-Control,Content-Type,Pragma,Date,X-Lift-Version,X-Frame-Options,Server"))
}

case class ErrorResponseResult(val id: String, val message: String, val url: String)