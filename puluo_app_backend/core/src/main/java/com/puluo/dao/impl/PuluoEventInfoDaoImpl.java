package com.puluo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.puluo.dao.PuluoEventInfoDao;
import com.puluo.entity.PuluoEventInfo;
import com.puluo.entity.impl.PuluoEventInfoImpl;
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
				.append("event_name text, ")
				.append("description text, ")
				.append("coach_name text, ")
				.append("coach_uuid text, ")
				.append("thumbnail_uuid text, ")
				.append("details text, ")
				.append("duration int, ")
				.append("event_level int, ")
				.append("event_type int)")
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
	
	public boolean deleteByEventInfoUUID(String uuid){
		return super.deleteByUniqueKey("event_info_uuid", uuid);
	}

	@Override
	public PuluoEventInfo getEventInfoByUUID(String uuid) {
		SqlReader reader = getReader();
		StringBuilder selectSQL = new StringBuilder().append("select * from ")
				.append(super.getFullTableName()).append(" where event_info_uuid = ?");
		List<PuluoEventInfo> entities = reader.query(selectSQL.toString(), new Object[]{uuid},
				new RowMapper<PuluoEventInfo>() {
					@Override
					public PuluoEventInfo mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						PuluoEventInfoImpl info = new PuluoEventInfoImpl(
								rs.getString("event_info_uuid"),
								rs.getString("event_name"),
								rs.getString("description"),
								rs.getString("coach_name"),
								rs.getString("coach_uuid"),
								rs.getString("thumbnail_uuid"),
								rs.getString("details"),
								rs.getInt("duration"),
								rs.getInt("event_level"),
								rs.getInt("event_type"));
						return info;
					}
				});
		if (entities.size() == 1)
			return entities.get(0);
		else if (entities.size() > 1)
			throw new PuluoDatabaseException("通过event info uuid查到多个event info！");
		else
			return null;
	}

	@Override
	public boolean upsertEventInfo(PuluoEventInfo info) {
		try {
			SqlReader reader = getReader();
			String querySQL = new StringBuilder().append("select count(1) from ")
					.append(super.getFullTableName())
					.append(" where event_info_uuid = '" + info.eventInfoUUID() + "'")
					.toString();
			log.info(reader.queryForInt(querySQL));
			if (reader.queryForInt(querySQL)>0) {
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
			String querySQL = new StringBuilder().append("select count(1) from ")
					.append(super.getFullTableName())
					.append(" where event_info_uuid = '" + info.eventInfoUUID() + "'")
					.toString();
			log.info(reader.queryForInt(querySQL));
			String updateSQL;
			if (reader.queryForInt(querySQL)==0) {
				updateSQL = new StringBuilder().append("insert into ")
						.append(super.getFullTableName())
						.append(" (event_info_uuid, event_name, description, coach_name, coach_uuid, thumbnail_uuid, details, duration, event_level, event_type)")
						.append(" values ('" + info.eventInfoUUID() + "', ")
						.append(Strs.isEmpty(info.name()) ? "null" : "'" + info.name() + "'").append(", ")
						.append(Strs.isEmpty(info.description()) ? "null" : "'" + info.description() + "'").append(", ")
						.append(Strs.isEmpty(info.coachName()) ? "null" : "'" + info.coachName() + "'").append(", ")
						.append(Strs.isEmpty(info.coachUUID()) ? "null" : "'" + info.coachUUID() + "'").append(", ")
						.append(Strs.isEmpty(info.coachThumbnail()) ? "null" : "'" + info.coachThumbnail() + "'").append(", ")
						.append(Strs.isEmpty(info.details()) ? "null" : "'" + info.details() + "'").append(", ")
						.append(info.duration() + ", ")
						.append(info.level() + ", ")
						.append(info.type() + ")")
						.toString();
				log.info(updateSQL);
				getWriter().update(updateSQL);
				return true;
			} else {
				throw new PuluoDatabaseException("event_info_uuid为'" + info.eventInfoUUID() + "'已存在不能插入数据！");
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
			String querySQL = new StringBuilder().append("select count(1) from ")
					.append(super.getFullTableName())
					.append(" where event_info_uuid = '" + info.eventInfoUUID() + "'")
					.toString();
			log.info(reader.queryForInt(querySQL));
			StringBuilder updateSQL;
			if (reader.queryForInt(querySQL)>0) {
				updateSQL = new StringBuilder().append("update ")
						.append(super.getFullTableName()).append(" set");
				if (!Strs.isEmpty(info.name())) {
					updateSQL.append(" event_name = '" + info.name() + "',");
				}
				if (!Strs.isEmpty(info.description())) {
					updateSQL.append(" description = '" + info.description() + "',");
				}
				if (!Strs.isEmpty(info.coachName())) {
					updateSQL.append(" coach_name = '" + info.coachName() + "',");
				}
				if (!Strs.isEmpty(info.coachUUID())) {
					updateSQL.append(" coach_uuid = '" + info.coachUUID() + "',");
				}
				if (!Strs.isEmpty(info.coachThumbnail())) {
					updateSQL.append(" thumbnail_uuid = '" + info.coachThumbnail() + "',");
				}
				if (!Strs.isEmpty(info.details())) {
					updateSQL.append(" details = '" + info.details() + "',");
				}
				updateSQL.append(" duration = ").append(info.duration() + ",")
						.append(" event_level = ").append(info.level() + ",")
						.append(" event_type = ").append(info.type())
						.append(" where event_info_uuid = '" + info.eventInfoUUID() + "'");
				log.info(updateSQL.toString());
				getWriter().update(updateSQL.toString());
				return true;
			} else {
				throw new PuluoDatabaseException("event_info_uuid为'" + info.eventInfoUUID() + "'不存在不能更新数据！");
			}
		} catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}
	}

}
