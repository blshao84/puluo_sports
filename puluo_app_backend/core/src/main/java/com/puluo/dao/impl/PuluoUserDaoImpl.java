package com.puluo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;

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
				.append(" (idUser uuid not null, ")
				.append("type varchar(10), ")
				.append("username varchar(18), ")
				.append("iconurl varchar(50), ")
				.append("name varchar(18), ")
				.append("phone varchar(10), ")
				.append("email varchar(50), ")
				.append("birthday date, ")
				.append("sex char(1), ")
				.append("zip varchar(10), ")
				.append("country varchar(20), ")
				.append("state varchar(20), ")
				.append("city varchar(20), ")
				.append("occupation varchar(20), ")
				.append("address varchar(100), ")
				.append("interests varchar(100)[], ")
				.append("description varchar(100), ")
				.append("friends varchar(100)[], ")
				.append("privacy varchar(100), ")
				.append("status integer, ")
				.append("lastLogin timestamp, ")
				.append("lastDuration long, ")
				.append("create timestamp, ")
				.append("update timestamp, ")
				.append("firstName varchar(100), ")
				.append("lastName varchar(100), ")
				.append("thumbnail varchar(100), ")
				.append("largeImage varchar(100), ")
				.append("mobile varchar(100) not null, ")
				.append("autoAddFriend boolean, ")
				.append("allowStrangerViewTimeline boolean, ")
				.append("allowSearched boolean, ")
				.append("saying varchar(100), ")
				.append("likes integer, ")
				.append("banned boolean, ")
				.append("following integer, ")
				.append("isCoach boolean, ")
				.append("password varchar(20) not null)")
				.toString();
			getWriter().execute(createSQL);
		} catch (DataAccessException e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean save(String mobile, String password) {
		// TODO Auto-generated method stub
		return false;
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
				.append(super.getFullTableName()).append(" where iduser = ?")
				.toString();
		List<PuluoUser> entities = reader.query(selectSQL, new Object[] {uuid},
				new RowMapper<PuluoUser>() {
					public PuluoUser mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						PuluoUserImpl puluoUser = new PuluoUserImpl();
						puluoUser.setAddress(rs.getString("address"));
						puluoUser.setBirthday(new DateTime(rs.getDate("birthday").getTime()));
						puluoUser.setCity(rs.getString("city"));
						puluoUser.setCountry(rs.getString("country"));
						puluoUser.setDescription(rs.getString("description"));
						puluoUser.setEmail(rs.getString("email"));
//						puluoUser.setFriends(rs.getArray("friends"));
						puluoUser.setIconurl(rs.getString("iconurl"));
						puluoUser.setIduser(rs.getString("iduser"));
//						puluoUser.setInterests(interests);
						puluoUser.setName(rs.getString("name"));
						puluoUser.setOccupation(rs.getString("occupation"));
						puluoUser.setPhone(rs.getString("phone"));
						puluoUser.setPrivacy(rs.getString("privacy"));
//						puluoUser.setSex(sex);
						puluoUser.setState(rs.getString("state"));
						puluoUser.setStatus(rs.getInt("status"));
						puluoUser.setType(rs.getString("type"));
						puluoUser.setUsername(rs.getString("username"));
						puluoUser.setZip(rs.getString("zip"));
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
}
