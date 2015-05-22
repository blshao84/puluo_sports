package bootstrap.liftweb

// framework imports
import net.liftweb.common._
import net.liftweb.util._
import net.liftweb.util.Helpers._
import net.liftweb.http._
import net.liftweb.sitemap._
import net.liftweb.sitemap.Loc._
import net.liftweb.mapper.{ DB, Schemifier, DefaultConnectionIdentifier, StandardDBVendor, MapperRules }
import net.liftweb.http.js.jquery.JQuery14Artifacts
import net.liftweb.http.js.jquery.JQueryArtifacts
import net.liftweb.db.DBLogEntry
import net.liftweb.http.js.jquery.JQuery13Artifacts
import java.util.concurrent.atomic.AtomicInteger
import net.liftweb.mapper.Mapper
import net.liftweb.mapper.MetaMapper
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException
import net.liftweb.http.js.JsCmds
import com.puluo.service.PuluoService
import com.puluo.entity.PuluoAdmin
import com.puluo.snippet.PuluoFileUploadAPI
import com.puluo.entity.UserImage
import com.puluo.snippet.PuluoDownloader

object Boot {
  def MustBeLoggedIn = PuluoAdmin.loginFirst
}

class Boot extends Loggable {
  import Boot._

  def setupUX() = {
    /**** user experience settings ****/
    // set the time that notices should be displayed and then fadeout
    LiftRules.noticesAutoFadeOut.default.set((notices: NoticeType.Value) => Full(2 seconds, 2 seconds))
    LiftRules.loggedInTest = Full(() => PuluoAdmin.loggedIn_?)
  }

  lazy val dsi: StandardDBVendor = createDSI
  def createDSI = {
    val env = System.getProperty("run.mode")
    val (ip,user) = if(env == "production") {
      ("localhost:5432","puluoprod")
    } else ("183.131.76.93:2345","puluodev")
    val dsiUri = s"jdbc:postgresql://${ip}/puluo"
    val dsiDriver = "org.postgresql.Driver"
    logger.info("dsiUri=%s,dsiDriver=%s".format(dsiUri, dsiDriver))
    new StandardDBVendor(dsiDriver, dsiUri, Full(user), Full("puLuo20!5")) {
      override def maxPoolSize = 50
      override def doNotExpandBeyond = 50
    }
  }

  def setupDB(): Int = {
    /**** database settings ****/
    MapperRules.columnName = (_, name) => StringHelpers.snakify(name)
    MapperRules.tableName = (_, name) => StringHelpers.snakify(name)
    MapperRules.createForeignKeys_? = (_) => true

    logger.info("当前数据库连接:%s".format(DB.currentConnection.isDefined))

    val entities = List(PuluoAdmin, UserImage)

    // handle JNDI not being avalible
    if (!DB.jndiJdbcConnAvailable_?) {
      DB.defineConnectionManager(DefaultConnectionIdentifier, dsi)
      // make sure cyote unloads database connections before shutting down
      LiftRules.unloadHooks.append(() => dsi.closeAllConnections_!())
    }
    // automatically create the tables
    Schemifier.schemify(true, Schemifier.infoF _, entities: _*)
    // setup the loan pattern
    S.addAround(DB.buildLoanWrapper)

    DB.addLogFunc((query, time) => {
      //logger.info(query.allEntries.size + " 数据库查询耗时 " + time + " milliseconds ")
      query.allEntries.foreach(e => {
        if (!e.statement.contains("SELECT") && !e.statement.contains("Closed")) {
          logger.info(
            e.statement + " 耗时 " + e.duration + "ms")
        } else if (e.duration > 100) {
          logger.info(e.statement + " 执行时间过长，" + e.duration + " ms")
        } else {
          logger.debug(e.statement + " 耗时 " + e.duration + "ms")
        }
      })
    })

    entities.size

  }

  def setupEmailServer() = {
    //email server
    System.setProperty("mail.smtp.host", PuluoService.emailHost)
    System.setProperty("mail.smtp.user", PuluoService.emailUsername)
    System.setProperty("mail.smtp.auth", "true")
    System.setProperty("mail.smtp.from", "admin@puluosports.com")
    Mailer.authenticator = Full(new javax.mail.Authenticator {
      override def getPasswordAuthentication(): javax.mail.PasswordAuthentication =
        new javax.mail.PasswordAuthentication(PuluoService.emailUsername, PuluoService.emailPassword)
    })
  }

  def setupNewSiteMap() = {

    SiteMap.enforceUniqueLinks = false
    val menus = List(
      Menu("网站") / "site" / ** >> LocGroup("public"),
      Menu("课程报表") / "event_report" >> LocGroup("public") >> MustBeLoggedIn,
      Menu("图片上传") / "image" >> LocGroup("public") >> MustBeLoggedIn,
      Menu("活动信息更新") / "event_info" >> LocGroup("public") >> MustBeLoggedIn,
      Menu("课程更新") / "event" >> LocGroup("public") >> MustBeLoggedIn,
      Menu("活动信息搜索") / "event_info_search" >> LocGroup("public") >> MustBeLoggedIn,
      Menu("课程搜索") / "event_search" >> LocGroup("public") >> MustBeLoggedIn,
      Menu("用户管理") / "user" >> LocGroup("public") >> MustBeLoggedIn,
      Menu("主页") / "index" >> LocGroup("public")) ::: userMenus
    LiftRules.setSiteMap(SiteMap(menus: _*))
  }

  def userMenus = List(PuluoAdmin.loginMenuLoc.get, PuluoAdmin.logoutMenuLoc.get)

  def setupRequestConfig() = {
    val withAuthentication: PartialFunction[Req, Unit] = {
      case r: Req if S.loggedIn_? =>
    }
    LiftRules.early.append(_.setCharacterEncoding("UTF-8"))
    // setup the 404 handler 
    LiftRules.uriNotFound.prepend(NamedPF("404handler") {
      case (req, failure) => NotFoundAsTemplate(ParsePath(List("404"), "html", false, false))
    })
    LiftRules.addToPackages("com.puluo.snippet")
    LiftRules.addToPackages("com.puluo")
    LiftRules.dispatch.append(PuluoDownloader)
    LiftRules.dispatch.append(withAuthentication guard PuluoFileUploadAPI)

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
    setupJS()
    setupUX()
    setupDB()
    setupEmailServer()
    //setupSiteMap()
    setupNewSiteMap()
    setupRequestConfig()
    setupErrorHandling()
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

  def setupErrorHandling() = {
    LiftRules.exceptionHandler.prepend {
      case (_, _, exception: SizeLimitExceededException) => {
        RedirectResponse("/error") //JavaScriptResponse(JsCmds.Alert("文件大小不能超过2M"))
      }
      case (Props.RunModes.Production, _, exception) => RedirectResponse("/error")
    }
  }
}
