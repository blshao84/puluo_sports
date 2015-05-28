package com.puluo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.jdbc.core.RowMapper;

import com.puluo.dao.PuluoPrivateMessageDao;
import com.puluo.entity.PuluoPrivateMessage;
import com.puluo.entity.impl.PuluoPrivateMessageImpl;
import com.puluo.enumeration.PuluoMessageType;
import com.puluo.enumeration.SortDirection;
import com.puluo.jdbc.DalTemplate;
import com.puluo.jdbc.SqlReader;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.Strs;
import com.puluo.util.TimeUtils;

public class PuluoPrivateMessageDaoImpl extends DalTemplate implements
		PuluoPrivateMessageDao {

	public static Log log = LogFactory.getLog(PuluoPrivateMessageDaoImpl.class);

	@Override
	public boolean saveMessage(PuluoPrivateMessage message) {
		try {
			String updateSQL = new StringBuilder()
					.append("insert into ")
					.append(super.getFullTableName())
					.append(" (message_uuid, content, message_type, friend_request_uuid, from_user_uuid, to_user_uuid, created_at)")
					.append("values ('" + message.messageUUID() + "', ")
					.append("'" + super.processSingleQuote(message.content()) + "', ")
					.append("'" + message.messageType().name() + "', ")
					.append(Strs.isEmpty(message.requestUUID()) ? "null, "
							: "'" + message.requestUUID() + "', ")
					.append("'" + message.fromUser().userUUID() + "', ")
					.append("'" + message.toUser().userUUID() + "', ")
					.append("'" + TimeUtils.formatDate(message.createdAt())
							+ "')").toString();
			log.info(Strs.join("SQL:", updateSQL));
			getWriter().update(updateSQL);
			return true;
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
	}

	public boolean deleteByMsgUUID(String uuid) {
		return super.deleteByUniqueKey("message_uuid", uuid);
	}

	public boolean deleteByUserUUID(String uuid) {
		return super.deleteByUniqueKey("from_user_uuid", uuid);
	}

	@Override
	public List<PuluoPrivateMessage> getFriendRequestMessage(
			String fromUserUUID, String toUserUUID) {
		try {
			SqlReader reader = getReader();
			String selectSQL = new StringBuilder()
					.append("select * from ")
					.append(super.getFullTableName())
					.append(" where from_user_uuid = ?  and to_user_uuid = ? and message_type = '"
							+ PuluoMessageType.FriendRequest.name()
							+ "' order by created_at desc").toString();
			log.info(super.sqlRequestLog(selectSQL, fromUserUUID, toUserUUID));
			List<PuluoPrivateMessage> entities = reader.query(
					selectSQL.toString(), new Object[] { fromUserUUID,
							toUserUUID }, new PrivateMessageMapper());
			return entities;
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	@Override
	public List<PuluoPrivateMessage> getMessagesByFromUser(String userUUID,
			DateTime time_from, DateTime time_to, int limit, int offset) {
		try {
			SqlReader reader = getReader();
			StringBuilder selectSQL = new StringBuilder()
					.append("select * from ")
					.append(super.getFullTableName())
					.append(" where from_user_uuid = ? and message_type <> '"
							+ PuluoMessageType.FriendRequest.name() + "'");
			if (!Strs.isEmpty(TimeUtils.formatDate(time_from))) {
				selectSQL.append(" and created_at >= '"
						+ TimeUtils.formatDate(time_from) + "'");
			}
			if (!Strs.isEmpty(TimeUtils.formatDate(time_to))) {
				selectSQL.append(" and created_at <= '"
						+ TimeUtils.formatDate(time_to) + "'");
			}
			selectSQL.append(" order by created_at");
			if (limit > 0)
				selectSQL.append(" limit ").append(limit);
			if (offset > 0)
				selectSQL.append(" offset ").append(offset);

			log.info(selectSQL.toString());
			List<PuluoPrivateMessage> entities = reader.query(
					selectSQL.toString(), new Object[] { userUUID },
					new PrivateMessageMapper());
			return entities;
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	@Override
	public boolean createTable() {
		try {
			String createSQL = new StringBuilder().append("create table ")
					.append(super.getFullTableName())
					.append(" (id serial primary key, ")
					.append("message_uuid text unique, ")
					.append("content text, ").append("message_type text, ")
					.append("friend_request_uuid text, ")
					.append("from_user_uuid text, ")
					.append("to_user_uuid text, ")
					.append("created_at timestamp)").toString();
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
							+ "_i_message_uuid on ")
					.append(super.getFullTableName()).append(" (message_uuid)")
					.toString();
			log.info(indexSQL2);
			getWriter().execute(indexSQL2);

			String indexSQL3 = new StringBuilder()
					.append("create index " + super.getFullTableName()
							+ "_i_friend_request_uuid on ")
					.append(super.getFullTableName())
					.append(" (friend_request_uuid)").toString();
			log.info(indexSQL3);
			getWriter().execute(indexSQL3);

			String indexSQL4 = new StringBuilder()
					.append("create index " + super.getFullTableName()
							+ "_i_from_user_uuid on ")
					.append(super.getFullTableName())
					.append(" (from_user_uuid)").toString();
			log.info(indexSQL4);
			getWriter().execute(indexSQL4);

			String indexSQL5 = new StringBuilder()
					.append("create index " + super.getFullTableName()
							+ "_i_to_user_uuid on ")
					.append(super.getFullTableName()).append(" (to_user_uuid)")
					.toString();
			log.info(indexSQL5);
			getWriter().execute(indexSQL5);

			String indexSQL6 = new StringBuilder()
					.append("create index " + super.getFullTableName()
							+ "_i_from_n_to on ")
					.append(super.getFullTableName())
					.append(" (from_user_uuid, to_user_uuid)").toString();
			log.info(indexSQL6);
			getWriter().execute(indexSQL6);

			return true;
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
	}
	
	@Override
	public int getMessagesCountByUser(String from_user_uuid,
			String to_user_uuid, DateTime time_from, DateTime time_to) {
		try {
			SqlReader reader = getReader();
			StringBuilder selectSQL = new StringBuilder()
					.append("select count(*) from ").append(super.getFullTableName())
					.append(" where ");
			String query = buildMessageQuery(from_user_uuid, to_user_uuid,
					time_from, time_to);
			selectSQL.append(query);
			log.info(selectSQL.toString());
			int cnt = reader.queryForInt(selectSQL.toString());
			return cnt;
		} catch (Exception e) {
			log.error(e.getStackTrace().toString());
			return 0;
		}
	}

	@Override
	public List<PuluoPrivateMessage> getMessagesByUser(String from_user_uuid,
			String to_user_uuid, DateTime time_from, DateTime time_to,
			int limit, int offset, SortDirection direction) {
		try {
			SqlReader reader = getReader();
			StringBuilder selectSQL = new StringBuilder()
					.append("select t.* from (select * from ").append(super.getFullTableName())
					.append(" where ");
			String query = buildMessageQuery(from_user_uuid, to_user_uuid,
					time_from, time_to);
			selectSQL.append(query);
			selectSQL.append(" order by created_at ").append(direction.toString());
			if (limit > 0)
				selectSQL.append(" limit ").append(limit);
			if (offset > 0)
				selectSQL.append(" offset ").append(offset);
			selectSQL.append(") t order by t.created_at ").append(direction.toString());
			log.info(selectSQL.toString());
			List<PuluoPrivateMessage> entities = reader.query(
					selectSQL.toString(), new PrivateMessageMapper());
			return entities;
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	@Override
	public PuluoPrivateMessage findByUUID(String uuid) {
		try {
			SqlReader reader = getReader();
			String selectSQL = new StringBuilder().append("select * from ")
					.append(super.getFullTableName())
					.append(" where message_uuid = ?").toString();

			log.info(super.sqlRequestLog(selectSQL, uuid));
			List<PuluoPrivateMessage> entities = reader.query(selectSQL,
					new Object[] { uuid }, new PrivateMessageMapper());
			return verifyUniqueResult(entities);
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	/**
	 * list all users 'user_uuid' sent messages to and for each user returns the
	 * latest msg
	 */
	@Override
	public List<PuluoPrivateMessage> getSentMessageSummary(String user_uuid) {
		try {
			SqlReader reader = getReader();
			String selectSQL = new StringBuilder()
					.append("select d.* from ( select to_user_uuid, max(created_at) as max_time from ")
					.append(super.getFullTableName())
					.append(" where from_user_uuid = ? group by to_user_uuid) s ")
					.append("join ").append(super.getFullTableName())
					.append(" d on s.to_user_uuid = d.to_user_uuid")
					.append(" and s.max_time = d.created_at").toString();

			log.info(super.sqlRequestLog(selectSQL, user_uuid));
			List<PuluoPrivateMessage> entities = reader.query(selectSQL,
					new Object[] { user_uuid }, new PrivateMessageMapper());
			return entities;
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	/**
	 * list all users 'user_uuid' received messages from and for each user
	 * returns the latest msg
	 */
	@Override
	public List<PuluoPrivateMessage> getReceivedMessageSummary(String user_uuid) {
		try {
			SqlReader reader = getReader();
			String selectSQL = new StringBuilder()
					.append("select d.* from ( select from_user_uuid, max(created_at) as max_time from ")
					.append(super.getFullTableName())
					.append(" where to_user_uuid = ? group by from_user_uuid) s ")
					.append("join ").append(super.getFullTableName())
					.append(" d on s.from_user_uuid = d.from_user_uuid")
					.append(" and s.max_time = d.created_at").toString();

			log.info(super.sqlRequestLog(selectSQL, user_uuid));
			List<PuluoPrivateMessage> entities = reader.query(selectSQL,
					new Object[] { user_uuid }, new PrivateMessageMapper());
			return entities;
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	private String buildMessageQuery(String from_user_uuid,
			String to_user_uuid, DateTime time_from, DateTime time_to) {
		StringBuilder selectSQL = new StringBuilder();
		String usersFilter = null;
		if (!Strs.isEmpty(from_user_uuid) && !Strs.isEmpty(to_user_uuid)) {
			usersFilter = String
					.format(" ((from_user_uuid = '%s' and to_user_uuid = '%s') or (from_user_uuid = '%s' and to_user_uuid = '%s')) and",
							from_user_uuid, to_user_uuid, to_user_uuid,
							from_user_uuid);
		} else if (!Strs.isEmpty(from_user_uuid)) {
			usersFilter = String.format(" from_user_uuid = '%s' and",
					from_user_uuid);
		} else if (!Strs.isEmpty(to_user_uuid)) {
			usersFilter = String.format(" to_user_uuid = '%s' and",
					to_user_uuid);
		} else {
			usersFilter = "";
		}
		selectSQL.append(usersFilter);
		selectSQL.append(" message_type <> '"
				+ PuluoMessageType.FriendRequest.name() + "'");
		if (!Strs.isEmpty(TimeUtils.formatDate(time_from))) {
			selectSQL.append(" and created_at >= '"
					+ TimeUtils.formatDate(time_from) + "'");
		}
		if (!Strs.isEmpty(TimeUtils.formatDate(time_to))) {
			selectSQL.append(" and created_at <= '"
					+ TimeUtils.formatDate(time_to) + "'");
		}
		return selectSQL.toString();
	}
}

class PrivateMessageMapper implements RowMapper<PuluoPrivateMessage> {
	@Override
	public PuluoPrivateMessage mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		PuluoPrivateMessageImpl message = new PuluoPrivateMessageImpl(
				rs.getString("message_uuid"), rs.getString("content"),
				TimeUtils.parseDateTime(TimeUtils.formatDate(rs
						.getTimestamp("created_at"))),
				PuluoMessageType.valueOf(rs.getString("message_type")),
				rs.getString("friend_request_uuid"),
				rs.getString("from_user_uuid"), rs.getString("to_user_uuid"));
		return message;
	}
}
