package com.puluo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.puluo.dao.PuluoUserFriendshipDao;
import com.puluo.entity.PuluoUserFriendship;
import com.puluo.entity.impl.PuluoUserFriendshipImpl;
import com.puluo.jdbc.DalTemplate;
import com.puluo.jdbc.SqlReader;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;

public class PuluoUserFriendshipDaoImpl extends DalTemplate implements
		PuluoUserFriendshipDao {
	
	public static Log log = LogFactory.getLog(PuluoUserFriendshipDaoImpl.class);

	@Override
	public boolean createTable() {
		try {
			String createSQL = new StringBuilder().append("create table ")
				.append(super.getFullTableName())
				.append(" (id serial primary key, ")
				.append("user_uuid text unique, ")
				.append("friend_uuids text[])")
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
	public List<PuluoUserFriendship> getFriendListByUUID(String userUUID) {
		SqlReader reader = getReader();
		String selectSQL = new StringBuilder().append("select * from ")
				.append(super.getFullTableName()).append(" where user_uuid = ?")
				.toString();
		List<PuluoUserFriendship> entities = reader.query(selectSQL, new Object[] {userUUID},
				new RowMapper<PuluoUserFriendship>() {
					@Override
					public PuluoUserFriendship mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						String[] friend_uuids = rs.getArray("friend_uuids")!=null ? (String[])rs.getArray("friend_uuids").getArray() : new String[]{};
						PuluoUserFriendshipImpl puluoUserFriendship = new PuluoUserFriendshipImpl(
								rs.getString("user_uuid"),
								friend_uuids);
						return puluoUserFriendship;
					}
				});
		return (ArrayList<PuluoUserFriendship>) entities;
	}

	@Override
	public List<PuluoUserFriendship> deleteOneFriend(String userUUID,
			String frendUUID) {
		try {
			String updateSQL = new StringBuilder().append("update ")
					.append(super.getFullTableName())
					.append(" set friend_uuids = array_remove(friend_uuids, '" + frendUUID + "')")
					.append(" where user_uuid = '" + userUUID + "'")
					.toString();
			log.info(updateSQL);
			getWriter().update(updateSQL);
			updateSQL = new StringBuilder().append("update ")
					.append(super.getFullTableName())
					.append(" set friend_uuids = array_remove(friend_uuids, '" + userUUID + "')")
					.append(" where user_uuid = '" + frendUUID + "'")
					.toString();
			log.info(updateSQL);
			getWriter().update(updateSQL);
			return getFriendListByUUID(userUUID);
		} catch (Exception e) {
			log.info(e.getMessage());
			return null;
		}
	}

	@Override
	public List<PuluoUserFriendship> addOneFriend(String userUUID,
			String frendUUID) {
		try {
			SqlReader reader = getReader();
			String querySQL;
			String updateSQL;
			
			querySQL = new StringBuilder().append("select count(1) from ")
					.append(super.getFullTableName())
					.append(" where user_uuid = '" + userUUID + "'")
					.toString();
			log.info(reader.queryForInt(querySQL));
			if (reader.queryForInt(querySQL)>0) {
				updateSQL = new StringBuilder().append("update ")
						.append(super.getFullTableName())
						.append(" set friend_uuids = array_append(friend_uuids, '" + frendUUID + "')")
						.append(" where user_uuid = '" + userUUID + "'")
						.append(" and friend_uuids @> array['" + frendUUID + "'] <> true")
						.toString();
			} else {
				updateSQL = new StringBuilder().append("insert into ")
						.append(super.getFullTableName())
						.append(" (user_uuid, friend_uuids)")
						.append(" values ('" + userUUID + "', array['" + frendUUID + "'])")
						.toString();
			}
			log.info(updateSQL);
			getWriter().update(updateSQL);
			querySQL = new StringBuilder().append("select count(1) from ")
					.append(super.getFullTableName())
					.append(" where user_uuid = '" + frendUUID + "'")
					.toString();
			log.info(reader.queryForInt(querySQL));
			if (reader.queryForInt(querySQL)>0) {
				updateSQL = new StringBuilder().append("update ")
						.append(super.getFullTableName())
						.append(" set friend_uuids = array_append(friend_uuids, '" + userUUID + "')")
						.append(" where user_uuid = '" + frendUUID + "'")
						.append(" and friend_uuids @> array['" + userUUID + "'] <> true")
						.toString();
			} else {
				updateSQL = new StringBuilder().append("insert into ")
						.append(super.getFullTableName())
						.append(" (user_uuid, friend_uuids)")
						.append(" values ('" + frendUUID + "', array['" + userUUID + "'])")
						.toString();
			}
			log.info(updateSQL);
			getWriter().update(updateSQL);
		} catch (Exception e) {
			log.info(e.getMessage());
		}
		return getFriendListByUUID(userUUID);
	}
}
