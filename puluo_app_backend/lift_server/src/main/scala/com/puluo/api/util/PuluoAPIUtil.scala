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
    val params = PuluoResponseFactory.createParamMap(requiredParams.keys.toSeq)
    val error = requiredParams.collectFirst {
      case (key, error) if (!params.get(key).isDefined) => error
    }
    if (error.isDefined) PuluoResponseFactory.createErrorResponse(error.get)
    else {
      callAPI(params)
    }

  }
}