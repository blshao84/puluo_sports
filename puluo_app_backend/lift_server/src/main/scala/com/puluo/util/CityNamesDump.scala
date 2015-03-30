package com.puluo.util

import scala.xml.XML
import net.sourceforge.pinyin4j.PinyinHelper

object CityNamesDump {
  def main(args:Array[String]) = {
    dump
  }
  
  def dump = {
    val xml = XML.loadFile("/Users/blshao/Documents/provinces.xml")
    val provinceXML = xml \\ "province"
    val provinceNames = provinceXML.map(province => (province \ "@name").toString)
    val cities = provinceXML.map(province => province \ "city")
    val cityNames = cities.map(_.map(c => (c \ "@name").toString)).flatten.toSet
    val provincePinyinNames = provinceNames.map(p => (p, p.map(c => PinyinHelper.toHanyuPinyinStringArray(c).head))).toMap
    val cityPinyinNames = cityNames.map(p => (p, p.map(c => PinyinHelper.toHanyuPinyinStringArray(c).head))).toMap
    val ptrans = provincePinyinNames.map(p => (p._1, p._2.map(c => c.dropRight(1)).mkString("")))
    val ctrans = cityPinyinNames.map(p => (p._1, p._2.map(c => c.dropRight(1)).mkString("")))
    val res29 = ptrans.map(p => (p._1, toUpper(p._2)))
    val res30 = ctrans.map(p => (p._1, toUpper(p._2)))
    res29.foreach(p => println(p._1 + "\t" + p._2))
    res30.foreach(p => println(p._1 + "\t" + p._2))
  }
  def toUpper(s: String) = {
    val head = s.head
    val tail = s.tail
    val newStr = head.toUpper :: tail.map(_.toLower).toList
    newStr.mkString("")
  }
}