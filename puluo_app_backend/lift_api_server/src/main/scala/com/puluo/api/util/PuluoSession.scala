package com.puluo.api.util

import net.liftweb.http.SessionVar
import com.puluo.dao.impl.DaoApi

/**
 * Use this page's mechanism to do session-based authentication 
 * http://timperrett.com/2011/02/23/http-dispatch-guards-using-partial-function/
 */
object PuluoSession extends SessionVar(SessionInfo("",None)){
  def login = is.session.isDefined
  def userMobile = session.userMobile()
  def userUUID = is.userUUID
  def session = is.session.get
}

case class SessionInfo(val userUUID:String, val session:Option[com.puluo.entity.PuluoSession])