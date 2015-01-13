package bootstrap.liftweb

// framework imports
import net.liftweb.common._
import net.liftweb.util._
import net.liftweb.util.Helpers._
import net.liftweb.http._
import net.liftweb.sitemap._
import net.liftweb.sitemap.Loc._
import net.liftweb.mapper.{ DB, Schemifier, DefaultConnectionIdentifier, StandardDBVendor, MapperRules }
import net.liftweb.mapper.MetaMapper
import org.apache.commons.fileupload.FileUpload
import com.unuotech.config.Configurations
import com.unuotech.snippet.api.MessagingService
import com.unuotech.util.SMSClient
import net.liftweb.http.js.jquery.JQuery14Artifacts

trait BootResult
case class DSIBootResult(val db: Option[Int]) extends BootResult
case class CustomFilter(test: Boolean) extends AnyLocParam

trait DSI {
  def createDSI: StandardDBVendor
  def setupDB: Int
}

class Boot extends DSI with Loggable {
  def createDSI: net.liftweb.mapper.StandardDBVendor = ??? 
  
  def setupDB: Int = ???

  def setupUX() = {
    /**** user experience settings ****/
    // set the time that notices should be displayed and then fadeout
    LiftRules.noticesAutoFadeOut.default.set((notices: NoticeType.Value) => Full(2 seconds, 2 seconds))
    //LiftRules.loggedInTest = Full(() => Customer.loggedIn_?)
  }

  def setupPayment() = {
    /**** paypal settings ****/
  }

  def setupEmailServer() = {
    //email server
    System.setProperty("mail.smtp.host", Configurations.smtpHost)
    System.setProperty("mail.smtp.user", Configurations.smtpUser)
    System.setProperty("mail.smtp.auth", "true")
    System.setProperty("mail.smtp.from", Configurations.smtpSender)
    Mailer.authenticator = Full(new javax.mail.Authenticator {
      override def getPasswordAuthentication(): javax.mail.PasswordAuthentication =
        new javax.mail.PasswordAuthentication(Configurations.smtpUser, Configurations.smtpPassword)
    })
  }

  def setupNewSiteMap() = {

    SiteMap.enforceUniqueLinks = false
    val menus = List()

    LiftRules.setSiteMap(SiteMap(menus: _*))
  }

  def setupRequestConfig() = {
    // setup the 404 handler 
    LiftRules.uriNotFound.prepend(NamedPF("404handler") {
      case (req, failure) => NotFoundAsTemplate(ParsePath(List("404"), "html", false, false))
    })
    // make requests utf-8
    LiftRules.early.append(_.setCharacterEncoding("UTF-8"))
    LiftRules.dispatch.append(MessagingService)

    LiftRules.handleMimeFile = OnDiskFileParamHolder.apply


  }
  def setupJS() = {
    /*FoBo.InitParam.JQuery = FoBo.JQuery191
    FoBo.InitParam.ToolKit = FoBo.Bootstrap232
    FoBo.init()*/
    // set the JSArtifacts
    LiftRules.jsArtifacts = JQuery14Artifacts

    // make the furniture appear
    LiftRules.ajaxStart =
      Full(() => LiftRules.jsArtifacts.show("loading").cmd)

    // make the furniture go away when it ends
    LiftRules.ajaxEnd =
      Full(() => LiftRules.jsArtifacts.hide("loading").cmd)
  }

  def setupMisc() = {
    val url = LiftRules.defaultGetResource("/logback.xml").get
    Logger.setup = Full(Logback.withFile(url))
    SMSClient.init
  }
  def boot {
    val result = doBoot
    Unit
  }

  def doBoot: BootResult = {
    setupJS()
    setupUX()
    setupPayment()
    setupEmailServer()
    //setupSiteMap()
    setupNewSiteMap()
    setupRequestConfig()
    setupErrorHandling()
    setupMisc()
    setupFileUpload

    DSIBootResult(None)
  }

  def setupFileUpload = {
    LiftRules.maxMimeFileSize = 2000000L
    LiftRules.maxMimeSize = 2000000L
    //LiftRules.dispatch.append(UploadManager)

    //Show the spinny image when an Ajax call starts
    LiftRules.ajaxStart =
      Full(() => LiftRules.jsArtifacts.show("ajax-loader").cmd)

    // Make the spinny image go away when it ends
    LiftRules.ajaxEnd =
      Full(() => LiftRules.jsArtifacts.hide("ajax-loader").cmd)
  }

  def setupErrorHandling() = {
    LiftRules.exceptionHandler.prepend {
      case (_, _, exception: Exception) => {
        RedirectResponse("/error") //JavaScriptResponse(JsCmds.Alert("文件大小不能超过2M"))
      }
      case (Props.RunModes.Production, _, exception) => RedirectResponse("/error")
    }
  }
}
