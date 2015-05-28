package com.puluo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.puluo.dao.PuluoTimelineLikeDao;
import com.puluo.entity.PuluoTimelineLike;
import com.puluo.entity.impl.PuluoTimelineLikeImpl;
import com.puluo.jdbc.DalTemplate;
import com.puluo.jdbc.SqlReader;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.Strs;
import com.puluo.util.TimeUtils;

public class PuluoTimelineLikeDaoImpl extends DalTemplate implements PuluoTimelineLikeDao {

	public static Log log = LogFactory.getLog(PuluoTimelineLikeDaoImpl.class);

	@Override
	public boolean createTable() {
		try {
			String createSQL = new StringBuilder().append("create table ")
					.append(super.getFullTableName())
					.append(" (id serial primary key, ")
					.append("timeline_uuid text unique, ")
					.append("user_uuid text not null, ")
					.append("user_name text, ")
					.append("created_at timestamp)").toString();
			log.info(createSQL);
			getWriter().execute(createSQL);
			
			String indexSQL1 = new StringBuilder().append("create index " + super.getFullTableName() + "_i_id on ")
					.append(super.getFullTableName())
					.append(" (id)").toString();
			log.info(indexSQL1);
			getWriter().execute(indexSQL1);
			
			String indexSQL2 = new StringBuilder().append("create index " + super.getFullTableName() + "_i_timeline_uuid on ")
					.append(super.getFullTableName())
					.append(" (timeline_uuid)").toString();
			log.info(indexSQL2);
			getWriter().execute(indexSQL2);
			
			return true;
		} catch (Exception e) {
			log.debug(e.getMessage());
			return false;
		}
	}

	@Override
	public String saveTimelineLike(String timeline_uuid, String user_uuid) {
		try {
			StringBuilder sb = new StringBuilder()
					.append("insert into ")
					.append(super.getFullTableName())
					.append(" (timeline_uuid, user_uuid, created_at)")
					.append(" values ('" + timeline_uuid + "', '" + user_uuid + "', now()::timestamp)");
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
	public String removeTimelineLike(String timeline_uuid, String from_user_uuid) {
		try {
			StringBuilder sb = new StringBuilder()
					.append("delete ")
					.append(super.getFullTableName())
					.append(" where timeline_uuid = '" + timeline_uuid + "' and user_uuid ='" + from_user_uuid + "'");
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
	public List<PuluoTimelineLike> getTotalLikes(String timeline_uuid) {
		try {
			SqlReader reader = getReader();
			String selectSQL = new StringBuilder()
					.append("select * from ")
					.append(super.getFullTableName())
					.append(" where timeline_uuid = ?").toString();
			log.info(super.sqlRequestLog(selectSQL,timeline_uuid));
			List<PuluoTimelineLike> entities = reader.query(
					selectSQL.toString(), new Object[] {timeline_uuid}, new PuluoTimelineLikeMapper());
			return entities;
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	class PuluoTimelineLikeMapper implements RowMapper<PuluoTimelineLike> {
		@Override
		public PuluoTimelineLike mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			PuluoTimelineLike like = new PuluoTimelineLikeImpl(
					rs.getString("timeline_uuid"),
					rs.getString("user_uuid"),
					rs.getString("user_name"),
					TimeUtils.parseDateTime(TimeUtils.formatDate(rs.getTimestamp("created_at"))));
			return like;
		}
	}
	
	public boolean deleteByUUID(String uuid){
		return super.deleteByUniqueKey("uuid", uuid);
	}

}
