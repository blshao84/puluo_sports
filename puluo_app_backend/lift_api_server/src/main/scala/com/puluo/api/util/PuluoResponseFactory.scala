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
import com.puluo.result.ApiErrorResult

object PuluoResponseFactory extends Loggable{

  implicit val formats = DefaultFormats
  
  def createParamMap(keys:Seq[String]):Map[String,String] = {
    createParamMapFromJson(keys) ++ createParamMapFromS(keys)
  }

  def createErrorResponse(error: ApiErrorResult): LiftResponse = {
    val jvalue = parse(error.toJson())
    JsonResponse(jvalue, requestHeader, JsonResponse.cookies, 200)
  }

  def createJSONResponse(api: PuluoAPI[_, _], code: Int = 200): LiftResponse = {
    val jvalue = parse(api.result)
    JsonResponse(withSession(jvalue), requestHeader, JsonResponse.cookies, code)
  }

  def createDummyJSONResponse(jresult: String, code: Int = 200): LiftResponse = {
    val jvalue = parse(jresult)
    JsonResponse(withSession(jvalue), requestHeader, JsonResponse.cookies, code)
  }
  
  private def withSession(jvalue:JValue):JValue = {
    val session = S.session.flatMap(_.httpSession.map(_.sessionId)).getOrElse("")
    jvalue ++ JField("token",JString(session))
  }
  
  private def renderJson(jv:JValue):Option[String] = {
    jv match {
      case JNothing => None
      case _ =>{
        val raw = Printer.pretty(JsonAST.render((jv)))
        logger.info("parsed json param "+raw)
        val value = if(raw.size>=2){
          if((raw.head=='"') && (raw.last == '"')){
            raw.drop(1).dropRight(1)
          } else raw
        } else raw
        Some(value)
      }
    }
  }
  private def createParamMapFromJson(keys:Seq[String]) = {
   val params = S.request.get.json.map { json =>
     keys.flatMap(k=> {
       val jvalue = renderJson(json\k)
       if(jvalue.isDefined) Some(k,jvalue.get) else None
     }).toMap
   }.getOrElse(Map.empty[String,String])
   logger.debug("从req.json获得的参数:\n"+params.mkString("\n"))
   params
  }
  
  private def createParamMapFromS(keys:Seq[String]) = {
    val params = keys.map(key=> S.param(key).map(value=>(key,value))).flatten.toMap
    logger.debug("从S.param获得的参数:\n"+params.mkString("\n"))
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
  implicit def errorResponseResultToJavaErrorResult(e:ErrorResponseResult):ApiErrorResult = {
    ApiErrorResult.getError(e.id)
  }
  def apply(id:Integer):ErrorResponseResult = {
    val e = ApiErrorResult.getError(id)
    ErrorResponseResult(e.id,e.error_type,e.message,e.url)
  }
}
case class ErrorResponseResult(val id:Integer, val error_type:String, val message: String, val url: String)