package com.puluo.snippet
import net.liftweb.http.rest.RestHelper
import net.liftweb.util.Helpers._
import net.liftweb.http._
import net.liftweb.common.Loggable
import net.liftweb.common.Full

object PuluoDownloader extends RestHelper  with Loggable {
  object data extends RequestVar[String]("")
  object fileName extends RequestVar[String]("")

  serve {
    case "download" :: Nil Get _ => downloadData
  }

  def downloadData = {
    val content: Array[Byte] = data.get.getBytes("UTF-8")
    val disposition = "attachment; filename=\""+fileName.get+"\""
    val headers =
      ("Content-type" -> "text/csv; charset=UTF-8") ::
        ("Content-length" -> content.length.toString) ::
        ("Content-Disposition" -> disposition) :: Nil
    Full(StreamingResponse(
      new java.io.ByteArrayInputStream(content),
      () => {},
      content.length,
      headers, Nil, 200))
  }
}