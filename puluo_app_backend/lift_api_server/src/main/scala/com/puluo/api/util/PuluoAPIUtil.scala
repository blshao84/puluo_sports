package com.puluo.api.util

import net.liftweb.http.LiftResponse
import net.liftweb.http.S
import com.puluo.api.PuluoAPI
import net.liftweb.common.Loggable
import com.puluo.result.ApiErrorResult

trait PuluoAPIUtil extends Loggable {

  /**
   * requiredParams:
   * 	* key: parameter name
   *  	* value: error message in case of missing
   * callAPI:
   * 	* functions that assumes all required params are ready and does the actual work
   */
  def callWithParam(
    requiredParams: Map[String, ErrorResponseResult])(
      callAPI: Map[String, String] => LiftResponse): LiftResponse = {
    val params = PuluoResponseFactory.createParamMap(requiredParams.keys.toSeq)
    val error = requiredParams.collectFirst {
      case (key, error) if (!params.get(key).isDefined) => error
    }
    if (error.isDefined) PuluoResponseFactory.createErrorResponse(error.get)
    else {
      callAPI(params)
    }

  }
  def callWithAuthParam(
    requiredParams: Map[String, ErrorResponseResult] = Map.empty)(
      callAPI: Map[String, String] => LiftResponse): LiftResponse = {
    callWithParam(requiredParams + ("token" -> ErrorResponseResult(15).copy(message = "token")))(callAPI)
  }

  def safeRun(api: PuluoAPI[_, _]): PuluoAPI[_, _] = {
    try {
      api.execute()
    } catch {
      case e: Throwable => {
        logger.error("error found")
        e.printStackTrace()
        api.setError(ApiErrorResult.getError(100))
      }
    }
    api
  }
  
  def getIntParam(params:Map[String,String],name:String, default:Int):Int = {
    try {
      params.get(name).map(_.toInt).getOrElse(default)
    }catch {
      case e:Exception => {
        logger.info(s"use default ${name} as ${default}")
        default
      }
    }
  }
}