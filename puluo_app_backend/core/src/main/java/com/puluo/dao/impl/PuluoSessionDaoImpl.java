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
import com.puluo.util.PuluoDatabaseException;
import com.puluo.util.TimeUtils;

public class PuluoSessionDaoImpl extends DalTemplate implements PuluoSessionDao {

	public static Log log = LogFactory.getLog(PuluoSessionDaoImpl.class);

	@Override
	public boolean createTable() {
		try {
			String createSQL = new StringBuilder().append("create table ")
				.append(super.getFullTableName())
				.append(" (id serial primary key, ")
				.append("user_uuid text not null, ")
				.append("session_id text not null, ")
				.append("created_at timestamp not null, ")
				.append("deleted_at timestamp)")
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
	public boolean save(String userID, String sessionID) {
		try {
			String insertSQL = new StringBuilder().append("insert into ")
					.append(super.getFullTableName())
					.append(" (user_uuid, session_id, created_at)")
					.append(" values ('" + userID + "', '" + sessionID + "', now()::timestamp)")
					.toString();
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
				.append(super.getFullTableName()).append(" where session_id = ?")
				.toString();
		List<PuluoSession> entities = reader.query(selectSQL, new Object[] {sessionID},
				new RowMapper<PuluoSession>() {
					@Override
					public PuluoSession mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						DateTime created_at = TimeUtils.parseDateTime(TimeUtils.formatDate(rs.getTimestamp("created_at")));
						DateTime deleted_at = rs.getTimestamp("deleted_at")!=null ? TimeUtils.parseDateTime(TimeUtils.formatDate(rs.getTimestamp("deleted_at"))) : null;
						PuluoSessionImpl puluoSession = new PuluoSessionImpl(
								rs.getString("user_uuid"),
								rs.getString("session_id"),
								created_at,
								deleted_at);
						return puluoSession;
					}
				});
		if (entities.size() == 1)
			return entities.get(0);
		else if (entities.size() > 1)
			throw new PuluoDatabaseException("通过session_id查到多个用户！");
		else
			return null;
	}

	@Override
	public boolean deleteSession(String sessionID) {
		try {
			String updateSQL = new StringBuilder().append("update ")
					.append(super.getFullTableName())
					.append(" set deleted_at = now()::timestamp")
					.append(" where session_id = '" + sessionID + "'")
					.toString();
			log.info(updateSQL);
			getWriter().update(updateSQL);
		} catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public PuluoSession getByMobile(String mobile) {
		// TODO Auto-generated method stub
		return null;
	}
}
