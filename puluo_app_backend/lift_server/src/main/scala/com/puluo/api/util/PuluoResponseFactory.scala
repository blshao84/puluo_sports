package com.puluo.api.util
import scala.collection.JavaConversions._
import net.liftweb.http.LiftResponse
import net.liftweb.json.JsonAST
import java.util.UUID
import net.liftweb.http.JsonResponse
import net.liftweb.json.JsonAST.JValue
import net.liftweb.json._
import com.puluo.api.PuluoAPI
import net.liftweb.http.S
import net.liftweb.common.Loggable
import com.puluo.api.result.ApiErrorResult

object PuluoResponseFactory extends Loggable{

  implicit val formats = DefaultFormats
  
  def createParamMap(keys:Seq[String]) = {
    createParamMapFromJson(keys) ++ createParamMapFromS(keys)
  }

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
  
  private def renderJson(jv:JValue) = {
    jv match {
      case JNothing => ""
      case _ =>{
        val raw = Printer.pretty(JsonAST.render((jv)))
        logger.info("parsed json param "+raw)
        if(raw.size>=2){
          if((raw.head=='"') && (raw.last == '"')){
            raw.drop(1).dropRight(1)
          } else raw
        } else raw
      }
    }
  }
  private def createParamMapFromJson(keys:Seq[String]) = {
   val params = S.request.get.json.map { json =>
     keys.map(k=> {
       val jvalue = renderJson(json\k)
       (k,jvalue)
     }).toMap
   }.getOrElse(Map.empty[String,String])
   logger.info("从req.json获得的参数:\n"+params.mkString("\n"))
   params
  }
  
  private def createParamMapFromS(keys:Seq[String]) = {
    val params = keys.map(key=> S.param(key).map(value=>(key,value))).flatten.toMap
    logger.info("从S.param获得的参数:\n"+params.mkString("\n"))
    params
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

object ErrorResponseResult{
  def apply(id:Integer):ErrorResponseResult = {
    val e = ApiErrorResult.getError(id)
    ErrorResponseResult(e.id,e.error_type,e.message,e.url)
  }
}
case class ErrorResponseResult(val id:Integer, val error_type:String, val message: String, val url: String)