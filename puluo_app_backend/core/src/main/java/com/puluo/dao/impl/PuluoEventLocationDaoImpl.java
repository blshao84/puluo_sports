package com.puluo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.puluo.dao.PuluoEventLocationDao;
import com.puluo.entity.PuluoEventLocation;
import com.puluo.entity.impl.PuluoEventLocationImpl;
import com.puluo.jdbc.DalTemplate;
import com.puluo.jdbc.SqlReader;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.PuluoDatabaseException;

public class PuluoEventLocationDaoImpl extends DalTemplate implements PuluoEventLocationDao{
	
	public static Log log = LogFactory.getLog(PuluoEventLocationDaoImpl.class);

	@Override
	public boolean createTable() {
		try {
			String createSQL = new StringBuilder().append("create table ")
				.append(super.getFullTableName())
				.append(" (id serial primary key, ")
				.append("location_uuid text unique, ")
				.append("address text, ")
				.append("zip text, ")
				.append("name text, ")
				.append("phone text, ")
				.append("city text, ")
				.append("longitude double precision, ")
				.append("latitude double precision, ")
				.append("court int, ")
				.append("capacity int, ")
				.append("location_type int)")
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
	public boolean upsertEventLocation(PuluoEventLocation location) {
		try {
			SqlReader reader = getReader();
			String querySQL = new StringBuilder().append("select count(1) from ")
					.append(super.getFullTableName())
					.append(" where location_uuid = '" + location.locationId() + "'")
					.toString();
			log.info(reader.queryForInt(querySQL));
			String updateSQL;
			if (reader.queryForInt(querySQL)>0) {
				updateSQL = new StringBuilder().append("update ")
						.append(super.getFullTableName())
						.append(" set address = ").append(location.address()!=null ? "'" + location.address() + "'" : "null").append(",")
						.append(" zip = ").append(location.zip()!=null ? "'" + location.zip() + "'" : "null").append(",")
						.append(" name = ").append(location.name()!=null ? "'" + location.name() + "'" : "null").append(",")
						.append(" phone = ").append(location.phone()!=null ? "'" + location.phone() + "'" : "null").append(",")
						.append(" city = ").append(location.city()!=null ? "'" + location.city() + "'" : "null").append(",")
						.append(" longitude = ").append(location.longitude() + ",")
						.append(" latitude = ").append(location.latitude() + ",")
						.append(" court = ").append(location.court() + ",")
						.append(" capacity = ").append(location.capacity() + ",")
						.append(" location_type = ").append(location.type())
						.append(" where location_uuid = '" + location.locationId() + "'")
						.toString();
			} else {
				updateSQL = new StringBuilder().append("insert into ")
						.append(super.getFullTableName())
						.append(" (location_uuid, address, zip, name, phone, city, longitude, latitude, court, capacity, location_type)")
						.append(" values ('" + location.locationId() + "', ")
						.append(location.address()!=null ? "'" + location.address() + "'" : "null").append(", ")
						.append(location.zip()!=null ? "'" + location.zip() + "'" : "null").append(", ")
						.append(location.name()!=null ? "'" + location.name() + "'" : "null").append(", ")
						.append(location.phone()!=null ? "'" + location.phone() + "'" : "null").append(", ")
						.append(location.city()!=null ? "'" + location.city() + "'" : "null").append(", ")
						.append(location.longitude() + ", ")
						.append(location.latitude() + ", ")
						.append(location.court() + ", ")
						.append(location.capacity() + ", ")
						.append(location.type() + ")")
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

	@Override
	public PuluoEventLocation getEventLocationByUUID(String location_uuid) {
		SqlReader reader = getReader();
		StringBuilder selectSQL = new StringBuilder().append("select * from ")
				.append(super.getFullTableName()).append(" where location_uuid = ?");
		List<PuluoEventLocation> entities = reader.query(selectSQL.toString(), new Object[]{location_uuid},
				new RowMapper<PuluoEventLocation>() {
					@Override
					public PuluoEventLocation mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						PuluoEventLocationImpl location = new PuluoEventLocationImpl(
								rs.getString("location_uuid"),
								rs.getString("address"),
								rs.getString("zip"),
								rs.getString("name"),
								rs.getString("phone"),
								rs.getString("city"),
								rs.getDouble("longitude"),
								rs.getDouble("latitude"),
								rs.getInt("court"),
								rs.getInt("capacity"),
								rs.getInt("location_type"));
						return location;
					}
				});
		if (entities.size() == 1)
			return entities.get(0);
		else if (entities.size() > 1)
			throw new PuluoDatabaseException("通过location uuid查到多个location！");
		else
			return null;
	}

}
