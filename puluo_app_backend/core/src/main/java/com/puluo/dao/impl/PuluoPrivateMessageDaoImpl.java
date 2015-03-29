package com.puluo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.jdbc.core.RowMapper;

import com.puluo.dao.PuluoPrivateMessageDao;
import com.puluo.entity.PuluoMessageType;
import com.puluo.entity.PuluoPrivateMessage;
import com.puluo.entity.impl.PuluoPrivateMessageImpl;
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
			String updateSQL = new StringBuilder().append("insert into ")
						.append(super.getFullTableName())
						.append(" (message_uuid, content, message_type, friend_request_uuid, from_user_uuid, to_user_uuid, created_at)")
						.append("values ('" + message.messageUUID() + "', ")
						.append("'" + message.content() + "', ")
						.append("'" + message.messageType().name() + "', ")
						.append(Strs.isEmpty(message.requestUUID()) ? "null, " : "'" + message.requestUUID() + "', ")
						.append("'" + message.fromUser().userUUID() + "', ")
						.append("'" + message.toUser().userUUID() + "', ")
						.append("'" + TimeUtils.formatDate(message.createdAt()) + "')")
						.toString();
			log.info(updateSQL);
			getWriter().update(updateSQL);
			return true;
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
	}

	@Override
	public List<PuluoPrivateMessage> getFriendRequestMessage(String fromUserUUID, String toUserUUID) {
		try {
			SqlReader reader = getReader();
			StringBuilder selectSQL = new StringBuilder().append("select * from ")
					.append(super.getFullTableName())
					.append(" where from_user_uuid = ?  and to_user_uuid = ? and message_type = '" + PuluoMessageType.FriendRequest.name() + "'" );
			log.info(selectSQL.toString());
			List<PuluoPrivateMessage> entities = reader.query(selectSQL.toString(), new Object[]{fromUserUUID, toUserUUID},
					new RowMapper<PuluoPrivateMessage>() {
						@Override
						public PuluoPrivateMessage mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							PuluoPrivateMessageImpl message = new PuluoPrivateMessageImpl(
									rs.getString("message_uuid"),
									rs.getString("content"),
									TimeUtils.parseDateTime(TimeUtils.formatDate(rs.getTimestamp("created_at"))),
									PuluoMessageType.valueOf(rs.getString("message_type")),
									rs.getString("friend_request_uuid"),
									rs.getString("from_user_uuid"),
									rs.getString("to_user_uuid"));
							return message;
						}
					});
			return entities;
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	@Override
	public PuluoPrivateMessage[] getMessagesByFromUser(String userUUID,
			DateTime time_from, DateTime time_to) {
		try {
			SqlReader reader = getReader();
			StringBuilder selectSQL = new StringBuilder().append("select * from ")
					.append(super.getFullTableName())
					.append(" where from_user_uuid = ? and message_type <> '" + PuluoMessageType.FriendRequest.name() + "'" );
			if (!Strs.isEmpty(TimeUtils.formatDate(time_from))) {
				selectSQL.append(" and created_at >= '" + TimeUtils.formatDate(time_from) + "'");
			}
			if (!Strs.isEmpty(TimeUtils.formatDate(time_to))) {
				selectSQL.append(" and created_at <= '" + TimeUtils.formatDate(time_from) + "'");
			}
			log.info(selectSQL.toString());
			List<PuluoPrivateMessage> entities = reader.query(selectSQL.toString(), new Object[]{userUUID},
					new RowMapper<PuluoPrivateMessage>() {
						@Override
						public PuluoPrivateMessage mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							PuluoPrivateMessageImpl message = new PuluoPrivateMessageImpl(
									rs.getString("message_uuid"),
									rs.getString("content"),
									TimeUtils.parseDateTime(TimeUtils.formatDate(rs.getTimestamp("created_at"))),
									PuluoMessageType.valueOf(rs.getString("message_type")),
									rs.getString("friend_request_uuid"),
									rs.getString("from_user_uuid"),
									rs.getString("to_user_uuid"));
							return message;
						}
					});
			if (entities.size() > 0)
				return (PuluoPrivateMessage[]) entities.toArray();
			else
				return null;
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
				.append("content timestamp, ")
				.append("message_type text, ")
				.append("friend_request_uuid text, ")
				.append("from_user_uuid text, ")
				.append("to_user_uuid text, ")
				.append("created_at timestamp)")
				.toString();
			log.info(createSQL);
			getWriter().execute(createSQL);
			// TODO create index
			return true;
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
	}

	@Override
	public PuluoPrivateMessage[] getMessagesByUser(String from_user_uuid,
			String to_user_uuid, DateTime time_from, DateTime time_to) {
		try {
			SqlReader reader = getReader();
			StringBuilder selectSQL = new StringBuilder().append("select * from ")
					.append(super.getFullTableName())
					.append(" where");
			Object[] args;
			if (!Strs.isEmpty(from_user_uuid)
					&& !Strs.isEmpty(to_user_uuid)) {
				selectSQL.append(" from_user_uuid = ? and to_user_uuid = ?");
				args = new Object[]{from_user_uuid, to_user_uuid};
			} else if (!Strs.isEmpty(from_user_uuid)) {
				selectSQL.append(" from_user_uuid = ?");
				args = new Object[]{from_user_uuid};
			} else if (!Strs.isEmpty(to_user_uuid)) {
				selectSQL.append(" to_user_uuid = ?");
				args = new Object[]{to_user_uuid};
			} else {
				args = new Object[]{};
			}
			selectSQL.append(" message_type <> '" + PuluoMessageType.FriendRequest.name() + "'");
			if (!Strs.isEmpty(TimeUtils.formatDate(time_from))) {
				selectSQL.append(" and created_at >= '" + TimeUtils.formatDate(time_from) + "'");
			}
			if (!Strs.isEmpty(TimeUtils.formatDate(time_to))) {
				selectSQL.append(" and created_at <= '" + TimeUtils.formatDate(time_from) + "'");
			}
			log.info(selectSQL.toString());
			List<PuluoPrivateMessage> entities = reader.query(selectSQL.toString(), args,
					new RowMapper<PuluoPrivateMessage>() {
						@Override
						public PuluoPrivateMessage mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							PuluoPrivateMessageImpl message = new PuluoPrivateMessageImpl(
									rs.getString("message_uuid"),
									rs.getString("content"),
									TimeUtils.parseDateTime(TimeUtils.formatDate(rs.getTimestamp("created_at"))),
									PuluoMessageType.valueOf(rs.getString("message_type")),
									rs.getString("friend_request_uuid"),
									rs.getString("from_user_uuid"),
									rs.getString("to_user_uuid"));
							return message;
						}
					});
			if (entities.size() > 0)
				return (PuluoPrivateMessage[]) entities.toArray();
			else
				return null;
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

}
