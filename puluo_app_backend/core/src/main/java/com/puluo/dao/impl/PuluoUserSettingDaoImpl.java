package com.puluo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.puluo.dao.PuluoUserSettingDao;
import com.puluo.entity.PuluoUserSetting;
import com.puluo.entity.impl.PuluoUserSettingImpl;
import com.puluo.jdbc.DalTemplate;
import com.puluo.jdbc.SqlReader;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.Strs;
import com.puluo.util.TimeUtils;

public class PuluoUserSettingDaoImpl extends DalTemplate implements PuluoUserSettingDao {
	
	public static Log log = LogFactory.getLog(PuluoUserSettingDaoImpl.class);

	@Override
	public boolean createTable() {
		try {
			String createSQL = new StringBuilder().append("create table ")
				.append(super.getFullTableName())
				.append(" (id serial primary key, ")
				.append("user_uuid text unique, ")
				.append("auto_friend boolean, ")
				.append("timeline_public boolean, ")
				.append("searchable boolean, ")
				.append("updated_at timestamp)")
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
	
	public boolean deleteByUserUUID(String uuid){
		return super.deleteByUniqueKey("user_uuid", uuid);
	}
	@Override
	public boolean saveNewSetting(String user_uuid) {
		try {
			String updateSQL = new StringBuilder().append("insert into ")
						.append(super.getFullTableName())
						.append(" (user_uuid, auto_friend, timeline_public, searchable, updated_at)")
						.append(" values ('" + user_uuid + "', true, true, true, now()::timestamp)")
						.toString();
			log.info(Strs.join("SQL:",updateSQL));
			getWriter().update(updateSQL);
			return true;
		} catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}
	}

	@Override
	public boolean updateAutoFriend(String user_uuid, boolean allowAutoFriend) {
		try {
			String updateSQL = new StringBuilder().append("update ")
						.append(super.getFullTableName())
						.append(" set auto_friend = " + allowAutoFriend + ",")
						.append(" updated_at = now()::timestamp")
						.append(" where user_uuid = '" + user_uuid + "'")
						.toString();
			log.info(Strs.join("SQL:",updateSQL));
			getWriter().update(updateSQL);
			return true;
		} catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}
	}

	@Override
	public boolean updateTimelineVisibility(String user_uuid,
			boolean timelineVisible) {
		try {
			String updateSQL = new StringBuilder().append("update ")
						.append(super.getFullTableName())
						.append(" set timeline_public = " + timelineVisible + ",")
						.append(" updated_at = now()::timestamp")
						.append(" where user_uuid = '" + user_uuid + "'")
						.toString();
			log.info(Strs.join("SQL:",updateSQL));
			getWriter().update(updateSQL);
			return true;
		} catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}
	}

	@Override
	public boolean updateSearchability(String user_uuid, boolean searchable) {
		try {
			String updateSQL = new StringBuilder().append("update ")
						.append(super.getFullTableName())
						.append(" set searchable = " + searchable + ",")
						.append(" updated_at = now()::timestamp")
						.append(" where user_uuid = '" + user_uuid + "'")
						.toString();
			log.info(Strs.join("SQL:",updateSQL));
			getWriter().update(updateSQL);
			return true;
		} catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}
	}

	@Override
	public PuluoUserSetting getByUserUUID(String user_uuid) {
		SqlReader reader = getReader();
		String selectSQL = new StringBuilder().append("select * from ")
				.append(super.getFullTableName()).append(" where user_uuid = ?")
				.toString();
		log.info(super.sqlRequestLog(selectSQL, user_uuid));
		List<PuluoUserSetting> entities = reader.query(selectSQL, new Object[] {user_uuid},
				new RowMapper<PuluoUserSetting>() {
					@Override
					public PuluoUserSetting mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						PuluoUserSettingImpl puluoUserSetting = new PuluoUserSettingImpl(
								rs.getString("user_uuid"), rs.getBoolean("auto_friend"), rs.getBoolean("timeline_public"), rs.getBoolean("searchable"),
								TimeUtils.parseDateTime(TimeUtils.formatDate(rs.getTimestamp("updated_at"))));
						return puluoUserSetting;
					}
				});
		return verifyUniqueResult(entities);
	}

}
