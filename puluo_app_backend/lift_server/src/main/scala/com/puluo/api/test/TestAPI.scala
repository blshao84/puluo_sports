package com.puluo.api.test

import net.liftweb.http.rest.RestHelper
import net.liftweb.http.JsonResponse
import com.puluo.api.ApiTest

object TestAPI extends RestHelper {
  serve {
    case "test" :: _ Get _ => {
      val x = new ApiTest().test
      JsonResponse(x)
    }
  }
}