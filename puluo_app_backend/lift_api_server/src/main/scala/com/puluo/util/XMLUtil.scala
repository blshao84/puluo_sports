package com.puluo.util
import scala.xml.Elem
import scala.xml.Node

object XMLUtil {
  def addChild(n: Node, newChild: Node) = n match {
    case Elem(prefix, label, attribs, scope, child @ _*) =>
      Elem(prefix, label, attribs, scope,false,child ++ newChild: _*)
    case _ => throw new XMLException("Can only add children to elements!")
  }
  
  def getChildren(n:Node) = n match {
    case Elem(_, _, _, _, child @ _*) => child
    case _ => throw new XMLException("Can only add children to elements!")
  }
}

class XMLException(error:String) extends Exception