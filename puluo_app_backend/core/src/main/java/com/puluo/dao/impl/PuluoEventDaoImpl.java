package com.puluo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.jdbc.core.RowMapper;

import com.puluo.dao.PuluoEventDao;
import com.puluo.entity.PuluoEvent;
import com.puluo.entity.PuluoEventInfo;
import com.puluo.entity.PuluoEventLocation;
import com.puluo.entity.impl.PuluoEventImpl;
import com.puluo.enumeration.EventSortType;
import com.puluo.enumeration.EventStatus;
import com.puluo.enumeration.PuluoEventCategory;
import com.puluo.enumeration.PuluoEventLevel;
import com.puluo.enumeration.SortDirection;
import com.puluo.jdbc.DalTemplate;
import com.puluo.jdbc.SqlReader;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.PuluoDatabaseException;
import com.puluo.util.Strs;
import com.puluo.util.TimeUtils;

public class PuluoEventDaoImpl extends DalTemplate implements PuluoEventDao {

	public static Log log = LogFactory.getLog(PuluoEventDaoImpl.class);

	@Override
	public boolean createTable() {
		try {
			String createSQL = new StringBuilder().append("create table ")
					.append(super.getFullTableName())
					.append(" (id serial primary key, ")
					.append("event_uuid text unique, ")
					.append("event_time timestamp, ").append("status text, ")
					.append("registered_users int, ").append("capatcity int, ")
					.append("price double precision, ")
					.append("discounted_price double precision, ")
					.append("info_uuid text, ").append("location_uuid text, ")
					.append("hottest int default 0)").toString();
			log.info(createSQL);
			getWriter().execute(createSQL);

			String indexSQL1 = new StringBuilder()
					.append("create index " + super.getFullTableName()
							+ "_i_id on ").append(super.getFullTableName())
					.append(" (id)").toString();
			log.info(indexSQL1);
			getWriter().execute(indexSQL1);

			String indexSQL2 = new StringBuilder()
					.append("create index " + super.getFullTableName()
							+ "_i_event_uuid on ")
					.append(super.getFullTableName()).append(" (event_uuid)")
					.toString();
			log.info(indexSQL2);
			getWriter().execute(indexSQL2);

			String indexSQL3 = new StringBuilder()
					.append("create index " + super.getFullTableName()
							+ "_i_info_uuid on ")
					.append(super.getFullTableName()).append(" (info_uuid)")
					.toString();
			log.info(indexSQL3);
			getWriter().execute(indexSQL3);

			String indexSQL4 = new StringBuilder()
					.append("create index " + super.getFullTableName()
							+ "_i_location_uuid on ")
					.append(super.getFullTableName())
					.append(" (location_uuid)").toString();
			log.info(indexSQL1);
			getWriter().execute(indexSQL4);

			return true;
		} catch (Exception e) {
			log.debug(e.getMessage());
			return false;
		}
	}

	public boolean deleteByEventUUID(String uuid) {
		return super.deleteByUniqueKey("event_uuid", uuid);
	}

	@Override
	public PuluoEvent getEventByUUID(String idevent) {
		SqlReader reader = getReader();
		String selectSQL = new StringBuilder().append("select * from ")
				.append(super.getFullTableName())
				.append(" where event_uuid = ?").toString();
		log.info(sqlRequestLog(selectSQL, idevent));
		List<PuluoEvent> entities = reader.query(selectSQL,
				new Object[] { idevent }, new PuluoEventMapper());
		return verifyUniqueResult(entities);
	}

