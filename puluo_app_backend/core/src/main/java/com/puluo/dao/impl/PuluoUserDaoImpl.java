package com.puluo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import org.springframework.jdbc.core.RowMapper;

import com.puluo.dao.PuluoUserDao;
import com.puluo.entity.PuluoUser;
import com.puluo.entity.impl.PuluoUserImpl;
import com.puluo.entity.impl.PuluoUserType;
import com.puluo.jdbc.DalTemplate;
import com.puluo.jdbc.SqlReader;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.PuluoDatabaseException;
import com.puluo.util.Strs;
import com.puluo.util.TimeUtils;

public class PuluoUserDaoImpl extends DalTemplate implements PuluoUserDao {

	public static Log log = LogFactory.getLog(PuluoUserDaoImpl.class);

	@Override
	public boolean createTable() {
		try {
			String createSQL = new StringBuilder().append("create table ")
					.append(super.getFullTableName())
					.append(" (id serial primary key, ")
					.append("user_uuid text unique, ")
					.append("mobile text unique, ")
					.append("interests text[], ")
					.append("user_password text not null, ")
					.append("first_name text, ").append("last_name text, ")
					.append("thumbnail text, ").append("large_image text, ")
					.append("user_type text, ").append("email text, ")
					.append("sex varchar(1), ").append("zip text, ")
					.append("country text, ").append("state text, ")
					.append("city text, ").append("occupation text, ")
					.append("address text, ").append("saying text, ")
					.append("birthday timestamp, ")
					.append("created_at timestamp, ")
					.append("updated_at timestamp, ")
					.append("banned boolean default true)").toString();
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
	public boolean save(String mobile, String password) {
		try {
			String uuid = UUID.randomUUID().toString();
			String insertSQL = new StringBuilder()
					.append("insert into ")
					.append(super.getFullTableName())
					.append(" (user_uuid, mobile, user_password, created_at)")
					.append(" values ('" + uuid + "', '" + mobile + "', '"
							+ password + "', now()::timestamp)").toString();
			log.info(insertSQL);
			getWriter().update(insertSQL);
		} catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public boolean updatePassword(PuluoUser user, String newPassword) {
		try {
			String uuid = user.userUUID();
			String updateSQL = new StringBuilder().append("update ")
					.append(super.getFullTableName())
					.append(" set user_password = '" + newPassword + "',")
					.append(" updated_at = now()::timestamp")
					.append(" where user_uuid = '" + uuid + "'").toString();
			log.info(updateSQL);
			getWriter().update(updateSQL);
		} catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public PuluoUser getByMobile(String mobile) {
		SqlReader reader = getReader();
		String selectSQL = new StringBuilder().append("select * from ")
				.append(super.getFullTableName()).append(" where mobile = ?")
				.toString();
		log.info(selectSQL);
		List<PuluoUser> entities;
		try {
			entities = reader.query(selectSQL, new Object[] { mobile },
					new RowMapper<PuluoUser>() {
						@Override
						public PuluoUser mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							String[] array = rs.getArray("interests") != null ? (String[]) rs.getArray("interests").getArray() : new String[] {};
							String user_type = rs.getString("user_type");
							if (Strs.isEmpty(user_type)) {
								user_type = PuluoUserType.User.name();
								log.info("findUser: user_type为null, 默认设置为PuluoUserType.User!");
							}
							String sex = rs.getString("sex");
							PuluoUserImpl puluoUser = new PuluoUserImpl(
									rs.getString("user_uuid"),
									rs.getString("mobile"),
									array,
									rs.getString("user_password"),
									rs.getString("first_name"),
									rs.getString("last_name"),
									rs.getString("thumbnail"),
									rs.getString("large_image"),
									PuluoUserType.valueOf(user_type),
									rs.getString("email"),
									sex != null ? new String(sex + " ").charAt(0) : new String(" ").charAt(0),
									rs.getString("zip"),
									rs.getString("country"),
									rs.getString("state"),
									rs.getString("city"),
									rs.getString("occupation"),
									rs.getString("address"),
									rs.getString("saying"),
									TimeUtils.parseDateTime(TimeUtils.formatDate(rs.getTimestamp("birthday"))),
									TimeUtils.parseDateTime(TimeUtils.formatDate(rs.getTimestamp("created_at"))),
									TimeUtils.parseDateTime(TimeUtils.formatDate(rs.getTimestamp("updated_at"))),
									rs.getBoolean("banned"));
							return puluoUser;
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		if (entities.size() == 1)
			return entities.get(0);
		else if (entities.size() > 1)
			throw new PuluoDatabaseException("通过mobile查到多个用户！");
		else
			return null;
	}

	@Override
	public PuluoUser getByUUID(String uuid) {
		SqlReader reader = getReader();
		String selectSQL = new StringBuilder().append("select * from ")
				.append(super.getFullTableName())
				.append(" where user_uuid = ?").toString();
		log.info(selectSQL);
		List<PuluoUser> entities = reader.query(selectSQL,
				new Object[] { uuid }, new RowMapper<PuluoUser>() {
					@Override
					public PuluoUser mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						String[] array = rs.getArray("interests") != null ? (String[]) rs.getArray("interests").getArray() : new String[] {};
						String user_type = rs.getString("user_type");
						if (Strs.isEmpty(user_type)) {
							user_type = PuluoUserType.User.name();
							log.info("findUser: user_type为null, 默认设置为PuluoUserType.User!");
						}
						String sex = rs.getString("sex");
						PuluoUserImpl puluoUser = new PuluoUserImpl(
								rs.getString("user_uuid"),
								rs.getString("mobile"),
								array,
								rs.getString("user_password"),
								rs.getString("first_name"),
								rs.getString("last_name"),
								rs.getString("thumbnail"),
								rs.getString("large_image"),
								PuluoUserType.valueOf(user_type),
								rs.getString("email"),
								sex != null ? new String(sex + " ").charAt(0) : new String(" ").charAt(0),
								rs.getString("zip"),
								rs.getString("country"),
								rs.getString("state"),
								rs.getString("city"),
								rs.getString("occupation"),
								rs.getString("address"),
								rs.getString("saying"),
								TimeUtils.parseDateTime(TimeUtils.formatDate(rs.getTimestamp("birthday"))),
								TimeUtils.parseDateTime(TimeUtils.formatDate(rs.getTimestamp("created_at"))),
								TimeUtils.parseDateTime(TimeUtils.formatDate(rs.getTimestamp("updated_at"))),
								rs.getBoolean("banned"));
						return puluoUser;
					}
				});
		if (entities.size() == 1)
			return entities.get(0);
		else if (entities.size() > 1)
			throw new PuluoDatabaseException("通过uuid查到多个用户！");
		else
			return null;
	}

	@Override
	public PuluoUser updateProfile(PuluoUser curuser, String first_name,
			String last_name, String thumbnail, String large_image,
			String saying, String email, String sex, String birthday,
			String country, String state, String city, String zip) {
		try {
			String uuid = curuser.userUUID();
			StringBuilder updateSQL = new StringBuilder().append("update ")
					.append(super.getFullTableName()).append(" set ");
			if (!Strs.isEmpty(first_name)) {
				updateSQL.append("first_name = '" + first_name + "', ");
			}
			if (!Strs.isEmpty(last_name)) {
				updateSQL.append("last_name = '" + last_name + "', ");
			}
			if (!Strs.isEmpty(thumbnail)) {
				updateSQL.append("thumbnail = '" + thumbnail + "', ");
			}
			if (!Strs.isEmpty(large_image)) {
				updateSQL.append("large_image = '" + large_image + "', ");
			}
			if (!Strs.isEmpty(saying)) {
				updateSQL.append("saying = '" + saying + "', ");
			}
			if (!Strs.isEmpty(email)) {
				updateSQL.append("email = '" + email + "', ");
			}
			if (!Strs.isEmpty(sex)) {
				updateSQL.append("sex = '" + sex + "', ");
			}
			if (!Strs.isEmpty(birthday)) {
				updateSQL.append("birthday = '" + birthday + "', ");
			}
			if (!Strs.isEmpty(country)) {
				updateSQL.append("country = '" + country + "', ");
			}
			if (!Strs.isEmpty(state)) {
				updateSQL.append("state = '" + state + "', ");
			}
			if (!Strs.isEmpty(city)) {
				updateSQL.append("city = '" + city + "', ");
			}
			if (!Strs.isEmpty(zip)) {
				updateSQL.append("zip = '" + zip + "', ");
			}
			updateSQL.append("updated_at = now()::timestamp ");
			updateSQL.append("where user_uuid = '" + uuid + "'");
			getWriter().update(updateSQL.toString());
			return getByUUID(uuid);
		} catch (Exception e) {
			log.info(e.getMessage());
			throw new PuluoDatabaseException("更新profile时出错！");
		}
	}

	@Override
	public List<PuluoUser> findUser(String first_name, String last_name,
			String email, String mobile, boolean and_or) {
		String and_or_string = and_or ? "and" : "or";
		SqlReader reader = getReader();
		StringBuilder selectSQL = new StringBuilder().append("select * from ")
				.append(super.getFullTableName()).append(" where ");
		boolean hasAnd = false;
		if (!Strs.isEmpty(first_name)) {
			if (hasAnd) {
				selectSQL.append(and_or_string + " first_name='" + first_name + "' ");
			} else {
				selectSQL.append("first_name='" + first_name + "' ");
				hasAnd = true;
			}
		}
		if (!Strs.isEmpty(last_name)) {
			if (hasAnd) {
				selectSQL.append(and_or_string + " last_name='" + last_name + "' ");
			} else {
				selectSQL.append("last_name='" + last_name + "' ");
				hasAnd = true;
			}
		}
		if (!Strs.isEmpty(email)) {
			if (hasAnd) {
				selectSQL.append(and_or_string + " email='" + email + "' ");
			} else {
				selectSQL.append("email='" + email + "' ");
				hasAnd = true;
			}
		}
		if (!Strs.isEmpty(mobile)) {
			if (hasAnd) {
				selectSQL.append(and_or_string + " mobile='" + mobile + "' ");
			} else {
				selectSQL.append("mobile='" + mobile + "' ");
				hasAnd = true;
			}
		}
		log.info(selectSQL.toString());
		List<PuluoUser> entities = reader.query(selectSQL.toString(), new Object[] {}, 
				new RowMapper<PuluoUser>() {
					@Override
					public PuluoUser mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						String[] array = rs.getArray("interests") != null ? (String[]) rs.getArray("interests").getArray() : new String[] {};
						String user_type = rs.getString("user_type");
						if (Strs.isEmpty(user_type)) {
							user_type = PuluoUserType.User.name();
							log.info("findUser: user_type为null, 默认设置为PuluoUserType.User!");
						}
						String sex = rs.getString("sex");
						PuluoUserImpl puluoUser = new PuluoUserImpl(
								rs.getString("user_uuid"),
								rs.getString("mobile"),
								array,
								rs.getString("user_password"),
								rs.getString("first_name"),
								rs.getString("last_name"),
								rs.getString("thumbnail"),
								rs.getString("large_image"),
								PuluoUserType.valueOf(user_type),
								rs.getString("email"),
								sex != null ? new String(sex + " ").charAt(0) : new String(" ").charAt(0),
								rs.getString("zip"),
								rs.getString("country"),
								rs.getString("state"),
								rs.getString("city"),
								rs.getString("occupation"),
								rs.getString("address"),
								rs.getString("saying"),
								TimeUtils.parseDateTime(TimeUtils.formatDate(rs.getTimestamp("birthday"))),
								TimeUtils.parseDateTime(TimeUtils.formatDate(rs.getTimestamp("created_at"))),
								TimeUtils.parseDateTime(TimeUtils.formatDate(rs.getTimestamp("updated_at"))),
								rs.getBoolean("banned"));
						return puluoUser;
					}
				});
		return entities;
	}
}
