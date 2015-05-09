package com.puluo.snippet.util
import java.io.ByteArrayInputStream
import net.liftweb.http.FileParamHolder
import java.util.UUID
import javax.imageio.ImageIO
import net.liftweb.json.Serialization
import net.liftweb.json.DefaultFormats
import net.liftweb.json.Extraction
import net.liftweb.common.Loggable
import com.puluo.result.ImageUploadServiceResult

trait JQueryFileUploadResult {
  def name:String
  def size:Long
  implicit val formats = DefaultFormats
}

case class JQueryFileUploadResultSet(files:Seq[JQueryFileUploadResult]){
  implicit val formats = DefaultFormats
  def jsonString = Serialization.write(this)
  def jsonValue = Extraction.decompose(this)
}
case class FileUploadSuccess(
    name:String,
    size:Long,
    url:String,
    thumbnailUrl:String,
    deleteUrl:String = "",
    deleteType:String = "DELETE"
) extends JQueryFileUploadResult

case class FileUploadError(
    name:String,
    size:Long,
    error:String) extends JQueryFileUploadResult
    
object JQueryFileUploadResult extends Loggable{
  def apply(originalName:String,res:ImageUploadServiceResult):JQueryFileUploadResult = {
    logger.info(s"image upload result:${res.image_link},${res.status}")
    if(res.status=="success"){
      FileUploadSuccess(originalName,0,res.image_link,res.thumbnai())
    } else FileUploadError(originalName,0,"上传图片失败")
  }
}