package com.puluo.snippet
import scala.collection.JavaConversions._
import java.util.UUID
import net.liftweb.common.Full
import net.liftweb.db.DB
import net.liftweb.db.DB1.db1ToDb
import net.liftweb.db.DefaultConnectionIdentifier
import net.liftweb.http.SHtml
import net.liftweb.http.RequestVar
import net.liftweb.http.js.JsCmds
import net.liftweb.http.js.JsCmds.Alert
import net.liftweb.mapper.By
import net.liftweb.util.AnyVar.whatVarIs
import net.liftweb.util.PassThru
import net.liftweb.common.Loggable
import net.liftweb.http.js.JsCmd
import net.liftweb.common.Empty
import net.liftweb.http.S
import net.liftweb.http.js.JsCmds.ReplaceOptions
import net.liftweb.util.Helpers._
import net.liftweb.mapper.NotBy
import net.liftweb.http.js.JE
import java.io.File
import com.puluo.config.Configurations
import com.puluo.entity.UserImage
import com.puluo.util.Strs
import com.puluo.entity.PuluoAdmin
import com.puluo.snippet.util.SortedChineseMapperPaginator
import com.puluo.service.PuluoService
import com.puluo.snippet.util.PuluoSnippetUtil
import com.puluo.entity.PuluoEvent
import net.liftweb.http.SessionVar
import com.puluo.api.event.EventSearchAPI
import com.puluo.enumeration.EventSortType
import com.puluo.enumeration.SortDirection
import com.puluo.enumeration.EventStatus
import com.puluo.util.TimeUtils
import com.puluo.enumeration.PuluoEventCategory
import com.puluo.entity.PuluoPaymentOrder
import com.puluo.dao.impl.DaoApi

class EventReportSnippet extends PuluoSnippetUtil with Loggable {
  object keyword extends RequestVar[Option[String]](None)
  object orders extends SessionVar[Seq[PuluoPaymentOrder]](Seq.empty)

  def render = {
    "#keyword" #> renderText(keyword) &
      "#search" #> SHtml.ajaxButton("搜索", () => {
        doSearch
        if (orders.size > 0) {
          JsCmds.Reload
        } else {
          val event = DaoApi.getInstance().eventDao().getEventByUUID(keyword.getOrElse(""))
          if (event == null) {
            JsCmds.Alert("您查找的课程不存在")
          } else JsCmds.Alert("该课程暂时无订单")
        }
      }) &
      "#download" #> renderDownload &
      ".event-results *" #> orders.get.zipWithIndex.map(o => renderOrder(o._1, o._2))
  }

  private def renderDownload = {
    SHtml.ajaxButton("下载", () => {
      if (orders.isEmpty) {
        JsCmds.Alert("该课程没有订单，无法下载")
      } else {
        val eventUUID = orders.head.eventId()
        val fileName = s"${eventUUID}.csv"
        JsCmds.RedirectTo("/download", () => {
          val content = generateCSV
          PuluoDownloader.data(content)
          PuluoDownloader.fileName(fileName)
        })
      }
    })
  }

  private def generateCSV = {
    val header = Seq("ID", "电话", "姓名", "锁号", "是否拿毛巾").mkString(",")
    val content = orders.get.zipWithIndex.map {
      case (order, index) =>
        val (mobile, name) = extractNameAndMobile(order)
        Seq(index, mobile, name, "", "").mkString(",")
    }.mkString("\n")
    s"${header}\n${content}"
  }
  private def renderOrder(order: PuluoPaymentOrder, index: Int) = {
    val (mobile, name) = extractNameAndMobile(order)
    "#id *" #> index &
      "#mobile *" #> mobile &
      "#name *" #> name
  }

  private def extractNameAndMobile(order: PuluoPaymentOrder): (String, String) = {
    val user = DaoApi.getInstance().userDao().getByUUID(order.userId())
    val mobile = if (user == null) "N/A" else user.mobile()
    val name = if (user == null) "N/A" else s"${user.lastName()}${user.firstName()}"
    (mobile, name)
  }
  private def doSearch: Unit = {
    orders(Seq())
    if (keyword.get.isDefined) {
      val keys = keyword.get.get
      val paymentOrders = DaoApi.getInstance().paymentDao().getPaidOrdersByEventUUID(keys)
      orders(paymentOrders)
    }
  }

}