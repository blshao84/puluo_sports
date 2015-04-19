package com.puluo.snippet

import net.liftweb.http.RequestVar
import net.liftweb.util.Helpers._
import net.liftweb.http.SHtml
import net.liftweb.http.js.JsCmds
import net.liftweb.common.Empty
import net.liftweb.http.js.JsCmd
import net.liftweb.http.SHtml.ChoiceHolder
import net.liftweb.http.js.JsCmds.ReplaceOptions
import net.liftweb.common.Full
import org.joda.time.LocalDate
import net.liftweb.http.S
import net.liftweb.common.Loggable
import net.liftweb.http.LiftRules
import net.liftweb.http.js.JsCmds._
import net.liftweb.sitemap.Loc.Value
import bootstrap.liftweb.Boot
import net.liftweb.util.PassThru
import com.puluo.entity.PuluoAdmin


object GreetingMessageSnippet {
  def render = {
    if(S.loggedIn_?){
      val user = PuluoAdmin.currentUser.get
      "#msg *" #> s"您好，${user.puluoUser.mobile()}"
    }else {
      "#msg *"#>"您好，请先登录"
    }
  }
}