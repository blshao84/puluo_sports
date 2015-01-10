package com.unuotech.snippet.api

import net.liftweb.http.JsonResponse
import net.liftweb.http.rest.RestHelper
import net.liftweb.http.{ InMemoryResponse, StreamingResponse }
import net.liftweb.http.S
import net.liftweb.http.FileParamHolder
import net.liftweb.json.JsonAST.JValue
import net.liftweb.json.JsonDSL._
import net.liftweb.common.{ Box, Full }
import net.liftweb.http.BadResponse
import net.liftweb.util.StringHelpers
import java.io.FileOutputStream
import java.io.FileInputStream
import java.io.File
import com.unuotech.snippet.proto.TestAsyncUpload
import com.unuotech.util.ImageUtil
import com.unuotech.snippet.util.ImageUploadResult
import net.liftweb.common.Logger
import net.liftweb.common.Loggable
import com.unuotech.config.Configurations
import com.unuotech.snippet.update.ImageUploadSnippet
import com.unuotech.snippet.PromotionUploadSnippet
import com.unuotech.snippet.StoreEventUploadSnippet
import javax.imageio.ImageIO
import net.liftweb.http.PlainTextResponse
import com.unuotech.model.user.Customer
import com.unuotech.model.StockImage
import net.liftweb.http.JavaScriptResponse
import net.liftweb.http.js.JsCmds
import com.unuotech.model.sales.Stock
import net.liftweb.http.Req
import net.liftweb.json.Serialization
import net.liftweb.json.DefaultFormats
import com.unuotech.snippet.util.JQueryFileUploadResult
import com.unuotech.snippet.util.FileUploadError
import com.unuotech.snippet.util.JQueryFileUploadResultSet

//import mongo.MongoStorage


    
object UploadManager extends RestHelper with Loggable {
  serve {
    case "sizeof" :: resize:: Nil Get req => if (S.loggedIn_?) {
      checkImageSize(resize)
    } else PlainTextResponse("您没登陆或者没有权限")
    case "uploading" :: usage :: Nil Post req => uploadImage(usage, req)
    case "serving" :: uuid :: fileType :: Nil Get req => {
      val root = Configurations.imageServerRoot + uuid + "." + fileType
      logger.info("读取文件%s".format(root))
      val file = new File(root)
      val imageStream = new FileInputStream(file)
      StreamingResponse(imageStream, () => imageStream.close(), file.length, ("Content-Type", "n/a") :: Nil, Nil, 200)
    }

    case "resize" :: uuid :: fileType :: Nil Get req => {
      if (S.loggedIn_? && Customer.currentUser.get.superUser.get) {
        val s0 = System.currentTimeMillis()
        val root = Configurations.imageServerRoot + uuid + "." + fileType
        val image = ImageIO.read(new File(root))
        ImageUtil.resizeAll(image, fileType, uuid)
        val s3 = System.currentTimeMillis()
        PlainTextResponse("转换%s，总计%s秒".format(root, (s3 - s0) / 1000.0))
      } else PlainTextResponse("您没登陆或者没有权限")
    }
    case "rename" :: "all" :: Nil Get req => {
      if (S.loggedIn_? && Customer.currentUser.get.superUser.get) {
        renameAllImages
      } else PlainTextResponse("您没登陆或者没有权限")
    }
    case "resize" :: "all" :: Nil Get req => {
      if (S.loggedIn_? && Customer.currentUser.get.superUser.get) {
        resizeAllImages
      } else PlainTextResponse("您没登陆或者没有权限")
    }
    case "resize" :: "large" :: Nil Get req => {
      if (S.loggedIn_? && Customer.currentUser.get.superUser.get) {
        resizeLargeImages
      } else PlainTextResponse("您没登陆或者没有权限")
    }
  }

