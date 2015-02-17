package bootstrap.liftweb

import com.puluo.api.auth.PuluoAuthAPI
import com.puluo.api.service.PuluoFileUploader
import com.puluo.api.test.TestAPI
import com.puluo.api.util.PuluoSession
import net.liftweb.common.Full
import net.liftweb.common.Loggable
import net.liftweb.http.LiftRules
import net.liftweb.http.LiftRulesMocker.toLiftRules
import net.liftweb.http.NotFoundAsTemplate
import net.liftweb.http.NoticeType
import net.liftweb.http.OnDiskFileParamHolder
import net.liftweb.http.ParsePath
import net.liftweb.http.RedirectResponse
import net.liftweb.http.Req
import net.liftweb.http.js.jquery.JQuery14Artifacts
import net.liftweb.sitemap.Menu
import net.liftweb.sitemap.SiteMap
import net.liftweb.sitemap._
import net.liftweb.util.Helpers.intToTimeSpanBuilder
import net.liftweb.util.Helpers.pfToGuardable
import net.liftweb.util.NamedPF
import net.liftweb.util.Props
import net.liftweb.util.Vendor.valToVendor



class Boot extends Loggable {


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
      case _ if PuluoSession.login => 
    }
    
    // setup the 404 handler 
    LiftRules.uriNotFound.prepend(NamedPF("404handler") {
      case (req, failure) => NotFoundAsTemplate(ParsePath(List("404"), "html", false, false))
    })
    // make requests utf-8
    LiftRules.early.append(_.setCharacterEncoding("UTF-8"))
    LiftRules.dispatch.append(withAuthentication guard TestAPI)
    LiftRules.dispatch.append(withAuthentication guard PuluoFileUploader)
    LiftRules.dispatch.append(PuluoAuthAPI)
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
    doBoot
  }

  def doBoot:Unit = {
    setupJS()
    setupUX()
    setupNewSiteMap()
    setupRequestConfig()
    setupErrorHandling()
    setupFileUpload
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
