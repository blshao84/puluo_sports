package com.unuotech.util
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.client.methods.HttpGet
import com.unuotech.config.Configurations
import java.io.FileOutputStream
import org.apache.http.impl.client.BasicResponseHandler
import net.liftweb.mapper.MapperRules
import net.liftweb.mapper.Schemifier
import net.liftweb.db.DefaultConnectionIdentifier
import net.liftweb.http.LiftRules
import net.liftweb.util.StringHelpers
import net.liftweb.mapper._
import net.liftweb.util.Props
import com.unuotech.model.sales._
import org.apache.http.client.methods.HttpPut
import org.apache.http.Header
import java.io.IOException
import org.apache.http.client.methods.HttpPost
import java.io.BufferedReader
import org.apache.http.HttpResponse
import org.apache.http.entity.mime.MultipartEntity
import org.apache.http.entity.mime.content.ByteArrayBody
import net.liftweb.common.LazyLoggable
import scala.xml.XML
import net.liftweb.common.Full
import org.apache.http.entity.mime.content.StringBody
import java.util.UUID
import java.io.ByteArrayInputStream
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import com.unuotech.snippet.util.ImageUploadResult
import net.liftweb.http.S
import com.unuotech.model.user.Customer
import java.io.BufferedWriter
import java.io.FileWriter
import java.io.FileInputStream
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.NameValuePair
import org.apache.http.message.BasicNameValuePair
import scala.collection.JavaConversions._
import java.io.File
import com.unuotech.config.Category
import com.unuotech.model.entity.LegalEntity
import bootstrap.liftweb.Boot
import com.unuotech.model.StockImage
import java.awt.Image
import com.unuotech.payment.config.AlipayConfig

object ImageUtil extends LazyLoggable {
  private val httpClient = new ThreadLocal[DefaultHttpClient] {
    protected override def initialValue() = new DefaultHttpClient()
  }

  def mkUserTempFileName(fileName: String) = {
    val userName = Customer.currentUserId.getOrElse(StringUtil.Empty)
    "tmp/" + userName + "_" + fileName
  }
  def getImageFile(name: String) = {
    val url = Configurations.uploadImageLoc
    val fileName = url + name
    getImageFromUrl(fileName)
  }

  def getImageFromUrl(url: String): Array[Byte] = {
    val httpGet = new HttpGet(url)
    val response = httpClient.get.execute(httpGet)
    val data = response.getEntity().getContent()
    val body = Stream.continually(data.read).takeWhile(-1 !=).map(_.toByte).toArray
    httpGet.releaseConnection()
    body
  }

  def saveImageFromUrl(url: String): ImageUploadResult = {
    val data = getImageFromUrl(url)
    logger.info("从%s获取%s byte数据".format(url,data.size))
    val imageStream = new ByteArrayInputStream(data)
    val reader = ImageIO.getImageReaders(ImageIO.createImageInputStream(imageStream)).toSeq.headOption
    if(reader.isDefined){
      val fileType = reader.get.getFormatName()
      logger.info("图像%s的类型为%s".format(url,fileType))
      saveImage(data,"",fileType,false)
    } else ImageUploadResult.ERROR
  }

  def uploadImage(data: Array[Byte], fileName: String, fileType: String, folder: String) = {
    val uuid = UUID.randomUUID().toString()
    val newFileName = uuid + "." + fileType
    val url = Configurations.imageServerURL
    val post = new HttpPost(url + "cgi-bin/upload.php")
    val entity = new MultipartEntity()
    entity.addPart("uploadedfile", new ByteArrayBody(data, newFileName))
    entity.addPart("username", new StringBody(folder))
    post.setEntity(entity)
    val response = httpClient.get.execute(post)
    //logger.info(response)
    val status = response.getStatusLine().getStatusCode()
    post.releaseConnection()
    if (status != 200) {
      ImageUploadResult.ERROR
    } else {
      if (Configurations.isImage(fileType)) {
        //auto detect uploaded image's type (large,medium,small) based on its size
        val imageStream = new ByteArrayInputStream(data);
        val image = ImageIO.read(imageStream);
        ImageUploadResult(fileName, newFileName, fileType, data.size) //, image.getHeight(), image.getWidth())
      } else ImageUploadResult(fileName, newFileName, fileType, data.size) //, 0, 0)
    }
  }

  def saveImage(data: Array[Byte], fileName: String, fileType: String, resize: Boolean = true) = {
    try {
      val s1 = System.currentTimeMillis()
      val uuid = UUID.randomUUID().toString()
      val newFileName = uuid + "." + fileType
      val root = Configurations.imageServerRoot + newFileName
      val fos = new FileOutputStream(root)
      fos.write(data)
      fos.close()
      val s2 = System.currentTimeMillis()
      logger.info("保存文件 %s 至 %s，%s秒".format(newFileName, root, (s2 - s1) / 1000.0))
      if (resize) {
        //auto detect uploaded image's type (large,medium,small) based on its size
        val s3 = System.currentTimeMillis()
        val imageStream = new ByteArrayInputStream(data)
        val image = ImageIO.read(imageStream)
        val s4 = System.currentTimeMillis()
        logger.info("图像转换，%s秒".format((s4 - s3) / 1000.0))
        resizeAll(image, fileType, uuid)
      }
      ImageUploadResult(fileName, newFileName, fileType, data.size)
    } catch {
      case e: Exception => {
        logger.error(e.getStackTraceString)
        ImageUploadResult.ERROR
      }
    }
  }

