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
import com.puluo.config.Configurations
import com.puluo.util.SMSClient
import net.liftweb.http.js.jquery.JQuery14Artifacts
import com.puluo.api.test.TestAPI
import com.puluo.api.PuluoFileUploader
import com.puluo.service.PuluoImageService
import com.puluo.api.util.LoggedIn
import com.puluo.api.PuluoLogin

trait BootResult
case class DSIBootResult(val db: Option[Int]) extends BootResult
case class CustomFilter(test: Boolean) extends AnyLocParam


class Boot extends Loggable {
  def createDSI: net.liftweb.mapper.StandardDBVendor = ??? 
  
  def setupDB: Int = ???

  def setupUX() = {
    /**** user experience settings ****/
    // set the time that notices should be displayed and then fadeout
    LiftRules.noticesAutoFadeOut.default.set((notices: NoticeType.Value) => Full(2 seconds, 2 seconds))
    //LiftRules.loggedInTest = Full(() => Customer.loggedIn_?)
  }

  def setupNewSiteMap() = {

    SiteMap.enforceUniqueLinks = false
    val menus:List[Menu] = Nil
    

    LiftRules.setSiteMap(SiteMap(menus: _*))
  }

  def setupRequestConfig() = {
    val withAuthentication: PartialFunction[Req, Unit] = { 
      case _ if LoggedIn.is => 
    }
    
    // setup the 404 handler 
    LiftRules.uriNotFound.prepend(NamedPF("404handler") {
      case (req, failure) => NotFoundAsTemplate(ParsePath(List("404"), "html", false, false))
    })
    // make requests utf-8
    LiftRules.early.append(_.setCharacterEncoding("UTF-8"))
    LiftRules.dispatch.append(withAuthentication guard TestAPI)
    LiftRules.dispatch.append(withAuthentication guard PuluoFileUploader)
    LiftRules.dispatch.append(PuluoLogin)
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

  def boot {
    val result = doBoot
    Unit
  }

  def doBoot: BootResult = {
    setupJS()
    setupUX()
    setupNewSiteMap()
    setupRequestConfig()
    setupErrorHandling()
    setupFileUpload
    DSIBootResult(None)
  }

  def setupFileUpload = {
    LiftRules.maxMimeFileSize = 2000000L
    LiftRules.maxMimeSize = 2000000L
    LiftRules.dispatch.append(PuluoFileUploader)

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
