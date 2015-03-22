package com.puluo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.puluo.dao.PuluoEventDao;
import com.puluo.entity.PuluoEvent;
import com.puluo.entity.impl.PuluoEventImpl;
import com.puluo.jdbc.DalTemplate;
import com.puluo.jdbc.SqlReader;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.PuluoDatabaseException;
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
				.append("price double, ")
				.append("discounted_price double, ")
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
						PuluoEventImpl event = new PuluoEventImpl(
								rs.getString("event_uuid"),
								TimeUtils.parseDateTime(TimeUtils.formatDate(rs.getTimestamp("event_time"))),
								rs.getString("status"),
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
	public List<PuluoEvent> findEvents(Date event_date, String keyword, String level, 
			String sort, String sort_direction, double latitude, double longitude, double range_from) {
		ArrayList<String> params = new ArrayList<String>();
		boolean hasAnd = false;
		if (event_date!=null) {
			if (!hasAnd) {
				params.add(" e.event_time = '" + TimeUtils.formatDate(event_date) + "'");
				hasAnd = true;
			} else {
				params.add(" and e.event_time = '" + TimeUtils.formatDate(event_date) + "'");
			}
		}
		if (keyword!=null) {
			if (!hasAnd) {
				params.add(" (position('" + keyword + "' in i.event_name)>0 or position('" + keyword + "' in i.description)>0)");
				hasAnd = true;
			} else {
				params.add(" and (position('" + keyword + "' in i.event_name)>0 or position('" + keyword + "' in i.description)>0)");
			}
		}
		if (range_from!=0.0) {
			if (!hasAnd) {
				params.add(" st_distance('point(l.latitude l.longitude)', 'point(" + latitude + " " + longitude + ")') <= " + range_from);
				hasAnd = true;
			} else {
				params.add(" and st_distance('point(l.latitude l.longitude)', 'point(" + latitude + " " + longitude + ")') <= " + range_from);
			}
		}
		StringBuilder orderBy = new StringBuilder("");
		if ("time".equals(sort)) {
			orderBy.append(" order by e.event_time " + sort_direction);
		} else if ("distance".equals(sort)) {
			orderBy.append(" order by st_distance('point(l.latitude l.longitude)', 'point(" + latitude + " " + longitude + ")') " + sort_direction);
		} else if ("price".equals(sort)) {
			orderBy.append(" order by i.discountedPrice " + sort_direction);
		}
		SqlReader reader = getReader();
		StringBuilder selectSQL = new StringBuilder().append("select e.* from ")
				.append(super.getFullTableName() + "e, " + new PuluoEventInfoDaoImpl().getFullTableName() + "i, " + new PuluoEventLocationDaoImpl().getFullTableName() + "l")
				.append(" where e.info_uuid = i.event_info_uuid and e.location_uuid = l.location_uuld");
		for (String tmp: params) {
			selectSQL.append(tmp);
		}
		selectSQL.append(orderBy.toString());
		List<PuluoEvent> entities = reader.query(selectSQL.toString(), new Object[]{},
				new RowMapper<PuluoEvent>() {
					@Override
					public PuluoEvent mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						PuluoEventImpl event = new PuluoEventImpl(
								rs.getString("event_uuid"),
								TimeUtils.parseDateTime(TimeUtils.formatDate(rs.getTimestamp("event_time"))),
								rs.getString("status"),
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
			String updateSQL;
			if (reader.queryForInt(querySQL)>0) {
				updateSQL = new StringBuilder().append("update ")
						.append(super.getFullTableName())
						.append(" set event_time = '" + TimeUtils.formatDate(event.eventTime()) + "',")
						.append(" status = '" + event.status() + "',")
						.append(" registered_users = " + event.registeredUsers() + ",")
						.append(" capatcity = " + event.capatcity() + ",")
						.append(" price = " + event.price() + ",")
						.append(" discounted_price = " + event.discountedPrice() + ",")
						.append(" info_uuid = '" + event.eventInfo().eventInfoUUID() + "',")
						.append(" location_uuid = '" + event.eventLocation().locationId() + "'")
						.append(" where event_uuid = '" + event.eventUUID() + "'")
						.toString();
			} else {
				updateSQL = new StringBuilder().append("insert into ")
						.append(super.getFullTableName())
						.append(" (event_uuid, event_time, status, registered_users, capatcity, price, discounted_price, info_uuid, location_uuid)")
						.append("'" + event.eventUUID() + "', ")
						.append("'" + TimeUtils.formatDate(event.eventTime()) + "',")
						.append("'" + event.status() + "',")
						.append(event.registeredUsers() + ",")
						.append(event.capatcity() + ",")
						.append(event.price() + ",")
						.append(event.discountedPrice() + ",")
						.append("'" + event.eventInfo().eventInfoUUID() + "',")
						.append("'" + event.eventLocation().locationId() + "'")
						.toString();
			}
			log.info(updateSQL);
			getWriter().update(updateSQL);
			return true;
		} catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}
	}

}