  def resizeAll(image: BufferedImage, fileType: String, uuid: String) = {
    val imageHeight = image.getHeight()
    val imageWidth = image.getWidth()
    val s1 = System.currentTimeMillis()
    resizeOneType(image, fileType, uuid, "large")
    val s2 = System.currentTimeMillis()
    logger.info("将图像(height=%s,width=%s)resize至large，%s秒".format(imageHeight, imageWidth, (s2 - s1) / 1000.0))

    val s3 = System.currentTimeMillis()
    resizeOneType(image, fileType, uuid, "medium")
    val s4 = System.currentTimeMillis()
    logger.info("将图像(height=%s,width=%s)resize至medium，%s秒".format(imageHeight, imageWidth, (s4 - s3) / 1000.0))

    val s5 = System.currentTimeMillis()
    resizeOneType(image, fileType, uuid, "small")
    val s6 = System.currentTimeMillis()
    logger.info("将图像(height=%s,width=%s)resize至lsmall，%s秒".format(imageHeight, imageWidth, (s6 - s5) / 1000.0))

  }

  def resizeOneType(image: BufferedImage, fileType: String, uuid: String, resultType: String) = {
    val originalWidth = image.getWidth()
    val originalHeight = image.getHeight()

    val (width, height) = resultType match {
      case "large" => if (originalWidth > 960) (960, -1) else (originalWidth, originalHeight)
      case "medium" => if (originalHeight > 450) (-1, 450) else (originalWidth, originalHeight)
      case "small" => if (originalHeight > 200) (-1, 200) else (originalWidth, originalHeight)
      case _ => throw new Exception("%s的图像尺寸不支持".format(resultType))
    }
    if (width == originalWidth && height == originalHeight) {
      logger.info("图片尺寸为height=%s,width=%s，不满足转换%s条件".format(originalHeight, originalWidth, resultType))
    } else {
      val newImage = doResize(image, height, width)
      val newName = Configurations.imageServerRoot + uuid + "-" + resultType + "." + fileType
      val newFile = new File(newName)
      ImageIO.write(newImage, fileType, newFile)
    }
  }

  def doResize(sourceImage: BufferedImage, height: Int, width: Int) = {
    val t1 = System.currentTimeMillis()
    val thumbnail = sourceImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    val bufferedThumbnail = new BufferedImage(thumbnail.getWidth(null),
      thumbnail.getHeight(null),
      BufferedImage.TYPE_INT_RGB)
    bufferedThumbnail.getGraphics().drawImage(thumbnail, 0, 0, null)
    val t2 = System.currentTimeMillis()
    logger.info("将图像resize至height=%s,width=%s消耗%s秒".format(height, width, (t2 - t1) / 1000.0))
    bufferedThumbnail
  }
  def readImage(uuid: String) = {
    val root = Configurations.imageServerRoot + uuid
    new FileInputStream(root)
  }

  def rename(file: String, posfix: String) = {
    val index = file.lastIndexOf('.')
    if (index > 0) {
      val name = file.substring(0, index)
      val fType = file.substring(index + 1, file.length())
      val tmp = name.split('-')
      val existingPosfix = tmp.last
      val rawName = if (existingPosfix == "small" || existingPosfix == "medium" || existingPosfix == "large") {
        tmp.reverse.tail.reverse.mkString("-")
      } else name
      rawName + "-" + posfix + "." + fType
    } else file
  }

  def renameToSmall(imageName: String): String = {
    val smallFileName = rename(imageName, "small")
    val newFile = new File(Configurations.imageServerRoot + smallFileName)
    if (newFile.exists()) smallFileName else renameToResizedImage(imageName, "medium")
  }

  def renameToResizedImage(imageName: String, iType: String): String = {
    val mediumFileName = rename(imageName, iType)
    val newFile = new File(Configurations.imageServerRoot + mediumFileName)
    if (newFile.exists()) mediumFileName else {
      if (overSize(imageName)) {
        logger.info("图片%s不存在".format(mediumFileName))
        Configurations.FILE_TOO_LARGE
      } else imageName
    }
  }
  def warnLargeImage(imageName: String): String = {
    if (overSize(imageName)) {
      logger.info("图片：%s 超过最大尺寸，尝试使用更小的图片".format(imageName))
      renameToResizedImage(imageName, "medium")
    } else imageName
  }

  private def overSize(imageName: String): Boolean = {
    val file = new File(Configurations.imageServerRoot + imageName)
    if (file.length() > Configurations.maxFileSize) true else false
  }

  private def getImage(file: String): Long = {
    val httpGet = new HttpGet("http://mallp/sizeof/" + file)
    logger.info("retreving data for %s".format(file))
    val response = httpClient.get.execute(httpGet)
    val data = response.getEntity().getContent()
    val body = Stream.continually(data.read).takeWhile(-1 !=).map(_.toByte).toArray
    httpGet.releaseConnection
    val str = new String(body, AlipayConfig.output_charset)
    str.toLong
  }
  def main(args: Array[String]) = {
    val imgs = StockImage.findAllFields(Seq(StockImage.stock, StockImage.uuid))
    val badImgs = imgs.map(i => {
      val sid = i.stock.get
      val uuid = i.uuid.get
      val original = getImage(uuid)
      if (original > 1000000) {
        logger.info("%s is too large:%s".format(uuid, original))
        val medium = getImage(rename(uuid, "medium"))
        if (medium > 1000000) {
          logger.info("%s-medium is too large:%s".format(uuid, original))
          Some((sid, uuid))
        } else None
      } else None
    })

    badImgs.foreach(println(_))

    /*

    val newFileName = "test.jpg"
    val post = new HttpPost("http://mallp:9999/cgi-bin/upload.php")
    val entity = new MultipartEntity()
    entity.addPart("uploadedfile", new ByteArrayBody(body, newFileName))
    entity.addPart("username", new StringBody(StringUtil.Empty))
    post.setEntity(entity)
    val response2 = httpClient.get.execute(post)
    println(response2.getStatusLine().getStatusCode())
    post.releaseConnection()*/
  }
}