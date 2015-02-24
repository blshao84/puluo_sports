package com.puluo.api.util

import net.liftweb.http.SessionVar

/**
 * Use this page's mechanism to do session-based authentication 
 * http://timperrett.com/2011/02/23/http-dispatch-guards-using-partial-function/
 */
object PuluoSession extends SessionVar(SessionInfo(None)){
  def login = is.session.isDefined
}

case class SessionInfo(val session:Option[com.puluo.entity.PuluoSession])