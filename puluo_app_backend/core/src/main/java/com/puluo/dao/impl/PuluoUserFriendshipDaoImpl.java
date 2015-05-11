package com.puluo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.puluo.dao.PuluoUserFriendshipDao;
import com.puluo.entity.PuluoUserFriendship;
import com.puluo.entity.impl.PuluoUserFriendshipImpl;
import com.puluo.jdbc.DalTemplate;
import com.puluo.jdbc.SqlReader;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.PuluoDatabaseException;
import com.puluo.util.Strs;


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
			
			String indexSQL1 = new StringBuilder().append("create index " + super.getFullTableName() + "_i_id on ")
					.append(super.getFullTableName())
					.append(" (id)").toString();
			log.info(indexSQL1);
			getWriter().execute(indexSQL1);
			
			String indexSQL2 = new StringBuilder().append("create index " + super.getFullTableName() + "_i_user_uuid on ")
					.append(super.getFullTableName())
					.append(" (user_uuid)").toString();
			log.info(indexSQL2);
			getWriter().execute(indexSQL2);
			
			return true;
		} catch (Exception e) {
			log.debug(e.getMessage());
			return false;
		}
	}
	
	public boolean deleteByUserUUID(String uuid){
		return super.deleteByUniqueKey("user_uuid", uuid);
	}

	@Override
	public PuluoUserFriendship getFriendListByUUID(String userUUID,int limit, int offset) {
		SqlReader reader = getReader();
		StringBuilder sb = new StringBuilder().append("select * from ")
				.append(super.getFullTableName()).append(" where user_uuid = ?");
		if(limit>0) sb.append(" limit ").append(limit);
		if(offset>0) sb.append(" offset ").append(offset);
		sb.append(" order by id");
		String selectSQL = sb.toString();
		log.info(super.sqlRequestLog(selectSQL, userUUID));
		List<PuluoUserFriendship> entities = reader.query(
				selectSQL, new Object[] {userUUID},new FriendshipMapper());

		return verifyUniqueResult(entities);
	}

	@Override
	public PuluoUserFriendship deleteOneFriend(String userUUID,
			String frendUUID) {
		try {
			String updateSQL = new StringBuilder().append("update ")
					.append(super.getFullTableName())
					.append(" set friend_uuids = array_remove(friend_uuids, '" + frendUUID + "')")
					.append(" where user_uuid = '" + userUUID + "'")
					.toString();
			log.info(Strs.join("SQL:",updateSQL));
			getWriter().update(updateSQL);
			updateSQL = new StringBuilder().append("update ")
					.append(super.getFullTableName())
					.append(" set friend_uuids = array_remove(friend_uuids, '" + userUUID + "')")
					.append(" where user_uuid = '" + frendUUID + "'")
					.toString();
			log.info(Strs.join("SQL:",updateSQL));
			getWriter().update(updateSQL);
			return getFriendListByUUID(userUUID,0,0);
		} catch (Exception e) {
			log.info(e.getMessage());
			return null;
		}
	}

	@Override
	public PuluoUserFriendship addOneFriend(String userUUID,
			String frendUUID) {
		try {
			SqlReader reader = getReader();
			String querySQL;
			String updateSQL;
			
			querySQL = new StringBuilder().append("select count(1) from ")
					.append(super.getFullTableName())
					.append(" where user_uuid = '" + userUUID + "'")
					.toString();
			log.info(Strs.join("SQL:",querySQL));
			int resCnt = reader.queryForInt(querySQL);
			if (resCnt>0) {
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
			log.info(Strs.join("SQL:",updateSQL));
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
			log.info(Strs.join("SQL:",updateSQL));
			getWriter().update(updateSQL);
			return getFriendListByUUID(userUUID,0,0);
		} catch (Exception e) {
			log.info(e.getMessage());
			return null;
		}
	}

	@Override
	public boolean isFriend(String oneUserUUID, String theOtherUUID) {
		try {
			SqlReader reader = getReader();
			String querySQL1 = new StringBuilder().append("select count(1) from ")
					.append(super.getFullTableName())
					.append(" where user_uuid = '" + oneUserUUID + "'")
					.append(" and friend_uuids @> array['" + theOtherUUID + "'] = true")
					.toString();
			String querySQL2 = new StringBuilder().append("select count(1) from ")
					.append(super.getFullTableName())
					.append(" where user_uuid = '" + theOtherUUID + "'")
					.append(" and friend_uuids @> array['" + oneUserUUID + "'] = true")
					.toString();
			log.info(Strs.join("SQL:",querySQL1));
			log.info(Strs.join("SQL:",querySQL2));
			int friendsCnt1 = reader.queryForInt(querySQL1);
			int friendsCnt2 = reader.queryForInt(querySQL2);
			if (friendsCnt1==0 &&friendsCnt2==0) {
				return false;
			} else if (friendsCnt1==1 && friendsCnt2==1) {
				return true;
			} else {
				throw new PuluoDatabaseException("oneUserUUID与theOtherUUID的好友关系数据存在错误！");
			}
		} catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}
	}
}

class FriendshipMapper implements RowMapper<PuluoUserFriendship>{
	@Override
	public PuluoUserFriendship mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		String[] friend_uuids = rs.getArray("friend_uuids")!=null ? (String[])rs.getArray("friend_uuids").getArray() : new String[]{};
		PuluoUserFriendshipImpl puluoUserFriendship = new PuluoUserFriendshipImpl(
				rs.getString("user_uuid"),
				friend_uuids);
		return puluoUserFriendship;
	}
}
