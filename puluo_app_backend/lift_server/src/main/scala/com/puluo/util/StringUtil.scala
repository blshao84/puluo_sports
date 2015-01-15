package com.puluo.util

import com.puluo.config.Configurations
import net.sourceforge.pinyin4j.PinyinHelper

object StringUtil {
  val Empty = ""
  def isEmpty(str: String) = if (str == null) true else str.trim.isEmpty()
  def isInt(str: String) = try {
    str.toInt
    true
  } catch {
    case e: Exception => false
  }
  def isDouble(str: String) = try {
    str.toDouble
    true
  } catch {
    case e: Exception => false
  }
  def isLong(str: String) = try {
    str.toLong
    true
  } catch {
    case e: Exception => false
  }
  def isFloat(str: String) = try {
    str.toFloat
    true
  } catch {
    case e: Exception => false
  }

  def isMobile(str: String): (Boolean, String) = {
    if (str.size < 10) (false, "电话号码过短")
    else if (str.size > 12) (false, "电话号码过长")
    else if (str.find(_.isDigit == false).isDefined) (false, "电话含有非数字字符")
    else (true, StringUtil.Empty)
  }
  def displayedEmail(email: String) = shorten(email, Configurations.maxEmailLength)
  def shorten(str: String, max: Int) = {
    if (str.length > max) {
      str.take(max) + "..."
    } else str
  }
  def prettyDouble(d: Double, format: Int = 1) = {
    val formatter = "%." + format + "f"
    d.formatted(formatter)
  }
  def toSelectionOptions(opts: Iterable[String]): List[(String, String)] = ("", "") :: opts.map(o => (o, o)).toList
  def replaceAmp(str: String) = str.replaceAll("&", "%26")

  val pinyinComparator = new PinyinComparator()
  def pinyinSort(strs: Seq[String]): Seq[String] = {
    strs.sortWith((o1, o2) => pinyinComparator.compare(o1, o2) < 0)
  }
  def getPinyinInitial(s: String): String = {
    if (isEmpty(s)) "" else {
      val firstChar = s.head
      if (isLetterOrDigit(firstChar)) firstChar.toString else {
        val pinyins = PinyinHelper.toHanyuPinyinStringArray(firstChar)
        if (pinyins != null && !pinyins.isEmpty) {
          val primaryPronunciation = pinyins.head
          primaryPronunciation.headOption.map(_.toString.toUpperCase).getOrElse("")
        } else ""
      }
    }
  }
  def isLetterOrDigit(c:Char) = (c>='a' && c<='z') || (c>='A' && c<='Z') || (c>='0' && c<='9')
}