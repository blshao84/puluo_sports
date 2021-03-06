package com.puluo.api.event
import net.liftweb.http.rest.RestHelper
import net.liftweb.http.JsonResponse
import com.puluo.api.util.PuluoSession
import com.puluo.api.util.SessionInfo
import net.liftweb.http.js.JsExp.strToJsExp
import com.puluo.api.util.PuluoResponseFactory
import com.puluo.api.util.PuluoAPIUtil
import com.puluo.api.util.ErrorResponseResult
import com.puluo.session.PuluoSessionManager
import net.liftweb.common.Loggable
import org.joda.time.DateTime
import com.puluo.enumeration._
import com.puluo.api.service.PuluoServiceAPI

object PuluoEventAPI extends RestHelper with PuluoAPIUtil with Loggable {
  serve {
    case "events" :: "registered" :: Nil Post _ => callWithAuthParam()(doGetRegisteredEvents)
    //TODO: will remove it later
    case "events" :: "configurations" :: Nil Post _ => callWithAuthParam()(PuluoServiceAPI.doGetConfigurations)
    case "events" :: "payment" :: eventUUID :: Nil Post _ => doRegister(eventUUID)
    case "events" :: "detail" :: eventUUID :: Nil Post _ => doGetEventDetail(eventUUID)
    case "events" :: "memory" :: eventUUID :: Nil Post _ => doGetEventMemory(eventUUID)
    case "events" :: "search" :: Nil Post _ => doEventSearch()
  }
  
  private def doGetRegisteredEvents(params:Map[String,String]) = {
    val token = PuluoResponseFactory.createParamMap(Seq("token")).values.head
    val session = PuluoSessionManager.getSession(token)
    val userUUID = session.userUUID()
    logger.info(s"user ${userUUID} is requesting registered events")
    val api = new RegisteredEventSearchAPI(userUUID)
    safeRun(api)
    PuluoResponseFactory.createJSONResponse(api)
  }
  

  private def doRegister(eventUUID: String) = {
    //token must exist because it's authenticated
    val params = PuluoResponseFactory.createParamMap(Seq("token","mock"))
    val token = params("token")
    val session = PuluoSessionManager.getSession(token)
    val userUUID = session.userUUID()
    val mock = params.get("mock").map(_=="true").getOrElse(false)
    val api = new EventRegistrationAPI(eventUUID, userUUID,PuluoPartner.PuluoApp,mock)
    safeRun(api)
    PuluoResponseFactory.createJSONResponse(api)
  }

  private def doGetEventDetail(eventUUID: String) = {val token = PuluoResponseFactory.createParamMap(Seq("token")).values.head
    val session = PuluoSessionManager.getSession(token)
    val userUUID = session.userUUID()
    val api = new EventDetailAPI(eventUUID, userUUID)
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
      "event_from_date", "event_to_date", "keyword", "level", "sort", "sort_direction",
      "user_lattitude", "user_longitude", "status", "type","limit","offset"))
    val eventFromDate: DateTime = getEventDate(params, "event_from_date")
    val eventToDate: DateTime = getEventDate(params, "event_to_date")
    logger.info(s"creating event search api with:\n${params.mkString("\n")}")
    val keyword = params.getOrElse("keyword", "")
    val level: PuluoEventLevel = try {
      PuluoEventLevel.valueOf(params.getOrElse("level", ""))
    } catch {
      case e: Exception => null
    }
    val category: PuluoEventCategory = try {
      PuluoEventCategory.valueOf(params.getOrElse("type", ""))
    } catch {
      case e: Exception => null
    }
    val status = try {
      params.get("status").map(s => EventStatus.valueOf(s)).getOrElse {
        logger.info(s"use default sort param:price:${params.get("status")}")
        EventStatus.Open
      }
    } catch {
      case e: Exception => {
        logger.info(s"use default sort param:price:${params.get("status")}")
        EventStatus.Open
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
    val limit = getIntParam(params, "limit", 20)
    val offset = getIntParam(params, "offset", 0)
    val api = (locationToDouble(params.get("user_lattitude")),
      locationToDouble(params.get("user_longitude"))) match {
        case (Some(lattitude), Some(longitude)) => {
          logger.info("api has longitude and lattitude")
          new EventSearchAPI(eventFromDate, eventToDate, keyword, level, sort, sortDirection, lattitude, longitude, 0.0, status, category,limit,offset)
        }
        case _ => new EventSearchAPI(eventFromDate, eventToDate, keyword, level, sort, sortDirection, 0.0, 0.0, 0.0, status, category,limit,offset)
      }
    safeRun(api)
    PuluoResponseFactory.createJSONResponse(api)

  }

  private def locationToDouble(pos: Option[String]): Option[Double] = {
    pos.map(p => try {
      Some(p.toDouble)
    } catch { case e: Exception => None }).flatten
  }

  private def getEventDate(params: Map[String, String], dateType: String): DateTime = {
    params.get(dateType).map(d =>
      try {
        new DateTime(d.toLong)
      } catch {
        case e: Exception =>
          null
      }).getOrElse(null)
  }
}