package com.puluo.config

import net.liftweb.http.LiftRules
import net.liftweb.sitemap.Menu
import net.liftweb.sitemap.Loc.LocGroup
import net.liftweb.sitemap.NormalLocPath
import net.liftweb.common.Full
import com.puluo.util.StringUtil
import bootstrap.liftweb.Boot
import net.liftweb.common.Loggable
import net.liftweb.json.DefaultFormats
import net.liftweb.json.JsonAST._
import net.liftweb.json._

object ButtonKeyType extends Enumeration {
  type ButtonKeyType = ButtonKeyType.Value
  val FLOOR_PLAN = Value("FLOOR_PLAN")
  val OPENING_TIME = Value("OPENING_TIME")
  val CUSTOMER_SERVICE = Value("CUSTOMER_SERVICE")
  val STORE_EVENT = Value("STORE_EVENT")
  val STORE_PROMOTION = Value("STORE_PROMOTION")
  val LOTTERY = Value("LOTTERY")
  val CHECKIN = Value("CHECKIN")
}
case class WeiChatButtonGroup(val button: Seq[WeiChatButton]) {
  implicit val formats = DefaultFormats
  def toJsonString = Serialization.write(Map("button" -> button.map(_.toJson)))
}
case class WeiChatButton(val buttonType: String, val name: String, val key: String, val url: String, val sub_button: Seq[WeiChatButton]) {
  implicit val formats = DefaultFormats
  def buttonKey: ButtonKeyType.Value = ButtonKeyType.withName(key)
  def toMap: Map[String, Any] = Map(
    "type" -> buttonType,
    "name" -> name,
    "key" -> key,
    "url" -> url) ++
    (if (sub_button.isEmpty)
      Map.empty
    else
      Map("sub_button" -> sub_button.map(_.toMap)))
  def toJson = parse(Serialization.write(toMap))
}

object WeiChatButton {
  def buttons = {
    val allButtons = (LiftRules.loadResourceAsXml("/weichat.xml").get \\ "button").map(button => {
      val buttonType = (button \ "@button_type").toString
      val name = (button \ "@button_name").toString
      val key = (button \ "@button_key").toString
      val url = (button \ "@button_url").toString
      val subButtons = (button \\ "sub_button").map(subButton => {
        val buttonType = (subButton \ "@button_type").toString
        val name = (subButton \ "@button_name").toString
        val key = (subButton \ "@button_key").toString
        val url = (subButton \ "@button_url").toString
        WeiChatButton(buttonType, name, key, url, Seq())
      })
       WeiChatButton(buttonType, name, key, url, subButtons)
    })
    WeiChatButtonGroup(allButtons)
  }
}