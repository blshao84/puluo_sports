package com.puluo.util

import java.text.SimpleDateFormat
import java.util.Date
import org.joda.time.Interval
import org.joda.time.LocalDate
import net.liftweb.http.RequestVar
import net.liftweb.http.js.JsCmds
import net.liftweb.http.SessionVar
import org.joda.time.LocalDateTime

object DateUtil {
  val dateFormat = new SimpleDateFormat("yyyy-MM-dd")
  val dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

  def today = LocalDateTime.now.toDate
  def yesterday = LocalDateTime.now.minusDays(1).toDate
  def tomorrow = LocalDateTime.now.plusDays(1).toDate
  def nextYear = LocalDateTime.now.plusYears(1).toDate
  def dateRange(next: Int): Seq[Date] = Range(1, 1 + next).map(LocalDateTime.now.plusDays(_).toDate)
  def parseDateTime(d: String) = {
    val dateTimeFormat = org.joda.time.format.DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")
    LocalDateTime.parse(d, dateTimeFormat).toDate
  }
  def parseSimpleDate(d: String) = {
    dateFormat parse d
  }
  def formatDateTime(date: Date): String = if (date != null) dateTimeFormat format date else ""
  def formatSimpleDate(date: Date): String = if (date != null) {
    dateFormat format date
  } else StringUtil.Empty
  def formatSimpleDate(date: Option[Date]): String = date match {
    case Some(d) => formatSimpleDate(d)
    case _ => StringUtil.Empty
  }

  def spiltInterval(interval: Interval): (Long, Long, Long, Long) = {
    val duration = interval.toDuration()
    val days = duration.getStandardDays()
    val hours = duration.getStandardHours()
    val mins = duration.getStandardMinutes()
    val secs = duration.getStandardSeconds()
    val remainingHours = hours - 24 * days
    val remainingMins = mins - 60 * hours
    val remainingSecs = secs - 60 * mins
    (days, remainingHours, remainingMins, remainingSecs)
  }
  def setDate(s: String, at: SessionVar[Option[Date]]) = {
    try {
      val date = DateUtil.parseSimpleDate(s)
      at(Some(date))
      JsCmds.Noop
    } catch {
      case e: Exception => if (StringUtil.isEmpty(s)) JsCmds.Noop else JsCmds.Alert("您输入的日期格式不正确")
    }
  }
  def setDate(s: String, at: RequestVar[Option[Date]]) = {
    try {
      val date = DateUtil.parseSimpleDate(s)
      at(Some(date))
      JsCmds.Noop
    } catch {
      case e: Exception => if (StringUtil.isEmpty(s)) JsCmds.Noop else JsCmds.Alert("您输入的日期格式不正确")
    }
  }

  def diffInDays(date1: Date, date2: Date): Long = {
    val diff = date1.getTime - date2.getTime
    diff / (24*60*60 * 1000) % 60
  }
  
  def diffInHours(date1: Date, date2: Date): Long = {
    val diff = date1.getTime - date2.getTime
    diff / (60*60 * 1000) % 60
  }
}