  def uploadImage(usage: String, req: Req) = {
    try {
      def saveImage(fph: FileParamHolder) = {
        val fileType = fph.mimeType.split("/") match {
          case Array("image", x) => x
          case _ => "jpg"
        }
        val result = ImageUtil.saveImage(fph.file, fph.fileName, fileType)
        (result, ("name" -> result.hackFileType) ~ ("type" -> fph.mimeType) ~ ("size" -> fph.length))
      }

      val ojv: Box[(ImageUploadResult, JValue)] = req.uploadedFiles.map(fph => saveImage(fph)).headOption
      val ajv = ("name" -> "n/a") ~ ("type" -> "n/a") ~ ("size" -> 0L)
      val ret = ojv.map(_._2) openOr ajv
      val result = ojv.map(_._1).getOrElse(ImageUploadResult.ERROR)
      logger.info("保存文件 %s".format(result))
      logger.info("返回数据 %s".format(ret))
      val jr = JQueryFileUploadResultSet(Seq(JQueryFileUploadResult(result))).jsonValue
      logger.info("jquery-file-upload的返回值：%s".format(jr))
      JsonResponse(jr)
    } catch {
      case e: Exception => JsonResponse(JQueryFileUploadResultSet(Seq(FileUploadError("error",0,""))).jsonValue)
    }

  }
  def checkImageSize(resize:String) = {
    def check(uuid: String, resultType: String, file: File, original: File, resize: String = "no"): Option[String] = {
      try {
        val i = uuid.lastIndexOf('.')
        val fileType = uuid.substring(i + 1, uuid.size)
        val name = uuid.substring(0, i)
        if (file.exists() && file.length() > 1000000) {
          Some(file.getName() + " 大小为 %s MB".format(file.length() / 1000000.0))
        } else if (!file.exists()) {
          if (resize=="yes") {
            val image = ImageIO.read(original)
            ImageUtil.resizeOneType(image, fileType, name, resultType)
          }
          Some(file.getName + " 不存在")
        } else None
      } catch {
        case e: Exception => {
          logger.info("无法处理文件%s。uuid=%s,resultType=%s".format(file.getName, uuid, resultType))
          logger.error(e.getStackTraceString + "\n" + e.toString)
          None
        }
      }
    }
    val imgs = StockImage.findAllFields(Seq(StockImage.stock, StockImage.uuid))
    val badImgs = imgs.map(i => {
      val sid = i.stock.get
      val uuid = i.uuid.get
      val original = new File(Configurations.imageServerRoot + uuid)
      if (original.length > 1000000) {
        logger.info("%s is too large:%s".format(uuid, original))
        val large = new File(Configurations.imageServerRoot + ImageUtil.rename(uuid, "large"))
        val medium = new File(Configurations.imageServerRoot + ImageUtil.rename(uuid, "medium"))
        val small = new File(Configurations.imageServerRoot + ImageUtil.rename(uuid, "small"))
        val res = List(check(uuid, "large", large, original,resize), check(uuid, "medium", medium, original,resize), check(uuid, "small", small, original,resize)).flatten
        if (res.isEmpty) None else Some(sid, res)
      } else None
    }).flatten
    val res: List[(String, String)] = badImgs.map(i => {
      val req = Stock.findByKey(i._1).get.reqStatus
      (req, i._1 + ":" + req + "\n\t" + i._2.mkString("\n\t"))
    })
    val res2 = res.groupBy(_._1).map(_._2).flatten
    PlainTextResponse(res2.mkString("\n"))
  }
  def renameAllImages = {
    val root = new File(Configurations.imageServerRoot)
    val s0 = System.currentTimeMillis()
    val processed = scala.collection.mutable.Set[String]()
    val files = root.listFiles.foreach(file => {
      val fileName = file.getName
      val parts = fileName.split('.')
      val uuid = parts(0)
      val fileType = parts(1)
      if (fileType == "pjpeg") {
        logger.info("将文件%s的pjepg改为jpeg".format(fileName))
        file.renameTo(new File(Configurations.imageServerRoot + uuid + ".jpeg"))
      }
    })
    val s1 = System.currentTimeMillis()
    logger.info("重命名完成，%s秒".format((s1 - s0) / 1000.0))
    StockImage.findAll.foreach(file => {
      val fileName = file.uuid.get
      val parts = fileName.split('.')
      val uuid = parts(0)
      val fileType = parts(1)
      if (fileType == "pjpeg") {
        logger.info("将数据库StockImage的uuid %s的pjepg改为jpeg".format(fileName))
        file.uuid(uuid + ".jpeg").save
      }
    })
    val s2 = System.currentTimeMillis()
    logger.info("数据库重命名完成，%s秒".format((s2 - s1) / 1000.0))
    PlainTextResponse("重命名完成，总共用时%s秒".format((s2 - s0) / 1000.0))
  }
  def resizeAllImages = {
    val root = new File(Configurations.imageServerRoot)
    val s0 = System.currentTimeMillis()
    val processed = scala.collection.mutable.Set[String]()
    val files = root.listFiles.map(file => {
      val fileName = file.getName
      try {
        val parts = fileName.split('.')
        val uuid = parts(0)
        val fileType = parts(1)
        if (fileName.endsWith("-medium." + fileType) /*|| fileName.endsWith("-small." + fileType)*/ ) {
          logger.info(fileName + " 已经生成")
          processed.add(uuid)
          None
        } else {
          Some((uuid, fileType, file))
        }
      } catch {
        case e: Exception => {
          logger.error("无法处理文件%s".format(fileName))
          None
        }
      }
    }).flatten
    files.foreach(tmp => {
      val (uuid, fileType, file) = tmp
      if (!processed.find(_.contains(uuid)).isDefined) {
        val fileName = file.getName
        val s1 = System.currentTimeMillis()
        try {
          val image = ImageIO.read(new File(Configurations.imageServerRoot + fileName))
          ImageUtil.resizeAll(image, fileType, uuid)
        } catch {
          case e: Exception => {
            logger.error("无法处理文件%s".format(fileName))
          }
        }
        val s2 = System.currentTimeMillis()
        logger.info("resize文件%s的时间总计%s秒".format(fileName, (s2 - s1) / 1000.0))
      } else {
        logger.info(uuid + " 已经处理过")
      }
    })
    val s3 = System.currentTimeMillis()
    PlainTextResponse("转换%s文件，总计%s秒".format(files.size, (s3 - s0) / 1000.0))
  }

