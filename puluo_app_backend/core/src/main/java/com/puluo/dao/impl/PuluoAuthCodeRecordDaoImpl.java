package com.puluo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.puluo.dao.PuluoAuthCodeRecordDao;
import com.puluo.entity.PuluoAuthCodeRecord;
import com.puluo.entity.impl.PuluoAuthCodeRecordImpl;
import com.puluo.entity.impl.PuluoAuthCodeType;
import com.puluo.jdbc.DalTemplate;
import com.puluo.jdbc.SqlReader;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.PuluoDatabaseException;
import com.puluo.util.TimeUtils;

public class PuluoAuthCodeRecordDaoImpl extends DalTemplate implements
		PuluoAuthCodeRecordDao {
	
	public static Log log = LogFactory.getLog(PuluoAuthCodeRecordDaoImpl.class);

	@Override
	public boolean createTable() {
		try {
			String createSQL = new StringBuilder().append("create table ")
				.append(super.getFullTableName())
				.append(" (id serial primary key, ")
				.append("user_mobile text unique, ")
				.append("auth_code text, ")
				.append("auth_type text, ")
				.append("created_at timestamp, ")
				.append("updated_at timestamp)")
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
	public boolean upsertRegistrationAuthCode(String mobile, String authCode) {
		try {
			SqlReader reader = getReader();
			String querySQL = new StringBuilder().append("select count(1) from ")
					.append(super.getFullTableName())
					.append(" where user_mobile = '" + mobile + "'")
					.toString();
			log.info(reader.queryForInt(querySQL));
			String updateSQL;
			if (reader.queryForInt(querySQL)>0) {
				updateSQL = new StringBuilder().append("update ")
						.append(super.getFullTableName())
						.append(" set auth_code = '" + authCode + "', updated_at = now()::timestamp")
						.append(" where user_mobile = '" + mobile + "'")
						.toString();
			} else {
				updateSQL = new StringBuilder().append("insert into ")
						.append(super.getFullTableName())
						.append(" (user_mobile, auth_code, created_at)")
						.append(" values ('" + mobile + "', '" + authCode + "', now()::timestamp)")
						.toString();
			}
			log.info(updateSQL);
			getWriter().update(updateSQL);
			return true;
		} catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}
	}

	@Override
	public PuluoAuthCodeRecord getRegistrationAuthCodeFromMobile(String mobile) {
		SqlReader reader = getReader();
		StringBuilder selectSQL = new StringBuilder().append("select * from ")
				.append(super.getFullTableName()).append(" where user_mobile = ?");
		List<PuluoAuthCodeRecord> entities = reader.query(selectSQL.toString(), new Object[]{mobile},
				new RowMapper<PuluoAuthCodeRecord>() {
					@Override
					public PuluoAuthCodeRecord mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						String auth_type = rs.getString("auth_type");
						if (auth_type==null) {
							auth_type = PuluoAuthCodeType.Unknown.name();
							log.info("getRegistrationAuthCodeFromMobile: auth_type为null, 默认设置为PuluoAuthCodeType.Unknown!");
						}
						PuluoAuthCodeRecordImpl authCode = new PuluoAuthCodeRecordImpl(
								rs.getString("user_mobile"),
								rs.getString("auth_code"),
								PuluoAuthCodeType.valueOf(auth_type),
								TimeUtils.parseDateTime(TimeUtils.formatDate(rs.getTimestamp("created_at"))),
								TimeUtils.parseDateTime(TimeUtils.formatDate(rs.getTimestamp("updated_at"))));
						return authCode;
					}
				});
		if (entities.size() == 1)
			return entities.get(0);
		else if (entities.size() > 1)
			throw new PuluoDatabaseException("通过user mobile查到多个auth code！");
		else
			return null;
	}
}
