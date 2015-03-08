package com.puluo.dao.impl;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;

import scala.actors.threadpool.Arrays;

import com.puluo.dao.PuluoUserDao;
import com.puluo.entity.PuluoUser;
import com.puluo.entity.impl.PuluoUserImpl;
import com.puluo.jdbc.DalTemplate;
import com.puluo.jdbc.SqlReader;
import com.puluo.util.PuluoException;


public class PuluoUserDaoImpl extends DalTemplate implements PuluoUserDao {

	@Override
	public boolean createTable() {
		try {
			String createSQL = new StringBuilder().append("create table ")
				.append(super.getFullTableName())
				.append(" (user_uuid varchar(100), ")
//				.append("type varchar(10), ")
//				.append("username varchar(18), ")
//				.append("iconurl varchar(50), ")
//				.append("name varchar(18), ")
//				.append("phone varchar(10), ")
//				.append("email varchar(50), ")
//				.append("birthday date, ")
//				.append("sex char(1), ")
//				.append("zip varchar(10), ")
//				.append("country varchar(20), ")
//				.append("state varchar(20), ")
//				.append("city varchar(20), ")
//				.append("occupation varchar(20), ")
//				.append("address varchar(100), ")
//				.append("interests varchar(100)[], ")
//				.append("description varchar(100), ")
//				.append("friends varchar(100)[], ")
//				.append("privacy varchar(100), ")
//				.append("status integer, ")
//				.append("lastLogin timestamp, ")
//				.append("lastDuration long, ")
//				.append("create timestamp, ")
//				.append("update timestamp, ")
//				.append("firstName varchar(100), ")
//				.append("lastName varchar(100), ")
//				.append("thumbnail varchar(100), ")
//				.append("largeImage varchar(100), ")
//				.append("mobile varchar(100) not null, ")
//				.append("autoAddFriend boolean, ")
//				.append("allowStrangerViewTimeline boolean, ")
//				.append("allowSearched boolean, ")
//				.append("saying varchar(100), ")
//				.append("likes integer, ")
//				.append("banned boolean, ")
//				.append("following integer, ")
//				.append("isCoach boolean, ")
				.append("user_password varchar(100) not null, ")
				.append("user_mobile varchar(15) not null, ")
				.append("user_interests text[])")
				.toString();
			getWriter().execute(createSQL);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean save(String mobile, String password) {
		try {
			String insertSQL = new StringBuilder().append("insert into ")
					.append(super.getFullTableName())
					.append(" (user_mobile, user_password)")
					.append(" values ('" + mobile + "', '" + password + "')")
					.toString();
			getWriter().update(insertSQL);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean updatePassword(PuluoUser user, String newPassword) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PuluoUser getByMobile(String mobile) {
		// TODO Auto-generated method stub
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
					public PuluoUser mapRow(ResultSet rs, int rowNum)
							throws SQLException {
//						PuluoUserImpl puluoUser = new PuluoUserImpl();
//						puluoUser.setAddress(rs.getString("address"));
//						puluoUser.setBirthday(new DateTime(rs.getDate("birthday").getTime()));
//						puluoUser.setCity(rs.getString("city"));
//						puluoUser.setCountry(rs.getString("country"));
//						puluoUser.setDescription(rs.getString("description"));
//						puluoUser.setEmail(rs.getString("email"));
////						puluoUser.setFriends(rs.getArray("friends"));
//						puluoUser.setIconurl(rs.getString("iconurl"));
//						puluoUser.setIduser(rs.getString("iduser"));
////						puluoUser.setInterests(interests);
//						puluoUser.setName(rs.getString("name"));
//						puluoUser.setOccupation(rs.getString("occupation"));
//						puluoUser.setPhone(rs.getString("phone"));
//						puluoUser.setPrivacy(rs.getString("privacy"));
////						puluoUser.setSex(sex);
//						puluoUser.setState(rs.getString("state"));
//						puluoUser.setStatus(rs.getInt("status"));
//						puluoUser.setType(rs.getString("type"));
//						puluoUser.setUsername(rs.getString("username"));
//						puluoUser.setZip(rs.getString("zip"));
						String[] array = rs.getArray("user_interests")!=null ? (String[])rs.getArray("user_interests").getArray() : new String[]{};
						PuluoUserImpl puluoUser = new PuluoUserImpl(rs.getString("user_uuid"),
								rs.getString("user_mobile"),
								array,
								rs.getString("user_password"));
						return puluoUser;
					}
				});
		if (entities.size() == 1)
			return entities.get(0);
		else if (entities.size() > 1)
			throw new PuluoException("");
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
		PuluoUser puluoUser = DaoApi.getInstance().userDao().getByUUID(null);
		System.out.println(puluoUser!=null ? puluoUser.mobile() : null);
		System.out.println("DONE.");
	}
}
