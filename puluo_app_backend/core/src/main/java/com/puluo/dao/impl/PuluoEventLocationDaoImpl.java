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
import com.puluo.util.Strs;

public class PuluoEventLocationDaoImpl extends DalTemplate implements
		PuluoEventLocationDao {

	public static Log log = LogFactory.getLog(PuluoEventLocationDaoImpl.class);

	@Override
	public boolean createTable() {
		try {
			String createSQL = new StringBuilder().append("create table ")
					.append(super.getFullTableName())
					.append(" (id serial primary key, ")
					.append("location_uuid text unique, ")
					.append("address text, ").append("zip text, ")
					.append("name text, ").append("phone text, ")
					.append("city text, ")
					.append("longitude double precision, ")
					.append("latitude double precision, ")
					.append("court int, ").append("capacity int, ")
					.append("location_type int)").toString();
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
							+ "_i_location_uuid on ")
					.append(super.getFullTableName())
					.append(" (location_uuid)").toString();
			log.info(indexSQL2);
			getWriter().execute(indexSQL2);

			return true;
		} catch (Exception e) {
			log.debug(e.getMessage());
			return false;
		}
	}

	public boolean deleteByLocationUUID(String uuid) {
		return super.deleteByUniqueKey("location_uuid", uuid);
	}

	@Override
	public boolean upsertEventLocation(PuluoEventLocation location) {
		try {
			SqlReader reader = getReader();
			String querySQL = new StringBuilder()
					.append("select count(1) from ")
					.append(super.getFullTableName())
					.append(" where location_uuid = '" + location.locationId()
							+ "'").toString();
			log.info(Strs.join("SQL:", querySQL));
			int resCnt = reader.queryForInt(querySQL);
			if (resCnt > 0) {
				return this.updateEventLocation(location);
			} else {
				return this.saveEventLocation(location);
			}
		} catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}
	}

	@Override
	public PuluoEventLocation getEventLocationByUUID(String location_uuid) {
		SqlReader reader = getReader();
		String selectSQL = new StringBuilder().append("select * from ")
				.append(super.getFullTableName())
				.append(" where location_uuid = ?").toString();
		log.info(super.sqlRequestLog(selectSQL, location_uuid));
		List<PuluoEventLocation> entities = reader.query(selectSQL,
				new Object[] { location_uuid },new EventLocationMapper());
		return verifyUniqueResult(entities);
	}
	
	@Override
	public PuluoEventLocation getEventLocationByName(String location_name) {
		SqlReader reader = getReader();
		String selectSQL = new StringBuilder().append("select * from ")
				.append(super.getFullTableName())
				.append(" where name = ?").toString();
		log.info(super.sqlRequestLog(selectSQL, location_name));
		List<PuluoEventLocation> entities = reader.query(selectSQL,
				new Object[] { location_name },new EventLocationMapper());
		return verifyUniqueResult(entities);
	}

	@Override
	public boolean saveEventLocation(PuluoEventLocation location) {
		try {
			SqlReader reader = getReader();
			String querySQL = new StringBuilder()
					.append("select count(1) from ")
					.append(super.getFullTableName())
					.append(" where location_uuid = '" + location.locationId()
							+ "'").toString();
			log.info(Strs.join("SQL:", querySQL));
			int resCnt = reader.queryForInt(querySQL);
			String updateSQL;
			if (resCnt == 0) {
				updateSQL = new StringBuilder()
						.append("insert into ")
						.append(super.getFullTableName())
						.append(" (location_uuid, address, zip, name, phone, city, longitude, latitude, court, capacity, location_type)")
						.append(" values ('" + location.locationId() + "', ")
						.append(Strs.isEmpty(location.address()) ? "null" : "'"
								+ super.processSingleQuote(location.address())
								+ "'")
						.append(", ")
						.append(Strs.isEmpty(location.zip()) ? "null" : "'"
								+ location.zip() + "'")
						.append(", ")
						.append(Strs.isEmpty(location.name()) ? "null" : "'"
								+ super.processSingleQuote(location.name())
								+ "'")
						.append(", ")
						.append(Strs.isEmpty(location.phone()) ? "null" : "'"
								+ location.phone() + "'")
						.append(", ")
						.append(Strs.isEmpty(location.city()) ? "null" : "'"
								+ super.processSingleQuote(location.city())
								+ "'").append(", ")
						.append(location.longitude() + ", ")
						.append(location.latitude() + ", ")
						.append(location.court() + ", ")
						.append(location.capacity() + ", ")
						.append(location.type() + ")").toString();
				log.info(Strs.join("SQL:", updateSQL));
				getWriter().update(updateSQL);
				return true;
			} else {
				throw new PuluoDatabaseException("location_uuid为'"
						+ location.locationId() + "'已存在不能插入数据！");
			}
		} catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}
	}

	@Override
	public boolean updateEventLocation(PuluoEventLocation location) {
		try {
			SqlReader reader = getReader();
			String querySQL = new StringBuilder()
					.append("select count(1) from ")
					.append(super.getFullTableName())
					.append(" where location_uuid = '" + location.locationId()
							+ "'").toString();
			log.info(Strs.join("SQL:", querySQL));
			int resCnt = reader.queryForInt(querySQL);
			StringBuilder updateSQL;
			if (resCnt > 0) {
				updateSQL = new StringBuilder().append("update ")
						.append(super.getFullTableName()).append(" set");
				if (!Strs.isEmpty(location.address())) {
					updateSQL.append(" address = '"
							+ super.processSingleQuote(location.address())
							+ "',");
				}
				if (!Strs.isEmpty(location.zip())) {
					updateSQL.append(" zip = '" + location.zip() + "',");
				}
				if (!Strs.isEmpty(location.name())) {
					updateSQL.append(" name = '"
							+ super.processSingleQuote(location.name()) + "',");
				}
				if (!Strs.isEmpty(location.phone())) {
					updateSQL.append(" phone = '" + location.phone() + "',");
				}
				if (!Strs.isEmpty(location.city())) {
					updateSQL.append(" city = '"
							+ super.processSingleQuote(location.city()) + "',");
				}
				updateSQL
						.append(" longitude = ")
						.append(location.longitude() + ",")
						.append(" latitude = ")
						.append(location.latitude() + ",")
						.append(" court = ")
						.append(location.court() + ",")
						.append(" capacity = ")
						.append(location.capacity() + ",")
						.append(" location_type = ")
						.append(location.type())
						.append(" where location_uuid = '"
								+ location.locationId() + "'");
				log.info(Strs.join("SQL:", updateSQL.toString()));
				getWriter().update(updateSQL.toString());
				return true;
			} else {
				throw new PuluoDatabaseException("location_uuid为'"
						+ location.locationId() + "'不存在不能更新数据！");
			}
		} catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}
	}

	@Override
	public List<PuluoEventLocation> findAll() {
		SqlReader reader = getReader();
		String selectSQL = new StringBuilder().append("select * from ")
				.append(super.getFullTableName()).toString();
		List<PuluoEventLocation> entities = reader.query(selectSQL,new EventLocationMapper());
		return entities;
	}

	class EventLocationMapper implements RowMapper<PuluoEventLocation> {
		@Override
		public PuluoEventLocation mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			PuluoEventLocationImpl location = new PuluoEventLocationImpl(
					rs.getString("location_uuid"), rs.getString("address"),
					rs.getString("zip"), rs.getString("name"),
					rs.getString("phone"), rs.getString("city"),
					rs.getDouble("longitude"), rs.getDouble("latitude"),
					rs.getInt("court"), rs.getInt("capacity"),
					rs.getInt("location_type"));
			return location;
		}
	}

}
