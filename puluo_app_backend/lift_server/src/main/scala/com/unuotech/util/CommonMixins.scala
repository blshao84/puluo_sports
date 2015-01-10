package com.unuotech.util

import net.liftweb.common.Loggable

trait CommonMixins extends Loggable{
  def getOrNone[T](o:T):Option[T] = if(o==null) None else Some(o)
}