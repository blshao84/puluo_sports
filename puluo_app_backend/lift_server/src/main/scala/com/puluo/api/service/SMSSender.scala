package com.puluo.api.service

import scala.collection.JavaConversions._
import com.puluo.api.util.PuluoResponseFactory
import java.util.HashMap
import com.puluo.api.util.ErrorResponseResult
import net.liftweb.common.Loggable

trait SMSSender extends Loggable{
   protected def doSendSMS(params: Map[String, String]) = {
    val smsType = PuluoSMSType.valueOf(params("sms_type"))
    if (smsType == null) {
      PuluoResponseFactory.createErrorResponse(ErrorResponseResult(16).copy(message = "smsType"))
    } else {
      val mobile = params("mobile")
      val api = new SMSServiceAPI(smsType, mobile,params)
      logger.info(String.format("executing api:mobile=%s,smsType=%s", mobile, smsType))
      api.execute()
      PuluoResponseFactory.createJSONResponse(api)
    }
  }
}