	@Override
	public List<PuluoEvent> findEvents(DateTime event_from_date,
			DateTime event_to_date, String keyword, PuluoEventLevel level,
			EventSortType sort, SortDirection sort_direction, double latitude,
			double longitude, double range_from, EventStatus es,
			PuluoEventCategory type, int limit,int offset) {
		ArrayList<String> params = new ArrayList<String>();
		String dateQuery = null;
		if (event_from_date != null && event_to_date != null) {
			StringBuffer sb = new StringBuffer();
			sb.append(" and e.event_time >= '")
					.append(TimeUtils.formatDate(event_from_date))
					.append("' and e.event_time < '")
					.append(TimeUtils.formatDate(event_to_date)).append("'");
			dateQuery = sb.toString();
		} else if (event_from_date == null && event_to_date != null) {
			StringBuffer sb = new StringBuffer();
			sb.append(" and e.event_time < '")
					.append(TimeUtils.formatDate(event_to_date)).append("'");
			dateQuery = sb.toString();
		} else if (event_from_date != null && event_to_date == null) {
			StringBuffer sb = new StringBuffer();
			sb.append(" and e.event_time >= '")
					.append(TimeUtils.formatDate(event_from_date)).append("'");
			dateQuery = sb.toString();
		} else {
			dateQuery = null;
		}
		if (dateQuery != null) {
			params.add(dateQuery);
		}
		if (level != null) {
			params.add(" and i.event_level = '" + level.name() + "'");
		}
		if (keyword != null) {
			params.add(" and (position('" + keyword
					+ "' in i.event_name)>0 or position('" + keyword
					+ "' in i.description)>0)");
		}
		if (range_from != 0.0) {
			params.add(" and power(power(l.latitude-" + latitude
					+ ", 2) + power(l.longitude-" + longitude
					+ ", 2), 0.5) <= " + range_from);
		}
		if (es != null && !EventStatus.Full.equals(es)) {
			params.add(" and e.status = '" + es.name() + "'");
		}
		if (type != null) {
			params.add(" and i.event_type = '" + type.name() + "'");
		}
		StringBuilder orderBy = new StringBuilder("");
		if (EventSortType.Time.equals(sort)) {
			orderBy.append(" order by e.event_time "
					+ sort_direction.name().toLowerCase());
		} else if (EventSortType.Distance.equals(sort)) {
			orderBy.append(" order by power(power(l.latitude-" + latitude
					+ ", 2) + power(l.longitude-" + longitude + ", 2), 0.5) "
					+ sort_direction.name().toLowerCase());
		} else if (EventSortType.Price.equals(sort)) {
			orderBy.append(" order by e.discounted_price "
					+ sort_direction.name().toLowerCase());
		}
		SqlReader reader = getReader();
		StringBuilder selectSQL = new StringBuilder()
				.append("select e.* from ")
				.append(super.getFullTableName()
						+ " e, puluo_event_info i, puluo_event_location l")
				.append(" where e.info_uuid = i.event_info_uuid and e.location_uuid = l.location_uuid");
		for (String tmp : params) {
			selectSQL.append(tmp);
		}
		selectSQL.append(orderBy.toString())
		.append(" limit ").append(limit).append(" offset ").append(offset);
		log.info(selectSQL.toString());
		List<PuluoEvent> entities = reader.query(selectSQL.toString(),
				new Object[] {}, new PuluoEventMapper());
		return entities;
	}

