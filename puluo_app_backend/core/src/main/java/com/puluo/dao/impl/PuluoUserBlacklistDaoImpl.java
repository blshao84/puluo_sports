package com.puluo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.puluo.dao.PuluoUserBlacklistDao;
import com.puluo.entity.PuluoUserBlacklist;
import com.puluo.entity.impl.PuluoUserBlacklistImpl;
import com.puluo.jdbc.DalTemplate;
import com.puluo.jdbc.SqlReader;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.PuluoDatabaseException;
import com.puluo.util.Strs;

public class PuluoUserBlacklistDaoImpl extends DalTemplate implements
		PuluoUserBlacklistDao {

	public static Log log = LogFactory.getLog(PuluoUserBlacklistDaoImpl.class);

	@Override
	public boolean createTable() {
		try {
			String createSQL = new StringBuilder().append("create table ")
					.append(super.getFullTableName())
					.append(" (id serial primary key, ")
					.append("user_uuid text unique, ")
					.append("banned_uuids text[])").toString();
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
							+ "_i_user_uuid on ")
					.append(super.getFullTableName()).append(" (user_uuid)")
					.toString();
			log.info(indexSQL2);
			getWriter().execute(indexSQL2);

			return true;
		} catch (Exception e) {
			log.debug(e.getMessage());
			return false;
		}
	}

	public boolean deleteByUserUUID(String uuid) {
		return super.deleteByUniqueKey("user_uuid", uuid);
	}

	@Override
	public PuluoUserBlacklist getBlacklistByUUID(String userUUID) {
		SqlReader reader = getReader();
		String selectSQL = new StringBuilder().append("select * from ")
				.append(super.getFullTableName())
				.append(" where user_uuid = ?").toString();
		log.info(super.sqlRequestLog(selectSQL, userUUID));
		List<PuluoUserBlacklist> entities = reader.query(selectSQL,
				new Object[] { userUUID }, new BlacklistMapper());

		return verifyUniqueResult(entities);
	}

	@Override
	public PuluoUserBlacklist freeUser(String userUUID, String bannedUUID) {
		try {
			String updateSQL = new StringBuilder()
					.append("update ")
					.append(super.getFullTableName())
					.append(" set banned_uuids = array_remove(banned_uuids, '"
							+ bannedUUID + "')")
					.append(" where user_uuid = '" + userUUID + "'").toString();
			log.info(Strs.join("SQL:", updateSQL));
			getWriter().update(updateSQL);
			return getBlacklistByUUID(userUUID);
		} catch (Exception e) {
			log.info(e.getMessage());
			return null;
		}
	}

	@Override
	public PuluoUserBlacklist banUser(String userUUID, String bannedUUID) {
		try {
			SqlReader reader = getReader();
			String querySQL;
			String updateSQL;

			querySQL = new StringBuilder().append("select count(1) from ")
					.append(super.getFullTableName())
					.append(" where user_uuid = '" + userUUID + "'").toString();
			log.info(Strs.join("SQL:", querySQL));
			int resCnt = reader.queryForInt(querySQL);
			if (resCnt > 0) {
				updateSQL = new StringBuilder()
						.append("update ")
						.append(super.getFullTableName())
						.append(" set banned_uuids = array_append(banned_uuids, '"
								+ bannedUUID + "')")
						.append(" where user_uuid = '" + userUUID + "'")
						.append(" and banned_uuids @> array['" + bannedUUID
								+ "'] <> true").toString();
			} else {
				updateSQL = new StringBuilder()
						.append("insert into ")
						.append(super.getFullTableName())
						.append(" (user_uuid, banned_uuids)")
						.append(" values ('" + userUUID + "', array['"
								+ bannedUUID + "'])").toString();
			}
			log.info(Strs.join("SQL:", updateSQL));
			getWriter().update(updateSQL);
			return getBlacklistByUUID(userUUID);
		} catch (Exception e) {
			log.info(e.getMessage());
			return null;
		}
	}

	@Override
	public boolean isBanned(String oneUserUUID, String theOtherUUID) {
		try {
			SqlReader reader = getReader();
			String querySQL1 = new StringBuilder()
					.append("select count(1) from ")
					.append(super.getFullTableName())
					.append(" where user_uuid = '" + oneUserUUID + "'")
					.append(" and banned_uuids @> array['" + theOtherUUID
							+ "'] = true").toString();
			log.info(Strs.join("SQL:", querySQL1));
			int friendsCnt1 = reader.queryForInt(querySQL1);
			if (friendsCnt1 == 0) {
				return false;
			} else if (friendsCnt1 == 1) {
				return true;
			} else {
				throw new PuluoDatabaseException(
						"oneUserUUID与theOtherUUID的好友关系数据存在错误！");
			}
		} catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}
	}

}

class BlacklistMapper implements RowMapper<PuluoUserBlacklist> {
	@Override
	public PuluoUserBlacklist mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		String[] banned_uuids = rs.getArray("banned_uuids") != null ? (String[]) rs
				.getArray("banned_uuids").getArray() : new String[] {};
		PuluoUserBlacklist blacklist = new PuluoUserBlacklistImpl(
				rs.getString("user_uuid"), banned_uuids);
		return blacklist;
	}
}
