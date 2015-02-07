package com.puluo.api.util

import net.liftweb.http.SessionVar

/**
 * Use this page's mechanism to do session-based authentication 
 * http://timperrett.com/2011/02/23/http-dispatch-guards-using-partial-function/
 */
object PuluoSession extends SessionVar(SessionInfo(false)){
  def login = is.login
}

case class SessionInfo(val login:Boolean)