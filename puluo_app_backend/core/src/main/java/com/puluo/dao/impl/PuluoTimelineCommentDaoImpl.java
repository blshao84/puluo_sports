package com.puluo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import org.springframework.jdbc.core.RowMapper;

import com.puluo.dao.PuluoTimelineCommentDao;
import com.puluo.entity.PuluoTimelineComment;
import com.puluo.entity.impl.PuluoTimelineCommentImpl;
import com.puluo.jdbc.DalTemplate;
import com.puluo.jdbc.SqlReader;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.Strs;
import com.puluo.util.TimeUtils;

public class PuluoTimelineCommentDaoImpl extends DalTemplate implements PuluoTimelineCommentDao {

	public static Log log = LogFactory.getLog(PuluoTimelineCommentDaoImpl.class);
	
	@Override
	public boolean createTable() {
		try {
			String createSQL = new StringBuilder().append("create table ")
					.append(super.getFullTableName())
					.append(" (id serial primary key, ")
					.append("uuid text unique, ")
					.append("timeline_uuid text not null, ")
					.append("from_user_uuid text not null, ")
					.append("to_user_uuid text not null, ")
					.append("comment_content text not null, ")
					.append("created_at timestamp, ")
					.append("read boolean default true, ")
					.append("deleted boolean default false)").toString();
			log.info(createSQL);
			getWriter().execute(createSQL);
			
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
			
			String indexSQL3 = new StringBuilder().append("create index " + super.getFullTableName() + "_i_timeline_uuid on ")
					.append(super.getFullTableName())
					.append(" (timeline_uuid)").toString();
			log.info(indexSQL3);
			getWriter().execute(indexSQL3);
			
			String indexSQL4 = new StringBuilder().append("create index " + super.getFullTableName() + "_i_from_user_uuid on ")
					.append(super.getFullTableName())
					.append(" (from_user_uuid)").toString();
			log.info(indexSQL4);
			getWriter().execute(indexSQL4);
			
			String indexSQL5 = new StringBuilder().append("create index " + super.getFullTableName() + "_i_to_user_uuid on ")
					.append(super.getFullTableName())
					.append(" (to_user_uuid)").toString();
			log.info(indexSQL5);
			getWriter().execute(indexSQL5);
			
			return true;
		} catch (Exception e) {
			log.debug(e.getMessage());
			return false;
		}
	}

	@Override
	public String saveTimelineComment(String timeline_uuid, String replier, String reply_to, String comment) {
		try {
			StringBuilder sb = new StringBuilder()
					.append("insert into ")
					.append(super.getFullTableName())
					.append(" (uuid, timeline_uuid, from_user_uuid, to_user_uuid, comment_content, created_at)")
					.append(" values ('" + UUID.randomUUID().toString() + "', '" 
							+ timeline_uuid + "', '"
							+ replier + "', '"
							+ reply_to + "', '"
							+ comment + "', now()::timestamp)");
			String insertSQL = sb.toString();
			log.info(Strs.join("SQL:", insertSQL));
			getWriter().update(insertSQL);
			return "success";
		} catch (Exception e) {
			log.info(e.getMessage());
			return "failed";
		}
	}

	@Override
	public String removeTimelineComment(String comment_uuid) {
		try {
			StringBuilder sb = new StringBuilder()
					.append("update ")
					.append(super.getFullTableName())
					.append(" set deleted = true")
					.append(" where uuid = '" + comment_uuid + "'");
			String updateSQL = sb.toString();
			log.info(Strs.join("SQL:", updateSQL));
			getWriter().update(updateSQL);
			return "success";
		} catch (Exception e) {
			log.info(e.getMessage());
			return "failed";
		}
	}

	@Override
	public PuluoTimelineComment getByUUID(String comment_uuid) {
		try {
			SqlReader reader = getReader();
			String selectSQL = new StringBuilder()
					.append("select * from ")
					.append(super.getFullTableName())
					.append(" where uuid = ? and deleted = false").toString();
			log.info(super.sqlRequestLog(selectSQL,comment_uuid));
			List<PuluoTimelineComment> entities = reader.query(
					selectSQL.toString(), new Object[] {comment_uuid}, new PuluoTimelineCommentMapper());
			return super.verifyUniqueResult(entities);
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	@Override
	public List<PuluoTimelineComment> getByTimeline(String timeline_uuid) {
		try {
			SqlReader reader = getReader();
			StringBuilder selectSQL = new StringBuilder()
					.append("select * from ")
					.append(super.getFullTableName())
					.append(" where timeline_uuid = ? and deleted = false");
			log.info(super.sqlRequestLog(selectSQL.toString(),timeline_uuid));
			List<PuluoTimelineComment> entities = reader.query(
					selectSQL.toString(), new Object[] {timeline_uuid}, new PuluoTimelineCommentMapper());
			return entities;
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	@Override
	public List<PuluoTimelineComment> getUnreadCommentsFromUser(String user_uuid) {
		try {
			SqlReader reader = getReader();
			StringBuilder selectSQL = new StringBuilder()
					.append("select * from ")
					.append(super.getFullTableName())
					.append(" where from_user_uuid = ? and read = false and deleted = false");
			log.info(super.sqlRequestLog(selectSQL.toString(),user_uuid));
			List<PuluoTimelineComment> entities = reader.query(
					selectSQL.toString(), new Object[] {user_uuid}, new PuluoTimelineCommentMapper());
			return entities;
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	class PuluoTimelineCommentMapper implements RowMapper<PuluoTimelineComment> {
		@Override
		public PuluoTimelineComment mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			PuluoTimelineComment comment = new PuluoTimelineCommentImpl(
					rs.getString("uuid"),
					rs.getString("timeline_uuid"),
					rs.getString("from_user_uuid"),
					rs.getString("to_user_uuid"),
					rs.getString("comment_content"),
					TimeUtils.parseDateTime(TimeUtils.formatDate(rs.getTimestamp("created_at"))),
					rs.getBoolean("read"),
					rs.getBoolean("deleted"));
			return comment;
		}
	}
	
	public boolean deleteByUUID(String uuid){
		return super.deleteByUniqueKey("uuid", uuid);
	}
}
