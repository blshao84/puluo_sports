package com.puluo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.puluo.dao.PuluoFriendRequestDao;
import com.puluo.entity.PuluoFriendRequest;
import com.puluo.entity.impl.PuluoFriendRequestImpl;
import com.puluo.enumeration.FriendRequestStatus;
import com.puluo.jdbc.DalTemplate;
import com.puluo.jdbc.SqlReader;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.PuluoDatabaseException;
import com.puluo.util.Strs;
import com.puluo.util.TimeUtils;

public class PuluoFriendRequestDaoImpl extends DalTemplate implements
		PuluoFriendRequestDao {

	public static Log log = LogFactory.getLog(PuluoFriendRequestDaoImpl.class);

	@Override
	public boolean createTable() {
		try {
			String createSQL = new StringBuilder().append("create table ")
					.append(super.getFullTableName())
					.append(" (id serial primary key, ")
					.append("request_uuid text unique, ")
					.append("request_status text, ")
					.append("from_user_uuid text not null, ")
					.append("to_user_uuid text not null, ")
					.append("created_at timestamp, ")
					.append("updated_at timestamp)").toString();
			log.info(createSQL);
			getWriter().execute(createSQL);
			
			String indexSQL1 = new StringBuilder().append("create index " + super.getFullTableName() + "_i_id on ")
					.append(super.getFullTableName())
					.append(" (id)").toString();
			log.info(indexSQL1);
			getWriter().execute(indexSQL1);
			
			String indexSQL2 = new StringBuilder().append("create index " + super.getFullTableName() + "_i_request_uuid on ")
					.append(super.getFullTableName())
					.append(" (request_uuid)").toString();
			log.info(indexSQL2);
			getWriter().execute(indexSQL2);
			
			String indexSQL3 = new StringBuilder().append("create index " + super.getFullTableName() + "_i_from_user_uuid on ")
					.append(super.getFullTableName())
					.append(" (from_user_uuid)").toString();
			log.info(indexSQL3);
			getWriter().execute(indexSQL3);
			
			String indexSQL4 = new StringBuilder().append("create index " + super.getFullTableName() + "_i_to_user_uuid on ")
					.append(super.getFullTableName())
					.append(" (to_user_uuid)").toString();
			log.info(indexSQL4);
			getWriter().execute(indexSQL4);
			
			String indexSQL5 = new StringBuilder().append("create index " + super.getFullTableName() + "_i_from_n_to on ")
					.append(super.getFullTableName())
					.append(" (from_user_uuid, to_user_uuid)").toString();
			log.info(indexSQL5);
			getWriter().execute(indexSQL5);
			
			return true;
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
	}

	public boolean deleteByReqUUID(String uuid) {
		return super.deleteByUniqueKey("request_uuid", uuid);
	}

	public boolean deleteByUserUUID(String uuid) {
		return super.deleteByUniqueKey("from_user_uuid", uuid);
	}

	@Override
	public boolean saveNewRequest(String requestUUID, String fromUser,
			String toUser) {
		try {
			SqlReader reader = getReader();
			String querySQL = new StringBuilder()
					.append("select count(1) from ")
					.append(super.getFullTableName())
					.append(" where request_uuid = '" + requestUUID + "'")
					.toString();
			log.info(Strs.join("SQL:",querySQL));
			int resCnt = reader.queryForInt(querySQL);
			String updateSQL;
			if (resCnt == 0) {
				updateSQL = new StringBuilder()
						.append("insert into ")
						.append(super.getFullTableName())
						.append(" (request_uuid, request_status, from_user_uuid, to_user_uuid, created_at)")
						.append("values ('" + requestUUID + "', ")
						.append("'" + FriendRequestStatus.Requested.name()
								+ "'")
						.append(", ")
						.append(Strs.isEmpty(fromUser) ? "null" : "'"
								+ fromUser + "'")
						.append(", ")
						.append(Strs.isEmpty(toUser) ? "null" : "'" + toUser
								+ "'").append(", ").append("now()::timestamp)")
						.toString();
				log.info(Strs.join("SQL:",updateSQL));
				getWriter().update(updateSQL);
				return true;
			} else {
				throw new PuluoDatabaseException("request_uuid为'" + requestUUID
						+ "'已存在不能插入数据！");
			}
		} catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}
	}

	@Override
	public boolean updateRequestStatus(String requestUUID,
			FriendRequestStatus newStatus) {
		try {
			SqlReader reader = getReader();
			String querySQL = new StringBuilder()
					.append("select count(1) from ")
					.append(super.getFullTableName())
					.append(" where request_uuid = '" + requestUUID + "'")
					.toString();
			log.info(Strs.join("SQL:",querySQL));
			int resCnt = reader.queryForInt(querySQL);
			String updateSQL;
			if (resCnt > 0) {
				updateSQL = new StringBuilder().append("update ")
						.append(super.getFullTableName())
						.append(" set request_status = '" + newStatus + "',")
						.append(" updated_at = now()::timestamp")
						.append(" where request_uuid = '" + requestUUID + "'")
						.toString();
				log.info(Strs.join("SQL:",updateSQL));
				getWriter().update(updateSQL);
				return true;
			} else {
				throw new PuluoDatabaseException("request_uuid为'" + requestUUID
						+ "'不存在不能修改数据！");
			}
		} catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}
	}

	@Override
	public PuluoFriendRequest findByUUID(String requestUUID) {
		try {
			SqlReader reader = getReader();
			String selectSQL = new StringBuilder()
					.append("select * from ").append(super.getFullTableName())
					.append(" where request_uuid = ?").toString();
			log.info(super.sqlRequestLog(selectSQL,requestUUID));
			List<PuluoFriendRequest> entities = reader.query(
					selectSQL, new Object[] { requestUUID },new FriendRequestMapper());
			return verifyUniqueResult(entities);
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}
	
	@Override
	public List<PuluoFriendRequest> getFriendRequestByUsers(String userUUID,
			String friendUUID,FriendRequestStatus status) {
		try {
			SqlReader reader = getReader();
			String selectSQL = new StringBuilder()
					.append("select * from ").append(super.getFullTableName())
					.append(" where from_user_uuid = ? and to_user_uuid = ? and request_status = '" + status.name() + "'").toString();
			log.info(super.sqlRequestLog(selectSQL,userUUID,friendUUID));
			List<PuluoFriendRequest> entities = reader.query(
					selectSQL, new Object[] {userUUID, friendUUID},new FriendRequestMapper());
			return entities;
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	@Override
	public List<PuluoFriendRequest> getFriendRequestByUsers(String userUUID,
			String friendUUID) {
		try {
			SqlReader reader = getReader();
			String selectSQL = new StringBuilder()
					.append("select * from ").append(super.getFullTableName())
					.append(" where from_user_uuid = ? and to_user_uuid = ? ").toString();
			log.info(super.sqlRequestLog(selectSQL,userUUID,friendUUID));
			List<PuluoFriendRequest> entities = reader.query(
					selectSQL.toString(), new Object[] {userUUID, friendUUID},new FriendRequestMapper());
			return entities;
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}
	
	class FriendRequestMapper implements RowMapper<PuluoFriendRequest> {
		@Override
		public PuluoFriendRequest mapRow(ResultSet rs,
				int rowNum) throws SQLException {
			PuluoFriendRequestImpl message = new PuluoFriendRequestImpl(
					rs.getString("request_uuid"),
					FriendRequestStatus.valueOf(rs.getString("request_status")),
					rs.getString("from_user_uuid"),
					rs.getString("to_user_uuid"),
					TimeUtils.parseDateTime(TimeUtils.formatDate(rs.getTimestamp("created_at"))),
					TimeUtils.parseDateTime(TimeUtils.formatDate(rs.getTimestamp("updated_at"))));
			return message;
		}
	}

	@Override
	public List<PuluoFriendRequest> getPendingFriendRequestsByUserUUID(
			String userUUID) {
		try {
			SqlReader reader = getReader();
			String selectSQL = new StringBuilder()
					.append("select * from ").append(super.getFullTableName())
					.append(" where to_user_uuid = ? and request_status = '" + FriendRequestStatus.Requested.name() + "' order by created_at desc").toString();
			log.info(super.sqlRequestLog(selectSQL,userUUID));
			List<PuluoFriendRequest> entities = reader.query(
					selectSQL.toString(), new Object[] {userUUID},new FriendRequestMapper());
			return entities;
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}
}
