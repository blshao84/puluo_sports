package com.puluo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.jdbc.core.RowMapper;

import com.puluo.dao.PuluoSessionDao;
import com.puluo.entity.PuluoSession;
import com.puluo.entity.impl.PuluoSessionImpl;
import com.puluo.jdbc.DalTemplate;
import com.puluo.jdbc.SqlReader;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.Strs;
import com.puluo.util.TimeUtils;

public class PuluoSessionDaoImpl extends DalTemplate implements PuluoSessionDao {

	public static Log log = LogFactory.getLog(PuluoSessionDaoImpl.class);

	@Override
	public boolean createTable() {
		try {
			String createSQL = new StringBuilder().append("create table ")
					.append(super.getFullTableName())
					.append(" (id serial primary key, ")
					.append("user_mobile text not null, ")
					.append("session_id text not null, ")
					.append("created_at timestamp not null, ")
					.append("deleted_at timestamp)").toString();
			log.info(createSQL);
			getWriter().execute(createSQL);
			
			String indexSQL1 = new StringBuilder().append("create index " + super.getFullTableName() + "_i_id on ")
					.append(super.getFullTableName())
					.append(" (id)").toString();
			log.info(indexSQL1);
			getWriter().execute(indexSQL1);
			
			String indexSQL2 = new StringBuilder().append("create index " + super.getFullTableName() + "_i_user_mobile on ")
					.append(super.getFullTableName())
					.append(" (user_mobile)").toString();
			log.info(indexSQL2);
			getWriter().execute(indexSQL2);
			
			String indexSQL3 = new StringBuilder().append("create index " + super.getFullTableName() + "_i_session_id on ")
					.append(super.getFullTableName())
					.append(" (session_id)").toString();
			log.info(indexSQL3);
			getWriter().execute(indexSQL3);
			
			return true;
		} catch (Exception e) {
			log.debug(e.getMessage());
			return false;
		}
	}

	public boolean deleteBySessionId(String sessionID) {
		return super.deleteByUniqueKey("session_id", sessionID);
	}

	@Override
	public boolean save(String userID, String sessionID) {
		try {
			String insertSQL = new StringBuilder()
					.append("insert into ")
					.append(super.getFullTableName())
					.append(" (user_mobile, session_id, created_at)")
					.append(" values ('" + userID + "', '" + sessionID
							+ "', now()::timestamp)").toString();
			log.info(insertSQL);
			getWriter().update(insertSQL);
		} catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public PuluoSession getBySessionID(String sessionID) {
		SqlReader reader = getReader();
		String selectSQL = new StringBuilder().append("select * from ")
				.append(super.getFullTableName())
				.append(" where session_id = ?").toString();
		log.info(super.sqlRequestLog(selectSQL, sessionID));
		List<PuluoSession> entities = reader.query(selectSQL,
				new Object[] { sessionID }, new RowMapper<PuluoSession>() {
					@Override
					public PuluoSession mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						DateTime created_at = TimeUtils.parseDateTime(TimeUtils
								.formatDate(rs.getTimestamp("created_at")));
						DateTime deleted_at = rs.getTimestamp("deleted_at") != null ? TimeUtils
								.parseDateTime(TimeUtils.formatDate(rs
										.getTimestamp("deleted_at"))) : null;
						PuluoSessionImpl puluoSession = new PuluoSessionImpl(rs
								.getString("user_mobile"), rs
								.getString("session_id"), created_at,
								deleted_at);
						return puluoSession;
					}
				});
		return verifyUniqueResult(entities);
	}

	@Override
	public boolean deleteSession(String sessionID) {
		try {
			String updateSQL = new StringBuilder().append("update ")
					.append(super.getFullTableName())
					.append(" set deleted_at = now()::timestamp")
					.append(" where session_id = '" + sessionID + "'")
					.toString();
			log.info(Strs.join("SQL:",updateSQL));
			getWriter().update(updateSQL);
		} catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public PuluoSession getByMobile(String mobile) {
		SqlReader reader = getReader();
		String selectSQL = new StringBuilder().append("select * from ")
				.append(super.getFullTableName())
				.append(" where user_mobile = ? and deleted_at is null")
				.toString();
		log.info(super.sqlRequestLog(selectSQL, mobile));
		List<PuluoSession> entities = reader.query(selectSQL,
				new Object[] { mobile }, new RowMapper<PuluoSession>() {
					@Override
					public PuluoSession mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						DateTime created_at = TimeUtils.parseDateTime(TimeUtils
								.formatDate(rs.getTimestamp("created_at")));
						DateTime deleted_at = rs.getTimestamp("deleted_at") != null ? TimeUtils
								.parseDateTime(TimeUtils.formatDate(rs
										.getTimestamp("deleted_at"))) : null;
						PuluoSessionImpl puluoSession = new PuluoSessionImpl(rs
								.getString("user_mobile"), rs
								.getString("session_id"), created_at,
								deleted_at);
						return puluoSession;
					}
				});
		return verifyUniqueResult(entities);
	}

	@Override
	public boolean deleteAllSessions(String mobile) {
		try {
			String updateSQL = new StringBuilder().append("update ")
					.append(super.getFullTableName())
					.append(" set deleted_at = now()::timestamp")
					.append(" where user_mobile = '" + mobile + "'").toString();
			log.info(Strs.join("SQL:",updateSQL));
			getWriter().update(updateSQL);
		} catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}
		return true;
	}
	
	public boolean obliterateAllSessions(String mobile) {
		try {
			String updateSQL = new StringBuilder().append("delete from ")
					.append(super.getFullTableName())
					.append(" where user_mobile = '" + mobile + "'").toString();
			log.info(Strs.join("SQL:",updateSQL));
			getWriter().update(updateSQL);
		} catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public DateTime now() {
		SqlReader reader = getReader();
		String selectSQL = new StringBuilder().append("select now()::timestamp").toString();
		List<DateTime> entities = reader.query(selectSQL,
				new Object[] {}, new RowMapper<DateTime>() {
					@Override
					public DateTime mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return TimeUtils.parseDateTime(TimeUtils.formatDate(rs.getTimestamp(1)));
					}
				});
		return entities.get(0);
	}
	
	
}