	@Override
	public boolean upsertEvent(PuluoEvent event) {
		try {
			SqlReader reader = getReader();
			String querySQL = new StringBuilder()
					.append("select count(1) from ")
					.append(super.getFullTableName())
					.append(" where event_uuid = '" + event.eventUUID() + "'")
					.toString();
			log.info(sqlRequestLog(querySQL, event.eventUUID()));
			int resCnt = reader.queryForInt(querySQL);
			if (resCnt > 0) {
				return this.updateEvent(event);
			} else {
				return this.saveEvent(event);
			}
		} catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}
	}

	@Override
	public boolean saveEvent(PuluoEvent event) {

		try {
			SqlReader reader = getReader();
			String querySQL = new StringBuilder()
					.append("select count(1) from ")
					.append(super.getFullTableName())
					.append(" where event_uuid = '" + event.eventUUID() + "'")
					.toString();
			log.info(sqlRequestLog(querySQL, event.eventUUID()));
			int resCnt = reader.queryForInt(querySQL);
			String updateSQL;
			if (resCnt == 0) {
				PuluoEventInfo info = event.eventInfo();
				PuluoEventLocation loc = event.eventLocation();
				updateSQL = new StringBuilder()
						.append("insert into ")
						.append(super.getFullTableName())
						.append(" (event_uuid, event_time, status, registered_users, capatcity, price, discounted_price, info_uuid, location_uuid, hottest)")
						.append("values ('" + event.eventUUID() + "', ")
						.append(Strs.isEmpty(TimeUtils.formatDate(event
								.eventTime())) ? "null" : "'"
								+ TimeUtils.formatDate(event.eventTime()) + "'")
						.append(", ")
						.append(Strs.isEmpty(event.statusName()) ? "null" : "'"
								+ event.statusName() + "'")
						.append(", ")
//						.append(event.registeredUsers() + ", ")
						.append("0, ")
						.append(event.capatcity() + ", ")
						.append(event.originalPrice() + ", ")
						.append(event.discountedPrice() + ", ")
						.append((info ==null || Strs.isEmpty(event.eventInfo().eventInfoUUID())) ? "null"
								: "'" + event.eventInfo().eventInfoUUID() + "'")
						.append(", ")
						.append((loc==null || Strs.isEmpty(event.eventLocation().locationId())) ? "null"
								: "'" + event.eventLocation().locationId()
										+ "'")
						.append(",").append(event.hottest()).append(")").toString();
				log.info(Strs.join("SQL:", updateSQL));
				getWriter().update(updateSQL);
				return true;
			} else {
				throw new PuluoDatabaseException("event_uuid为'"
						+ event.eventUUID() + "'已存在不能插入数据！");
			}
		} catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}
	}

	@Override
	public boolean updateEvent(PuluoEvent event) {
		try {
			SqlReader reader = getReader();
			String querySQL = new StringBuilder()
					.append("select count(1) from ")
					.append(super.getFullTableName())
					.append(" where event_uuid = '" + event.eventUUID() + "'")
					.toString();
			log.info(Strs.join("SQL:", querySQL));
			int resCnt = reader.queryForInt(querySQL);
			StringBuilder updateSQL;
			if (resCnt > 0) {
				updateSQL = new StringBuilder().append("update ")
						.append(super.getFullTableName()).append(" set");
				if (!Strs.isEmpty(TimeUtils.formatDate(event.eventTime()))) {
					updateSQL.append(" event_time = '"
							+ TimeUtils.formatDate(event.eventTime()) + "',");
				}
				if(event.hottest()>0){
					updateSQL.append(" hottest = "+event.hottest()+",");
				}
				if (!Strs.isEmpty(event.statusName())) {
					updateSQL.append(" status = '" + event.statusName() + "',");
				}
				updateSQL
//						.append(" registered_users = " + event.registeredUsers() + ",")
						.append(" registered_users = 0,")
						.append(" capatcity = " + event.capatcity() + ",")
						.append(" price = " + event.originalPrice() + ",")
						.append(" discounted_price = "
								+ event.discountedPrice() + ",");
				if (!Strs.isEmpty(event.eventInfo().eventInfoUUID())) {
					updateSQL.append(" info_uuid = '"
							+ event.eventInfo().eventInfoUUID() + "',");
				}
				if (!Strs.isEmpty(event.eventLocation().locationId())) {
					updateSQL.append(" location_uuid = '"
							+ event.eventLocation().locationId() + "',");
				}
				updateSQL.deleteCharAt(updateSQL.length() - 1);
				updateSQL.append(" where event_uuid = '" + event.eventUUID()
						+ "'");
				log.info(Strs.join("SQL:", updateSQL.toString()));
				getWriter().update(updateSQL.toString());
				return true;
			} else {
				throw new PuluoDatabaseException("event_uuid为'"
						+ event.eventUUID() + "'不存在不能更新数据！");
			}
		} catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}
	}

	@Override
	public List<PuluoEvent> findPopularEvent(int popularity){
		SqlReader reader = getReader();
		String selectSQL = new StringBuilder().append("select * from ")
				.append(super.getFullTableName())
				.append(" where hottest > ? and status <> '").append(EventStatus.Closed).append("'").toString();
		log.info(sqlRequestLog(selectSQL, popularity));
		List<PuluoEvent> entities = reader.query(selectSQL,
				new Object[] { popularity }, new PuluoEventMapper());
		return entities;
	}
}

class PuluoEventMapper implements RowMapper<PuluoEvent> {
	@Override
	public PuluoEvent mapRow(ResultSet rs, int rowNum) throws SQLException {
		EventStatus es = EventStatus.valueOf(rs.getString("status"));
		PuluoEventImpl event = new PuluoEventImpl(rs.getString("event_uuid"),
				TimeUtils.parseDateTime(TimeUtils.formatDate(rs
						.getTimestamp("event_time"))), es,
				rs.getInt("registered_users"), rs.getInt("capatcity"),
				rs.getDouble("price"), rs.getDouble("discounted_price"),
				rs.getString("info_uuid"), rs.getString("location_uuid"),
				rs.getInt("hottest"));
		return event;
	}
}
