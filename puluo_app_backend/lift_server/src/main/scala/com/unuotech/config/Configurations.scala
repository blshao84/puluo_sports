package com.puluo.config

import net.liftweb.http.LiftRules
import java.util.Date
import org.joda.time.LocalDate
import net.liftweb.common.Full
import net.liftweb.util.Props
import com.puluo.util.StringUtil
import net.liftweb.mapper.By

object Configurations {
  /**
   * SNS connections
   */

  val weichatToken = Props.get("weichatToken", "wwwdqxmtcom")
  val weichatAppId = Props.get("weichatAppId", "wx002b167e928b4951")
  val weichatAppKey = Props.get("weichatAppKey", "faa1e7c66e8be5f635e10f069b54af35")
  /**
   * Database configurations
   */
  val dbDriver = Props.get("dbDriver", "org.postgresql.Driver")
  val dbUri = Props.get("dbUri", "jdbc:postgresql://")
  val dbServer = Props.get("dbServer", "")
  val dbName = Props.get("dbName", "")
  val dbUser = Props.get("dbUser")
  val dbPassword = Props.get("dbPassword")
  val maxQueryResult = 20

  /**
   * Webserver configurations
   */
  val webServer = Props.get("webServer", "localhost")
  val webServerPort = Props.get("webServerPort", "80")
  val imageServer = Props.get("imageServer", "localhost")
  val imageServerPort = Props.get("imageServerPort", "9999")
  val imageServerRoot = Props.get("image_root", "/var/www/html/upload/")
  val secureWebServer = {
    val sb = new StringBuilder()
    val sb2 = if (webServer == "localhost") sb.append("http://") else sb.append("https://")
    val sb3 = sb2.append(webServer)
    val sb4 = if (webServerPort == "80") sb3 else sb3.append(":").append(webServerPort)
    sb4.toString
  }

  /**
   * Messaging service configurations
   */
  val enableSMSNotification = Props.getBool("enable_sms_notification", false)
  val maxSMSLength = 60
  val smsSerial = Props.get("sms_serial", StringUtil.Empty) //软件序列号,请通过亿美销售人员获取
  val smsActivationKey = Props.get("sms_key", StringUtil.Empty) //序列号首次激活时自己设定
  val smsPassword = Props.get("sms_password", StringUtil.Empty) // 密码,请通过亿美销售人员获取
  val smsPriority = Props.getInt("sms_priority", 5)

  
  /**
   * Email smtp server
   */
  val smtpHost = Props.get("smtpHost", "smtp.qq.com")
  val smtpUser = Props.get("smtpUser", "blshao")
  val smtpPassword = Props.get("smtpPassword", "8409bL01")
  val smtpSender = Props.get("smtpSender", "blshao@qq.com")
  val emailFrom = ""
  val maxEmailLength = 20
 
  /**
   * Other path
   */
  val importPath = Props.get("import_path", "/data/import/")
  /**
   * Image limit
   */
  val maxFileSize = 1000000 // in byte
  val FILE_TOO_LARGE = "file_too_large.png"
  val imageTypes = Set("jpg", "png", "gif")
  val sexOptions = Seq((StringUtil.Empty, StringUtil.Empty), ("男", "男"), ("女", "女"))
  val yearOptions = {
    val currentYear = LocalDate.now().getYear()
    (StringUtil.Empty, StringUtil.Empty) :: Range(1930, currentYear).map(y => (y.toString, y.toString)).toList
  }
  val monthOptions = (StringUtil.Empty, StringUtil.Empty) :: Range(1, 13).map(m => (m.toString, m.toString)).toList
  val dayOptions = (StringUtil.Empty, StringUtil.Empty) :: Range(1, 32).map(d => (d.toString, d.toString)).toList

  /**
   * Payment
   */
  val unionPayMerId = Props.get("unionPayMerId", "808080201305550")
  val unionPayPrivateKeyPath = Props.get("unionPayPrivateKeyPath", "/data/payment/MerPrK.key")
  val unionPayPublicKeyPath = Props.get("unionPayPublicKeyPath", "/data/payment/PgPubk.key")
  val unionPayVersion = "20040916"
  val unionPayGateway = "8607"
  /**
   * Test
   */
  val mockPayment = Props.getBool("mockPayment", false)
  val mockStockCheck = true//mockPayment

  private val provinces = {
    (LiftRules.loadResourceAsXml("/provinces.xml").get \\ "province").map(province => {
      val name = (province \ "@name").toString()
      val id = (province \ "@id").toString()
      val cities = (province \\ "city").map(city => ((city \ "@name").toString, (city \ "@id").toString)).toMap
      (name, (id, cities))
    }).toMap
  }

  def isImage(fileType: String) = imageTypes.contains(fileType.toLowerCase())
  def webServerURL = "http://" + webServer + ":" + webServerPort + "/"
  def webServerSecureURL = "https://" + webServer + "/"
  def imageServerURL = "http://" + imageServer + ":" + imageServerPort + "/"
  def uploadImageLoc = imageServerURL + "upload/"
  def getCity(s: String): List[String] = {
    provinces.get(s) match {
      case Some(tmp) => tmp._2.toSeq.sortBy(_._2.toInt).map(_._1).toList
      case None => List.empty
    }
  }
  def allProvinces = provinces.toSeq.sortBy(_._2._1.toInt).map(_._1).toList
}