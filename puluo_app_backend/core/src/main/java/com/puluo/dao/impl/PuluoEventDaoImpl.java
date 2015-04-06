package com.puluo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.jdbc.core.RowMapper;

import com.puluo.api.event.EventSortType;
import com.puluo.dao.PuluoEventDao;
import com.puluo.entity.EventStatus;
import com.puluo.entity.PuluoEvent;
import com.puluo.entity.impl.PuluoEventImpl;
import com.puluo.jdbc.DalTemplate;
import com.puluo.jdbc.SqlReader;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.PuluoDatabaseException;
import com.puluo.util.SortDirection;
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
				.append("event_time timestamp, ")
				.append("status text, ")
				.append("registered_users int, ")
				.append("capatcity int, ")
				.append("price double precision, ")
				.append("discounted_price double precision, ")
				.append("info_uuid text, ")
				.append("location_uuid text)")
				.toString();
			log.info(createSQL);
			getWriter().execute(createSQL);
			// TODO create index
		} catch (Exception e) {
			log.debug(e.getMessage());
			return false;
		}
		return true;
	}
	
	public boolean deleteByEventUUID(String uuid){
		return super.deleteByUniqueKey("event_uuid", uuid);
	}

	@Override
	public PuluoEvent getEventByUUID(String idevent) {
		SqlReader reader = getReader();
		StringBuilder selectSQL = new StringBuilder().append("select * from ")
				.append(super.getFullTableName())
				.append(" where event_uuid = ?");
		List<PuluoEvent> entities = reader.query(selectSQL.toString(), new Object[]{idevent},
				new RowMapper<PuluoEvent>() {
					@Override
					public PuluoEvent mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						EventStatus es = EventStatus.valueOf(rs.getString("status"));
						PuluoEventImpl event = new PuluoEventImpl(
								rs.getString("event_uuid"),
								TimeUtils.parseDateTime(TimeUtils.formatDate(rs.getTimestamp("event_time"))),
								es,
								rs.getInt("registered_users"),
								rs.getInt("capatcity"),
								rs.getDouble("price"),
								rs.getDouble("discounted_price"),
								rs.getString("info_uuid"),
								rs.getString("location_uuid"));
						return event;
					}
				});
		if (entities.size() == 1)
			return entities.get(0);
		else if (entities.size() > 1)
			throw new PuluoDatabaseException("通过event uuid查到多个event！");
		else
			return null;
	}
	
	@Override
	public List<PuluoEvent> findEvents(DateTime event_date, String keyword, String level, 
			EventSortType sort, SortDirection sort_direction, double latitude, double longitude, double range_from) {
		ArrayList<String> params = new ArrayList<String>();
		if (event_date!=null) {
			params.add(" and e.event_time = '" + TimeUtils.formatDate(event_date) + "'");
		}
		if (keyword!=null) {
			params.add(" and (position('" + keyword + "' in i.event_name)>0 or position('" + keyword + "' in i.description)>0)");
		}
		if (range_from!=0.0) {
			params.add(" and power(power(l.latitude-" + latitude + ", 2) + power(l.longitude-" + longitude + ", 2), 0.5) <= " + range_from);
		}
		StringBuilder orderBy = new StringBuilder("");
		if (EventSortType.Time.equals(sort)) {
			orderBy.append(" order by e.event_time " + sort_direction);
		} else if (EventSortType.Distance.equals(sort)) {
			orderBy.append(" order by power(power(l.latitude-" + latitude + ", 2) + power(l.longitude-" + longitude + ", 2), 0.5) " + sort_direction);
		} else if (EventSortType.Price.equals(sort)) {
			orderBy.append(" order by e.discounted_price " + sort_direction);
		}
		SqlReader reader = getReader();
		StringBuilder selectSQL = new StringBuilder().append("select e.* from ")
				.append(super.getFullTableName() + " e, puluo_event_info i, puluo_event_location l")
				.append(" where e.info_uuid = i.event_info_uuid and e.location_uuid = l.location_uuid");
		for (String tmp: params) {
			selectSQL.append(tmp);
		}
		selectSQL.append(orderBy.toString());
		log.info(selectSQL.toString());
		List<PuluoEvent> entities = reader.query(selectSQL.toString(), new Object[]{},
				new RowMapper<PuluoEvent>() {
					@Override
					public PuluoEvent mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						EventStatus es = EventStatus.valueOf(rs.getString("status"));
						PuluoEventImpl event = new PuluoEventImpl(
								rs.getString("event_uuid"),
								TimeUtils.parseDateTime(TimeUtils.formatDate(rs.getTimestamp("event_time"))),
								es,
								rs.getInt("registered_users"),
								rs.getInt("capatcity"),
								rs.getDouble("price"),
								rs.getDouble("discounted_price"),
								rs.getString("info_uuid"),
								rs.getString("location_uuid"));
						return event;
					}
				});
		return entities;
	}

	@Override
	public boolean upsertEvent(PuluoEvent event) {
		try {
			SqlReader reader = getReader();
			String querySQL = new StringBuilder().append("select count(1) from ")
					.append(super.getFullTableName())
					.append(" where event_uuid = '" + event.eventUUID() + "'")
					.toString();
			log.info(reader.queryForInt(querySQL));
			if (reader.queryForInt(querySQL)>0) {
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
			String querySQL = new StringBuilder().append("select count(1) from ")
					.append(super.getFullTableName())
					.append(" where event_uuid = '" + event.eventUUID() + "'")
					.toString();
			log.info(reader.queryForInt(querySQL));
			String updateSQL;
			if (reader.queryForInt(querySQL)==0) {
				updateSQL = new StringBuilder().append("insert into ")
						.append(super.getFullTableName())
						.append(" (event_uuid, event_time, status, registered_users, capatcity, price, discounted_price, info_uuid, location_uuid)")
						.append("values ('" + event.eventUUID() + "', ")
						.append(Strs.isEmpty(TimeUtils.formatDate(event.eventTime())) ? "null" : "'" + TimeUtils.formatDate(event.eventTime()) + "'").append(", ")
						.append(Strs.isEmpty(event.statusName()) ? "null" : "'" + event.statusName() + "'").append(", ")
						.append(event.registeredUsers() + ", ")
						.append(event.capatcity() + ", ")
						.append(event.price() + ", ")
						.append(event.discountedPrice() + ", ")
						.append(Strs.isEmpty(event.eventInfo().eventInfoUUID()) ? "null" : "'" + event.eventInfo().eventInfoUUID() + "'").append(", ")
						.append(Strs.isEmpty(event.eventLocation().locationId()) ? "null" : "'" + event.eventLocation().locationId() + "'").append(")")
						.toString();
				log.info(updateSQL);
				getWriter().update(updateSQL);
				return true;
			} else {
				throw new PuluoDatabaseException("event_uuid为'" + event.eventUUID() + "'已存在不能插入数据！");
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
			String querySQL = new StringBuilder().append("select count(1) from ")
					.append(super.getFullTableName())
					.append(" where event_uuid = '" + event.eventUUID() + "'")
					.toString();
			log.info(reader.queryForInt(querySQL));
			StringBuilder updateSQL;
			if (reader.queryForInt(querySQL)>0) {
				updateSQL = new StringBuilder().append("update ")
						.append(super.getFullTableName()).append(" set");
				if (!Strs.isEmpty(TimeUtils.formatDate(event.eventTime()))) {
					updateSQL.append(" event_time = '" + TimeUtils.formatDate(event.eventTime()) + "',");
				}
				if (!Strs.isEmpty(event.statusName())) {
					updateSQL.append(" status = '" + event.statusName() + "',");
				}
				updateSQL.append(" registered_users = " + event.registeredUsers() + ",")
						.append(" capatcity = " + event.capatcity() + ",")
						.append(" price = " + event.price() + ",")
						.append(" discounted_price = " + event.discountedPrice() + ",");
				if (!Strs.isEmpty(event.eventInfo().eventInfoUUID())) {
					updateSQL.append(" info_uuid = '" + event.eventInfo().eventInfoUUID() + "',");
				}
				if (!Strs.isEmpty(event.eventLocation().locationId())) {
					updateSQL.append(" location_uuid = '" + event.eventLocation().locationId() + "',");
				}
				updateSQL.deleteCharAt(updateSQL.length()-1);
				updateSQL.append(" where event_uuid = '" + event.eventUUID() + "'");
				log.info(updateSQL.toString());
				getWriter().update(updateSQL.toString());
				return true;
			} else {
				throw new PuluoDatabaseException("event_uuid为'" + event.eventUUID() + "'不存在不能更新数据！");
			}
		} catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}
	}

}