  def resizeLargeImages = {
    val root = new File(Configurations.imageServerRoot)
    val s0 = System.currentTimeMillis()
    val originalFiles = root.listFiles().filterNot(file => {
      val fileName = file.getName()
      fileName.contains("large") || fileName.contains("medium") || fileName.contains("small")
    })
    val files = originalFiles.map(file => {
      val fileName = file.getName
      try {
        val parts = fileName.split('.')
        val uuid = parts(0)
        val fileType = parts(1)
        Some((uuid, fileType, file))
      } catch {
        case e: Exception => {
          logger.error("无法处理文件%s".format(fileName))
          None
        }
      }
    }).flatten
    files.foreach(tmp => {
      val (uuid, fileType, file) = tmp
      val largeFile = new File(Configurations.imageServerRoot + uuid + "-large." + fileType)
      if (!largeFile.exists) {
        val fileName = file.getName
        val s1 = System.currentTimeMillis()
        try {
          val image = ImageIO.read(new File(Configurations.imageServerRoot + fileName))
          ImageUtil.resizeOneType(image, fileType, uuid, "large")
        } catch {
          case e: Exception => {
            logger.error("无法处理文件%s".format(fileName))
          }
        }
        val s2 = System.currentTimeMillis()
        logger.info("resize文件%s的时间总计%s秒".format(fileName, (s2 - s1) / 1000.0))
      } else {
        logger.info(uuid + " 已经处理过")
      }
    })
    val s3 = System.currentTimeMillis()
    PlainTextResponse("转换%s文件，总计%s秒".format(originalFiles.size, (s3 - s0) / 1000.0))
  }
}
