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

  def createJSONResponse(api:PuluoAPI, code: Int = 200): LiftResponse = {
    val jvalue = parse(api.result)
    JsonResponse(jvalue, requestHeader, JsonResponse.cookies, code)
  }

  private def requestHeader = requestIdHeader :: Nil

  private def requestIdHeader = {
    val uuid = UUID.randomUUID().toString()
    ("Request-ID", uuid)
  }
}