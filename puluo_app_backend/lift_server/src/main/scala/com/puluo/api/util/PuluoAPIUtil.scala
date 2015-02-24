package com.puluo.api.util

import net.liftweb.http.LiftResponse
import net.liftweb.http.S

trait PuluoAPIUtil {
  
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

    val error = requiredParams.collectFirst {
      case (key, error) if (!S.param(key).isDefined) => error
    }
    if (error.isDefined) PuluoResponseFactory.createErrorResponse(error.get)
    else {
      val params = requiredParams.map {
        case (key, value) => (key, S.param(key).get)
      }
      callAPI(params)
    }

  }
}