package com.puluo.api.service

import scala.collection.JavaConversions._
import com.puluo.api.util.PuluoResponseFactory
import java.util.HashMap
import com.puluo.api.util.ErrorResponseResult
import net.liftweb.common.Loggable
import com.puluo.api.util.PuluoAPIUtil

trait SMSSender extends PuluoAPIUtil with Loggable{
   protected def doSendSMS(params: Map[String, String]) = {
    val smsType = PuluoSMSType.valueOf(params("sms_type"))
    val mock = PuluoResponseFactory.createParamMap(Seq("mock")).values.headOption.map(_=="true").getOrElse(false)
    if (smsType == null) {
      PuluoResponseFactory.createErrorResponse(ErrorResponseResult(16).copy(message = "smsType"))
    } else {
      val mobile = params("mobile")
      val api = new SMSServiceAPI(smsType, mobile,mock,params)
      logger.info(String.format("executing api:mobile=%s,smsType=%s", mobile, smsType))
      safeRun(api)
      PuluoResponseFactory.createJSONResponse(api)
    }
  }
}