package com.puluo.api.test

import net.liftweb.http.rest.RestHelper
import com.puluo.test.TestConstants
import net.liftweb.http.JsonResponse

object TestAPI extends RestHelper {
  serve {
    case "test" :: _ Get _ => {
      val x = new TestConstants().test
      JsonResponse(x)
    }
  }
}