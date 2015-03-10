package com.puluo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
import com.puluo.util.TimeUtils;


public class PuluoUserDaoImpl extends DalTemplate implements PuluoUserDao {
	
	public static Log log = LogFactory.getLog(PuluoUserDaoImpl.class);

	@Override
	public boolean createTable() {
		try {
//			String dropSQL = new StringBuilder().append("drop table ").append(super.getFullTableName()).toString();
//			getWriter().execute(dropSQL);
			String createSQL = new StringBuilder().append("create table ")
				.append(super.getFullTableName())
				.append(" (id serial primary key, ")
				.append("user_uuid text unique, ")
				.append("mobile text unique, ")
				.append("interests text[], ")
				.append("user_password text not null, ")
				.append("first_name text, ")
				.append("last_name text, ")
				.append("thumbnail text, ")
				.append("large_image text, ")
				.append("user_type text, ")
				.append("email text, ")
				.append("sex varchar(1), ")
				.append("zip text, ")
				.append("country text, ")
				.append("state text, ")
				.append("city text, ")
				.append("occupation text, ")
				.append("address text, ")
				.append("saying text, ")
				.append("birthday text, ")
				.append("created_at timestamp, ")
				.append("updated_at timestamp, ")
				.append("banned boolean default true)")
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
	public boolean save(String mobile, String password) {
		try {
			String uuid =  UUID.randomUUID().toString();
			String insertSQL = new StringBuilder().append("insert into ")
					.append(super.getFullTableName())
					.append(" (user_uuid, mobile, user_password, created_at)")
					.append(" values ('" + uuid + "', '" + mobile + "', '" + password + "', now()::timestamp)")
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
	public boolean updatePassword(PuluoUser user, String newPassword) {
		try {
			String uuid =  user.userUUID();
			String updateSQL = new StringBuilder().append("update ")
					.append(super.getFullTableName())
					.append(" set user_password = '" + newPassword + "',")
					.append(" updated_at = now()::timestamp")
					.append(" where user_uuid = '" + uuid + "'")
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
	public PuluoUser getByMobile(String mobile) {
		SqlReader reader = getReader();
		String selectSQL = new StringBuilder().append("select * from ")
				.append(super.getFullTableName()).append(" where mobile = ?")
				.toString();
		List<PuluoUser> entities = reader.query(selectSQL, new Object[] {mobile},
				new RowMapper<PuluoUser>() {
					@Override
					public PuluoUser mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						String[] array = rs.getArray("interests")!=null ? (String[])rs.getArray("interests").getArray() : new String[]{};
						String user_type = rs.getString("user_type");
						if (user_type==null) {
							user_type = PuluoUserType.User.name();
							log.info("mobile(" + rs.getString("mobile") + "): user_type为null, 默认设置为PuluoUserType.User!");
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
			throw new PuluoDatabaseException("通过mobile查到多个用户！");
		else
			return null;
	}

	@Override
	public PuluoUser getByUUID(String uuid) {
		SqlReader reader = getReader();
		String selectSQL = new StringBuilder().append("select * from ")
				.append(super.getFullTableName()).append(" where user_uuid = ?")
				.toString();
		List<PuluoUser> entities = reader.query(selectSQL, new Object[] {uuid},
				new RowMapper<PuluoUser>() {
					@Override
					public PuluoUser mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						String[] array = rs.getArray("interests")!=null ? (String[])rs.getArray("interests").getArray() : new String[]{};
						String user_type = rs.getString("user_type");
						if (user_type==null) {
							user_type = PuluoUserType.User.name();
							log.info("uuid(" +  rs.getString("user_uuid") + "): user_type为null, 默认设置为PuluoUserType.User!");
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
			String uuid =  curuser.userUUID();
			StringBuilder updateSQL = new StringBuilder().append("update ")
					.append(super.getFullTableName()).append(" set ");
			if (first_name!=null) {
				updateSQL.append("first_name = '" + first_name + "', ");
			}
			if (last_name!=null) {
				updateSQL.append("last_name = '" + last_name + "', ");
			}
			if (thumbnail!=null) {
				updateSQL.append("thumbnail = '" + thumbnail + "', ");
			}
			if (large_image!=null) {
				updateSQL.append("large_image = '" + large_image + "', ");
			}
			if (saying!=null) {
				updateSQL.append("saying = '" + saying + "', ");
			}
			if (email!=null) {
				updateSQL.append("email = '" + email + "', ");
			}
			if (sex!=null) {
				updateSQL.append("sex = '" + sex + "', ");
			}
			if (birthday!=null) {
				updateSQL.append("birthday = '" + birthday + "', ");
			}
			if (country!=null) {
				updateSQL.append("country = '" + country + "', ");
			}
			if (state!=null) {
				updateSQL.append("state = '" + state + "', ");
			}
			if (city!=null) {
				updateSQL.append("city = '" + city + "', ");
			}
			if (zip!=null) {
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
	public ArrayList<PuluoUser> findUser(String first_name, String last_name,
			String email, String mobile) {
		SqlReader reader = getReader();
		StringBuilder selectSQL = new StringBuilder().append("select * from ")
				.append(super.getFullTableName()).append(" where ");
		boolean hasAnd = false;
		if (first_name!=null) {
			if (hasAnd) {
				selectSQL.append("and first_name='" + first_name + "' ");
			} else {
				selectSQL.append("first_name='" + first_name + "' ");
			}
			hasAnd = true;
		}
		if (last_name!=null) {
			if (hasAnd) {
				selectSQL.append("and last_name='" + last_name + "' ");
			} else {
				selectSQL.append("last_name='" + last_name + "' ");
			}
			hasAnd = true;
		}
		if (email!=null) {
			if (hasAnd) {
				selectSQL.append("and email='" + email + "' ");
			} else {
				selectSQL.append("email='" + email + "' ");
			}
			hasAnd = true;
		}
		if (mobile!=null) {
			if (hasAnd) {
				selectSQL.append("and mobile='" + mobile + "' ");
			} else {
				selectSQL.append("mobile='" + mobile + "' ");
			}
			hasAnd = true;
		}
		log.info(selectSQL.toString());
		List<PuluoUser> entities = reader.query(selectSQL.toString(), new Object[]{},
				new RowMapper<PuluoUser>() {
					@Override
					public PuluoUser mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						String[] array = rs.getArray("interests")!=null ? (String[])rs.getArray("interests").getArray() : new String[]{};
						String user_type = rs.getString("user_type");
						if (user_type==null) {
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
		return (ArrayList<PuluoUser>) entities;
	}
}
