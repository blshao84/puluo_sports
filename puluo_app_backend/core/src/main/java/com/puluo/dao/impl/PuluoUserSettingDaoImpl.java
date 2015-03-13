package com.puluo.dao.impl;

import com.puluo.dao.PuluoUserSettingDao;
import com.puluo.jdbc.DalTemplate;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;

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

	@Override
	public boolean saveNewSetting(String user_uuid) {
		try {
			String updateSQL = new StringBuilder().append("insert into ")
						.append(super.getFullTableName())
						.append(" (user_uuid, auto_friend, timeline_public, searchable, updated_at)")
						.append(" values ('" + user_uuid + "', true, true, true, now()::timestamp)")
						.toString();
			log.info(updateSQL);
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
			log.info(updateSQL);
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
			log.info(updateSQL);
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
			log.info(updateSQL);
			getWriter().update(updateSQL);
			return true;
		} catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}
	}

}
