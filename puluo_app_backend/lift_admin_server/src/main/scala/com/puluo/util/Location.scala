package com.puluo.util

import net.liftweb.http.LiftRules

object Location {
  private val provinces = {
    (LiftRules.loadResourceAsXml("/provinces.xml").get \\ "province").map(province => {
      val name = (province \ "@name").toString()
      val id = (province \ "@id").toString()
      val cities = (province \\ "city").map(city => ((city \ "@name").toString, (city \ "@id").toString)).toMap
      (name, (id, cities))
    }).toMap
  }
  
  val states = provinces.keys.toSeq
  
  def getCity(s: String): List[String] = {
    provinces.get(s) match {
      case Some(tmp) => tmp._2.toSeq.sortBy(_._2.toInt).map(_._1).toList
      case None => List.empty
    }
  }
  
  def main(args:Array[String]):Unit = {
    provinces foreach println
  }
}