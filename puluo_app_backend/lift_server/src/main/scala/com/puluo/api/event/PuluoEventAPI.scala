package com.puluo.api.event
import net.liftweb.http.rest.RestHelper
import net.liftweb.http.JsonResponse
import com.puluo.api.util.PuluoSession
import com.puluo.api.util.SessionInfo
import net.liftweb.http.js.JsExp.strToJsExp
import com.puluo.api.util.PuluoResponseFactory
import com.puluo.api.result.EventRegistrationResult
import com.puluo.api.result.EventDetailResult
import com.puluo.api.result.EventMemoryResult
import com.puluo.api.result.EventSearchResult
import com.puluo.api.util.PuluoAPIUtil
import com.puluo.api.util.ErrorResponseResult
import com.puluo.session.PuluoSessionManager
import net.liftweb.common.Loggable
import org.joda.time.DateTime
import com.puluo.entity.EventStatus
import com.puluo.util.SortDirection

object PuluoEventAPI extends RestHelper with PuluoAPIUtil with Loggable {
  serve {
    //FIXME: should be POST?
    case "events" :: "payment" :: eventUUID :: Nil Post _ => doRegister(eventUUID)
    case "events" :: "detail" :: eventUUID :: Nil Post _ => doGetEventDetail(eventUUID)
    case "events" :: "memory" :: eventUUID :: Nil Post _ => doGetEventMemory(eventUUID)
    case "events" :: "search" :: Nil Post _ => doEventSearch()
  }

  private def doRegister(eventUUID: String) = {
    //token must exist because it's authenticated
    val token = PuluoResponseFactory.createParamMap(Seq("token")).values.head
    val session = PuluoSessionManager.getSession(token)
    val userUUID = session.userUUID()
    val api = new EventRegistrationAPI(eventUUID, userUUID)
    safeRun(api)
    PuluoResponseFactory.createJSONResponse(api)
  }

  private def doGetEventDetail(eventUUID: String) = {
    val api = new EventDetailAPI(eventUUID)
    safeRun(api)
    PuluoResponseFactory.createJSONResponse(api)
  }

  private def doGetEventMemory(eventUUID: String) = {
    val maxCount = PuluoResponseFactory.createParamMap(Seq("max_count")).values.headOption.map { cnt =>
      try {
        cnt.toInt
      } catch {
        case e: Exception => {
          logger.warn(s"$cnt is not a valid number, use 10 instead")
          10
        }
      }
    }.getOrElse(10)
    val api = new EventMemoryAPI(eventUUID, maxCount)
    safeRun(api)
    PuluoResponseFactory.createJSONResponse(api)
  }

  private def doEventSearch() = {
    val params = PuluoResponseFactory.createParamMap(Seq(
      "event_date", "keyword", "sort", "sort_direction",
      "user_lattitude", "user_longitude", "status"))
    val eventDate: DateTime = params.get("event_date").map(d =>
      try {
        new DateTime(d.toLong)
      } catch {
        case e: Exception =>
          null
      }).getOrElse(null)
    logger.info(s"creating event search api with:\n${params.mkString("\n")}")
    val keyword = params.getOrElse("keyword", "")
    val level = params.getOrElse("level", "")
    val status = try {
      params.get("status").map(s => EventStatus.valueOf(s)).getOrElse {
        logger.info(s"use default sort param:price:${params.get("status")}")
        EventStatus.Full
      }
    } catch {
      case e: Exception => {
        logger.info(s"use default sort param:price:${params.get("status")}")
        EventStatus.Full
      }
    }
    val sort = try {
      params.get("sort").map(s => EventSortType.valueOf(s)).getOrElse {
        logger.info(s"use default sort param:price:${params.get("sort")}")
        EventSortType.Price
      }
    } catch {
      case e: Exception => {
        logger.info(s"use default sort param:price:${params.get("sort")}")
        EventSortType.Price
      }
    }
    val sortDirection = try {
      params.get("sort_direction").map(SortDirection.valueOf(_)).getOrElse {
        logger.info(s"use default sort direction:${params.get("sort_direction")}")
        SortDirection.Asc
      }
    } catch {
      case e: Exception => {
        logger.info(s"use default sort direction:${params.get("sort_direction")}")
        SortDirection.Asc
      }
    }
    val api = (locationToDouble(params.get("user_lattitude")),
      locationToDouble(params.get("user_longitude"))) match {
        case (Some(lattitude), Some(longitude)) => {
          logger.info("api has longitude and lattitude")
          new EventSearchAPI(eventDate, keyword, level, sort, sortDirection, lattitude, longitude, status)
        }
        case _ => new EventSearchAPI(eventDate, keyword, level, sort, sortDirection, 0.0, 0.0, status)
      }
    safeRun(api)
    PuluoResponseFactory.createJSONResponse(api)

  }

  private def locationToDouble(pos: Option[String]): Option[Double] = {
    pos.map(p => try {
      Some(p.toDouble)
    } catch { case e: Exception => None }).flatten
  }
}