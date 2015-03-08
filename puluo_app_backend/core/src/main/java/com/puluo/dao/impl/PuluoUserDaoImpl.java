package com.puluo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;

import com.puluo.dao.PuluoUserDao;
import com.puluo.entity.PuluoUser;
import com.puluo.entity.impl.PuluoUserImpl;
import com.puluo.jdbc.DalTemplate;
import com.puluo.jdbc.SqlReader;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.PuluoDatabaseException;


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
				.append("banned boolean)")
				.toString();
			getWriter().execute(createSQL);
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
					.append(" (user_uuid, mobile, user_password)")
					.append(" values ('" + uuid + "', '" + mobile + "', '" + password + "')")
					.toString();
			getWriter().update(insertSQL);
		} catch (Exception e) {
			log.debug(e.getMessage());
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
					.append(" set user_password = '" + newPassword + "' ")
					.append(" where user_uuid = '" + uuid + "')")
					.toString();
			getWriter().update(updateSQL);
		} catch (Exception e) {
			log.debug(e.getMessage());
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
						String[] array = rs.getArray("interests")!=null ? (String[])rs.getArray("user_interests").getArray() : new String[]{};
						PuluoUserImpl puluoUser = new PuluoUserImpl(
								rs.getString("user_uuid"),
								rs.getString("mobile"),
								array,
								rs.getString("user_password"));
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
						String[] array = rs.getArray("interests")!=null ? (String[])rs.getArray("user_interests").getArray() : new String[]{};
						PuluoUserImpl puluoUser = new PuluoUserImpl(
								rs.getString("user_uuid"),
								rs.getString("mobile"),
								array,
								rs.getString("user_password"));
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<PuluoUser> findUser(String first_name, String last_name,
			String email, String mobile) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static void main(String[] args) {
//		DaoApi.getInstance().userDao().createTable();
//		DaoApi.getInstance().userDao().save("17721014665", "shilei");
//		PuluoUser puluoUser = DaoApi.getInstance().userDao().getByUUID("16002924-4a95-4ccb-a66f-ab35d619d53e");
		PuluoUser puluoUser = DaoApi.getInstance().userDao().getByMobile("17721014665");
		System.out.println(puluoUser!=null ? puluoUser.userUUID() : null);
		System.out.println("DONE.");
	}
}
