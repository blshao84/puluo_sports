package bootstrap.liftweb

import com.puluo.api.auth.PuluoAuthAPI
import com.puluo.api.util._
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
import com.puluo.api.timeline.PuluoTimelineAPI
import com.puluo.api.message.PuluoMessageAPI
import com.puluo.api.user.PuluoUserAPI
import com.puluo.api.graph.PuluoGraphAPI
import com.puluo.api.event.PuluoEventAPI
import com.puluo.api.test.DemoAPI
import net.liftweb.http.provider.HTTPParam
import com.puluo.dao.impl.DaoApi
import com.puluo.api.auth.PuluoAuthPrivateAPI
import com.puluo.api.service.PuluoPrivateServiceAPI
import com.puluo.api.service.PuluoServiceAPI
import com.puluo.api.test.WechatService
import net.liftweb.http.XhtmlResponse
import net.liftweb.http.PlainTextResponse
import com.puluo.session.PuluoSessionManager
import com.puluo.api.test.DummyAPI
import com.puluo.api.test.DummyPrivateAPI
import com.puluo.api.test.PrivateWechatService
import net.liftweb.http.S
import com.puluo.api.payment.AliPaymentNotification

class Boot extends Loggable {

  def setupUX() = {
    /**** user experience settings ****/
    // set the time that notices should be displayed and then fadeout
    LiftRules.noticesAutoFadeOut.default.set((notices: NoticeType.Value) => Full(2 seconds, 2 seconds))
    //LiftRules.loggedInTest = Full(() => Customer.loggedIn_?)
  }

  def setupNewSiteMap() = {

    SiteMap.enforceUniqueLinks = false

    LiftRules.setSiteMap(SiteMap(
      Menu("test_bootstrap9") / "proto" / "test" >> net.liftweb.sitemap.Loc.Hidden,
      Menu("payment_success") / "payment_success" >> net.liftweb.sitemap.Loc.Hidden,
      Menu("payment") / "payment" >> net.liftweb.sitemap.Loc.Hidden,
      Menu("promotion") / "promotion" >> net.liftweb.sitemap.Loc.Hidden,
      Menu("event") / "event" >> net.liftweb.sitemap.Loc.Hidden,
      Menu("event_tab") / "event_tab" >> net.liftweb.sitemap.Loc.Hidden,
      Menu("single_event") / "single_event" >> net.liftweb.sitemap.Loc.Hidden))
  }

  def authenticate(r: Req): Boolean = {
    val token = PuluoResponseFactory.createParamMap(Seq("token")).values.headOption
    val verified = token.map(PuluoSessionManager.isLogin(_)).getOrElse(false)
    logger.debug(String.format("retrive token %s from request and verified = %s", token.toString, verified.toString))
    verified

  }

  def addHeader(s: net.liftweb.http.provider.HTTPResponse): Unit = {
    val allowedOrigin = S.request.get.header("Origin").getOrElse("http://localhost:8100")
    logger.debug(s"allowedOrigin:${allowedOrigin}")
    s.addHeaders(
      List( //HTTPParam("X-Lift-Version", LiftRules.liftVersion),
        HTTPParam("Access-Control-Allow-Origin", allowedOrigin),
        HTTPParam("Access-Control-Allow-Credentials", "true"),
        HTTPParam("Access-Control-Allow-Methods", "GET, POST, PUT, OPTIONS"),
        HTTPParam("Access-Control-Allow-Headers", "Accept,WWW-Authenticate,Keep-Alive,User-Agent,X-Requested-With,Cache-Control,Content-Type")))
  }

  def setupRequestConfig() = {
    val withAuthentication: PartialFunction[Req, Unit] = {
      case r: Req if authenticate(r) =>
    }
    LiftRules.addToPackages("com.puluo.snippet")
    LiftRules.addToPackages("com.puluo")
    // setup the 404 handler 
    LiftRules.uriNotFound.prepend(NamedPF("404handler") {
      case (req, failure) => NotFoundAsTemplate(ParsePath(List("404"), "html", false, false))
    })
    // setup cors
    LiftRules.supplimentalHeaders = addHeader
    // make requests utf-8
    LiftRules.early.append(_.setCharacterEncoding("UTF-8"))
    LiftRules.dispatch.append(DemoAPI)
    LiftRules.dispatch.append(PuluoServiceAPI)
    LiftRules.dispatch.append(PuluoAuthAPI)
    LiftRules.dispatch.append(WechatService)
    LiftRules.dispatch.append(DummyAPI)
    LiftRules.dispatch.append(AliPaymentNotification)
    LiftRules.dispatch.append(withAuthentication guard PrivateWechatService)
    LiftRules.dispatch.append(withAuthentication guard DummyPrivateAPI)
    LiftRules.dispatch.append(withAuthentication guard PuluoPrivateServiceAPI)
    LiftRules.dispatch.append(withAuthentication guard PuluoEventAPI)
    LiftRules.dispatch.append(withAuthentication guard PuluoGraphAPI)
    LiftRules.dispatch.append(withAuthentication guard PuluoMessageAPI)
    LiftRules.dispatch.append(withAuthentication guard PuluoTimelineAPI)
    LiftRules.dispatch.append(withAuthentication guard PuluoUserAPI)
    LiftRules.dispatch.append(withAuthentication guard PuluoAuthPrivateAPI)
    LiftRules.handleMimeFile = OnDiskFileParamHolder.apply

  }
  def setupJS() = {

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

  def doBoot: Unit = {
    setupJS()
    setupUX()
    setupNewSiteMap()
    setupRequestConfig()
    setupFileUpload
  }

  def setupFileUpload = {
    LiftRules.maxMimeFileSize = 2000000L
    LiftRules.maxMimeSize = 2000000L

    //Show the spinny image when an Ajax call starts
    LiftRules.ajaxStart =
      Full(() => LiftRules.jsArtifacts.show("ajax-loader").cmd)

    // Make the spinny image go away when it ends
    LiftRules.ajaxEnd =
      Full(() => LiftRules.jsArtifacts.hide("ajax-loader").cmd)
  }
}
