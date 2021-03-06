package com.puluo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.puluo.dao.PuluoEventInfoDao;
import com.puluo.entity.PuluoEventInfo;
import com.puluo.entity.impl.PuluoEventInfoImpl;
import com.puluo.enumeration.PuluoEventCategory;
import com.puluo.enumeration.PuluoEventLevel;
import com.puluo.jdbc.DalTemplate;
import com.puluo.jdbc.SqlReader;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.PuluoDatabaseException;
import com.puluo.util.Strs;

public class PuluoEventInfoDaoImpl extends DalTemplate implements
		PuluoEventInfoDao {

	public static Log log = LogFactory.getLog(PuluoEventInfoDaoImpl.class);

	@Override
	public boolean createTable() {
		try {
			String createSQL = new StringBuilder().append("create table ")
					.append(super.getFullTableName())
					.append(" (id serial primary key, ")
					.append("event_info_uuid text unique, ")
					.append("event_name text, ").append("description text, ")
					.append("coach_name text, ").append("coach_uuid text, ")
					.append("thumbnail_uuid text, ").append("details text, ")
					.append("duration int, ").append("event_level text, ")
					.append("event_type text)").toString();
			log.info(createSQL);
			getWriter().execute(createSQL);
			
			String indexSQL1 = new StringBuilder().append("create index " + super.getFullTableName() + "_i_id on ")
					.append(super.getFullTableName())
					.append(" (id)").toString();
			log.info(indexSQL1);
			getWriter().execute(indexSQL1);
			
			String indexSQL2 = new StringBuilder().append("create index " + super.getFullTableName() + "_i_event_info_uuid on ")
					.append(super.getFullTableName())
					.append(" (event_info_uuid)").toString();
			log.info(indexSQL2);
			getWriter().execute(indexSQL2);
			
			return true;
		} catch (Exception e) {
			log.debug(e.getMessage());
			return false;
		}
	}
	
	public List<PuluoEventInfo> getAll() {
		SqlReader reader = getReader();
		String selectSQL = new StringBuilder().append("select * from ")
				.append(super.getFullTableName()).toString();
		return reader.query(selectSQL.toString(),new PuluoEventInfoRowMapper());
	}

	@Override
	public List<PuluoEventInfo> findEventInfo(String keyword) {
		if (!Strs.isEmpty(keyword)) {
			String query = String
					.format("position('%s' in event_name)>0 or position('%s' in description)>0 or position('%s' in coach_name)>0",
							keyword, keyword, keyword);
			SqlReader reader = getReader();
			String selectSQL = new StringBuilder().append("select * from ")
					.append(super.getFullTableName()).append(" where ")
					.append(query).toString();
			log.info(selectSQL);
			return reader.query(selectSQL.toString(),new PuluoEventInfoRowMapper());
		} else {
			return new ArrayList<PuluoEventInfo>();
		}

	}

	public boolean deleteByEventInfoUUID(String uuid) {
		return super.deleteByUniqueKey("event_info_uuid", uuid);
	}

	@Override
	public PuluoEventInfo getEventInfoByUUID(String uuid) {
		SqlReader reader = getReader();
		String selectSQL = new StringBuilder().append("select * from ")
				.append(super.getFullTableName())
				.append(" where event_info_uuid = ?").toString();
		log.info(super.sqlRequestLog(selectSQL, uuid));
		List<PuluoEventInfo> entities = reader.query(selectSQL.toString(),
				new Object[] { uuid }, new PuluoEventInfoRowMapper());
		return verifyUniqueResult(entities);
	}

	@Override
	public boolean upsertEventInfo(PuluoEventInfo info) {
		try {
			SqlReader reader = getReader();
			String querySQL = new StringBuilder()
					.append("select count(1) from ")
					.append(super.getFullTableName())
					.append(" where event_info_uuid = '" + info.eventInfoUUID()
							+ "'").toString();
			log.info(Strs.join("SQL:", querySQL));
			int resCnt = reader.queryForInt(querySQL);
			if (resCnt > 0) {
				return this.updateEventInfo(info);
			} else {
				return this.saveEventInfo(info);
			}
		} catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}
	}

	@Override
	public boolean saveEventInfo(PuluoEventInfo info) {
		try {
			SqlReader reader = getReader();
			String querySQL = new StringBuilder()
					.append("select count(1) from ")
					.append(super.getFullTableName())
					.append(" where event_info_uuid = '" + info.eventInfoUUID()
							+ "'").toString();
			log.info(Strs.join("SQL:", querySQL));
			int resCnt = reader.queryForInt(querySQL);
			String updateSQL;
			if (resCnt == 0) {
				PuluoEventCategory eventType;
				if(info.eventType()==null) eventType = PuluoEventCategory.Others; else eventType = info.eventType();
				PuluoEventLevel eventLevel;
				if(info.level()==null) eventLevel = PuluoEventLevel.Level1; else eventLevel = info.level();
				updateSQL = new StringBuilder()
						.append("insert into ")
						.append(super.getFullTableName())
						.append(" (event_info_uuid, event_name, description, coach_name, coach_uuid, thumbnail_uuid, details, duration, event_level, event_type)")
						.append(" values ('" + info.eventInfoUUID() + "', ")
						.append(Strs.isEmpty(info.name()) ? "null" : "'"
								+ super.processSingleQuote(info.name()) + "'")
						.append(", ")
						.append(Strs.isEmpty(info.description()) ? "null" : "'"
								+ super.processSingleQuote(info.description()) + "'")
						.append(", ")
						.append(Strs.isEmpty(info.coachName()) ? "null" : "'"
								+ super.processSingleQuote(info.coachName()) + "'")
						.append(", ")
						.append(Strs.isEmpty(info.coachUUID()) ? "null" : "'"
								+ info.coachUUID() + "'")
						.append(", ")
						.append(Strs.isEmpty(info.coachThumbnail()) ? "null"
								: "'" + info.coachThumbnail() + "'")
						.append(", ")
						.append(Strs.isEmpty(info.details()) ? "null" : "'"
								+ super.processSingleQuote(info.details()) + "'").append(", ")
						.append(info.duration() + ", ")
						.append("'"+eventLevel.name() + "', '").append(eventType.name() + "')")
						.toString();
				log.info(Strs.join("SQL:", updateSQL));
				getWriter().update(updateSQL);
				return true;
			} else {
				throw new PuluoDatabaseException("event_info_uuid为'"
						+ info.eventInfoUUID() + "'已存在不能插入数据！");
			}
		} catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}
	}

	@Override
	public boolean updateEventInfo(PuluoEventInfo info) {
		try {
			SqlReader reader = getReader();
			String querySQL = new StringBuilder()
					.append("select count(1) from ")
					.append(super.getFullTableName())
					.append(" where event_info_uuid = '" + info.eventInfoUUID()
							+ "'").toString();
			log.info(Strs.join("SQL:", querySQL));
			int resCnt = reader.queryForInt(querySQL);
			StringBuilder updateSQL;
			if (resCnt > 0) {
				PuluoEventCategory eventType;
				if(info.eventType()==null) eventType = PuluoEventCategory.Others; else eventType = info.eventType();
				PuluoEventLevel eventLevel;
				if(info.level()==null) eventLevel = PuluoEventLevel.Level1; else eventLevel = info.level();
				
				updateSQL = new StringBuilder().append("update ")
						.append(super.getFullTableName()).append(" set");
				if (!Strs.isEmpty(info.name())) {
					updateSQL.append(" event_name = '" + super.processSingleQuote(info.name()) + "',");
				}
				if (!Strs.isEmpty(info.description())) {
					updateSQL.append(" description = '" + super.processSingleQuote(info.description())
							+ "',");
				}
				if (!Strs.isEmpty(info.coachName())) {
					updateSQL.append(" coach_name = '" + super.processSingleQuote(info.coachName())
							+ "',");
				}
				if (!Strs.isEmpty(info.coachUUID())) {
					updateSQL.append(" coach_uuid = '" + info.coachUUID()
							+ "',");
				}
				if (!Strs.isEmpty(info.coachThumbnail())) {
					updateSQL.append(" thumbnail_uuid = '"
							+ info.coachThumbnail() + "',");
				}
				if (!Strs.isEmpty(info.details())) {
					updateSQL.append(" details = '" + super.processSingleQuote(info.details()) + "',");
				}
				updateSQL
						.append(" duration = ")
						.append(info.duration() + ",")
						.append(" event_level = '")
						.append(eventLevel.name() + "',")
						.append(" event_type = '")
						.append(eventType.name())
						.append("' where event_info_uuid = '"
								+ info.eventInfoUUID() + "'");
				log.info(Strs.join("SQL:", updateSQL.toString()));
				getWriter().update(updateSQL.toString());
				return true;
			} else {
				throw new PuluoDatabaseException("event_info_uuid为'"
						+ info.eventInfoUUID() + "'不存在不能更新数据！");
			}
		} catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}
	}

}

class PuluoEventInfoRowMapper implements RowMapper<PuluoEventInfo> {
	@Override
	public PuluoEventInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		PuluoEventInfoImpl info = new PuluoEventInfoImpl(
				rs.getString("event_info_uuid"), rs.getString("event_name"),
				rs.getString("description"), rs.getString("coach_name"),
				rs.getString("coach_uuid"), rs.getString("thumbnail_uuid"),
				rs.getString("details"), rs.getInt("duration"),
				PuluoEventLevel.valueOf(rs.getString("event_level")),
				PuluoEventCategory.valueOf(rs.getString("event_type")));
		return info;
	}
}
