package com.puluo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.puluo.dao.PuluoWechatBindingDao;
import com.puluo.entity.PuluoWechatBinding;
import com.puluo.entity.impl.PuluoWechatBindingImpl;
import com.puluo.jdbc.DalTemplate;
import com.puluo.jdbc.SqlReader;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.Strs;
import com.puluo.util.TimeUtils;

public class PuluoWechatBindingDaoImpl extends DalTemplate implements
		PuluoWechatBindingDao {

	public static Log log = LogFactory.getLog(PuluoWechatBindingDaoImpl.class);

	@Override
	public boolean createTable() {
		try {
			String createSQL = new StringBuilder().append("create table ")
					.append(super.getFullTableName())
					.append(" (id serial primary key, ")
					.append("user_mobile text unique, ")
					.append("open_id text unique, ").append("status integer, ")
					.append("created_at timestamp)").toString();
			log.info(createSQL);
			getWriter().execute(createSQL);
			// TODO create index
		} catch (Exception e) {
			log.debug(e.getMessage());
			return false;
		}
		return true;
	}
	
	public boolean deleteByUserMobile(String mobile){
		return super.deleteByUniqueKey("user_mobile", mobile);
	}

	@Override
	public PuluoWechatBinding findByOpenId(String openId) {
		SqlReader reader = getReader();
		String selectSQL = new StringBuilder().append("select * from ")
				.append(super.getFullTableName()).append(" where open_id = ?")
				.toString();
		log.info(super.sqlRequestLog(selectSQL, openId));
		List<PuluoWechatBinding> entities = reader.query(selectSQL,
				new Object[] { openId }, new PuluoWechatBindingRowMapper());
		return verifyUniqueResult(entities);
	}

	@Override
	public PuluoWechatBinding findByMobile(String mobile) {
		SqlReader reader = getReader();
		String selectSQL = new StringBuilder().append("select * from ")
				.append(super.getFullTableName())
				.append(" where user_mobile = ?").toString();
		log.info(super.sqlRequestLog(selectSQL, mobile));
		List<PuluoWechatBinding> entities = reader.query(selectSQL,
				new Object[] { mobile }, new PuluoWechatBindingRowMapper());
		return verifyUniqueResult(entities);
	}

	@Override
	public boolean saveBinding(String mobile, String openId) {
		try {
			String updateSQL = new StringBuilder().append("insert into ")
					.append(super.getFullTableName())
					.append(" (user_mobile,open_id,status,created_at)")
					.append(" values ('").append(mobile).append("','")
					.append(openId).append("',").append(0)
					.append(", now()::timestamp)").toString();
			log.info(Strs.join("SQL:",updateSQL));
			return getWriter().ensureUpdate(updateSQL);
		} catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}
	}

	@Override
	public boolean updateBinding(String openId, int status) {
		try {
			String updateSQL = new StringBuilder().append("update ")
					.append(super.getFullTableName())
					.append(Strs.join(" set status = ", status))
					.append(Strs.join(" where open_id = '", openId, "'"))
					.toString();
			log.info(Strs.join("SQL:",updateSQL));
			return getWriter().ensureUpdate(updateSQL);
		} catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}
	}

	@Override
	public boolean updateMobile(String openId, String mobile) {
		try {
			String updateSQL = new StringBuilder().append("update ")
					.append(super.getFullTableName())
					.append(Strs.join(" set user_mobile = '", mobile, "'"))
					.append(Strs.join(" where open_id = '", openId, "'"))
					.toString();
			log.info(Strs.join("SQL:",updateSQL));
			return getWriter().ensureUpdate(updateSQL);
		} catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}
	}

}

class PuluoWechatBindingRowMapper implements RowMapper<PuluoWechatBinding> {
	@Override
	public PuluoWechatBinding mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		PuluoWechatBinding binding = new PuluoWechatBindingImpl(
				rs.getString("user_mobile"), rs.getString("open_id"),
				rs.getInt("status"), TimeUtils.parseDateTime(TimeUtils
						.formatDate(rs.getTimestamp("created_at"))));
		return binding;
	}
}
