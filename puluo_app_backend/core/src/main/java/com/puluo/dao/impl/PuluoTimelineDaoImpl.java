package com.puluo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.puluo.dao.PuluoTimelineDao;
import com.puluo.entity.PuluoTimelinePost;
import com.puluo.entity.impl.PuluoTimelinePostImpl;
import com.puluo.jdbc.DalTemplate;
import com.puluo.jdbc.SqlReader;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.PuluoDatabaseException;
import com.puluo.util.Strs;
import com.puluo.util.TimeUtils;


public class PuluoTimelineDaoImpl extends DalTemplate implements PuluoTimelineDao {
	
	public static Log log = LogFactory.getLog(PuluoTimelineDaoImpl.class);

	@Override
	public boolean createTable() {
		try {
			String createSQL = new StringBuilder().append("create table ")
					.append(super.getFullTableName())
					.append(" (id serial primary key, ")
					.append("uuid text unique, ")
					.append("event_uuid text not null, ")
					.append("owner_uuid text not null, ")
					.append("content text not null, ")
					.append("created_at timestamp, ")
					.append("updated_at timestamp)").toString();
			log.info(createSQL);
			getWriter().execute(createSQL);
			
			String updateSQL = new StringBuilder().append("alter table ")
					.append(super.getFullTableName())
					.append(" add constraint " + super.getFullTableName() + "_pk_event_n_owner unique(event_uuid, owner_uuid)").toString();
			log.info(updateSQL);
			getWriter().execute(updateSQL);
			
			String indexSQL1 = new StringBuilder().append("create index " + super.getFullTableName() + "_i_id on ")
					.append(super.getFullTableName())
					.append(" (id)").toString();
			log.info(indexSQL1);
			getWriter().execute(indexSQL1);
			
			String indexSQL2 = new StringBuilder().append("create index " + super.getFullTableName() + "_i_uuid on ")
					.append(super.getFullTableName())
					.append(" (uuid)").toString();
			log.info(indexSQL2);
			getWriter().execute(indexSQL2);
			
			String indexSQL3 = new StringBuilder().append("create index " + super.getFullTableName() + "_i_event_uuid on ")
					.append(super.getFullTableName())
					.append(" (event_uuid)").toString();
			log.info(indexSQL3);
			getWriter().execute(indexSQL3);
			
			String indexSQL4 = new StringBuilder().append("create index " + super.getFullTableName() + "_i_owner_uuid on ")
					.append(super.getFullTableName())
					.append(" (owner_uuid)").toString();
			log.info(indexSQL4);
			getWriter().execute(indexSQL4);
			
			return true;
		} catch (Exception e) {
			log.debug(e.getMessage());
			return false;
		}
	}


	@Override
	public List<PuluoTimelinePost> getUserTimeline(String user_uuid,
			String since_time, int limit, int offset) {
		try {
			SqlReader reader = getReader();
			StringBuilder selectSQL = new StringBuilder()
					.append("select * from ")
					.append(super.getFullTableName())
					.append(" where owner_uuid = ?");
			if (!Strs.isEmpty(TimeUtils.formatDate(TimeUtils.parseDateTime(since_time)))) {
				selectSQL.append(" and created_at >= '" + since_time + "'");
			}
			if (limit > 0)
				selectSQL.append(" limit ").append(limit);
			if (offset > 0)
				selectSQL.append(" offset ").append(offset);
			log.info(super.sqlRequestLog(selectSQL.toString(),user_uuid));
			List<PuluoTimelinePost> entities = reader.query(
					selectSQL.toString(), new Object[] {user_uuid}, new PuluoTimelinePostMapper());
			return entities;
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	@Override
	public boolean saveTimeline(PuluoTimelinePost timeline) {
		try {
			SqlReader reader = getReader();
			String querySQL = new StringBuilder()
					.append("select count(1) from ")
					.append(super.getFullTableName())
					.append(" where uuid = '" + timeline.timelineUUID() + "'").toString();
			int accountCnt = reader.queryForInt(querySQL);
			log.info(Strs.join("SQL:", querySQL));
			if (accountCnt == 0) {
				StringBuilder sb = new StringBuilder()
						.append("insert into ")
						.append(super.getFullTableName())
						.append(" (uuid, event_uuid, owner_uuid, content, created_at, updated_at)")
						.append(" values ('" + timeline.timelineUUID() + "', '" 
								+ timeline.event().eventUUID() + "', "
								+ timeline.owner().userUUID() + "', "
								+ timeline.content() +"','"
								+ TimeUtils.formatDate(timeline.createdAt()) + "', now()::timestamp)");
				String insertSQL = sb.toString();
				log.info(Strs.join("SQL:", insertSQL));
				getWriter().update(insertSQL);
				return true;
			} else {
				throw new PuluoDatabaseException("已存在对应的timeline，不能插入！");
			}
		} catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}
	}

	@Override
	public PuluoTimelinePost getByUUID(String uuid) {
		try {
			SqlReader reader = getReader();
			String selectSQL = new StringBuilder()
					.append("select * from ")
					.append(super.getFullTableName())
					.append(" where uuid = ?").toString();
			log.info(super.sqlRequestLog(selectSQL,uuid));
			List<PuluoTimelinePost> entities = reader.query(
					selectSQL.toString(), new Object[] {uuid}, new PuluoTimelinePostMapper());
			return super.verifyUniqueResult(entities);
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	@Override
	public PuluoTimelinePost getByUserAndEvent(String user_uuid,
			String event_uuid) {
		try {
			SqlReader reader = getReader();
			String selectSQL = new StringBuilder()
					.append("select * from ")
					.append(super.getFullTableName())
					.append(" where event_uuid = ? and owner_uuid = ?").toString();
			log.info(super.sqlRequestLog(selectSQL,event_uuid,user_uuid));
			List<PuluoTimelinePost> entities = reader.query(
					selectSQL.toString(), new Object[] {event_uuid,user_uuid}, new PuluoTimelinePostMapper());
			return super.verifyUniqueResult(entities);
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}
	
	class PuluoTimelinePostMapper implements RowMapper<PuluoTimelinePost> {
		@Override
		public PuluoTimelinePost mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			PuluoTimelinePost timeline = new PuluoTimelinePostImpl(
					rs.getString("uuid"),
					rs.getString("event_uuid"),
					rs.getString("owner_uuid"),
					rs.getString("content"),
					TimeUtils.parseDateTime(TimeUtils.formatDate(rs.getTimestamp("created_at"))),
					TimeUtils.parseDateTime(TimeUtils.formatDate(rs.getTimestamp("updated_at"))));
			return timeline;
		}
	}
	
	public boolean deleteByUUID(String uuid){
		return super.deleteByUniqueKey("uuid", uuid);
	}
}
