package com.puluo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.puluo.dao.PuluoCouponDao;
import com.puluo.entity.PuluoCoupon;
import com.puluo.entity.impl.PuluoCouponImpl;
import com.puluo.enumeration.CouponType;
import com.puluo.jdbc.DalTemplate;
import com.puluo.jdbc.SqlReader;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.PuluoDatabaseException;
import com.puluo.util.Strs;

public class PuluoCouponDaoImpl extends DalTemplate implements PuluoCouponDao {

	public static Log log = LogFactory.getLog(PuluoCouponDaoImpl.class);

	@Override
	public boolean createTable() {
		try {
			String createSQL = new StringBuilder().append("create table ")
				.append(super.getFullTableName())
				.append(" (uuid text primary key, ")
				.append("type text not null, ")
				.append("amount double precision, ")
				.append("user_uuid text, ")
				.append("valid boolean default false)")
				.toString();
			log.info(createSQL);
			getWriter().execute(createSQL);
			
			String indexSQL1 = new StringBuilder().append("create index " + super.getFullTableName() + "_i_uuid on ")
					.append(super.getFullTableName())
					.append(" (uuid)").toString();
			log.info(indexSQL1);
			getWriter().execute(indexSQL1);
			
			String indexSQL2 = new StringBuilder().append("create index " + super.getFullTableName() + "_i_user_uuid on ")
					.append(super.getFullTableName())
					.append(" (user_uuid)").toString();
			log.info(indexSQL2);
			getWriter().execute(indexSQL2);
			
			return true;
		} catch (Exception e) {
			log.debug(e.getMessage());
			return false;
		}
	}

	@Override
	public PuluoCoupon getByCouponUUID(String uuid) {
		SqlReader reader = getReader();
		StringBuilder sb = new StringBuilder().append("select * from ").append(super.getFullTableName()).append(" where uuid = ?");
		String selectSQL = sb.toString();
		log.info(super.sqlRequestLog(selectSQL, uuid));
		List<PuluoCoupon> entities = reader.query(selectSQL, new Object[] {uuid},new PuluoCouponMapper());
		return verifyUniqueResult(entities);
	}

	@Override
	public List<PuluoCoupon> getByUserUUID(String user_uuid) {
		SqlReader reader = getReader();
		StringBuilder sb = new StringBuilder().append("select * from ").append(super.getFullTableName()).append(" where user_uuid = ?");
		String selectSQL = sb.toString();
		log.info(super.sqlRequestLog(selectSQL, user_uuid));
		List<PuluoCoupon> entities = reader.query(selectSQL, new Object[] {user_uuid},new PuluoCouponMapper());
		return entities;
	}

	@Override
	public PuluoCoupon getByCouponUUID(String uuid, boolean is_valid) {
		SqlReader reader = getReader();
		StringBuilder sb = new StringBuilder().append("select * from ")
				.append(super.getFullTableName())
				.append(" where uuid = ? and valid = ?");
		String selectSQL = sb.toString();
		log.info(super.sqlRequestLog(selectSQL, uuid, is_valid));
		List<PuluoCoupon> entities = reader.query(selectSQL, new Object[] {uuid, is_valid},new PuluoCouponMapper());
		return verifyUniqueResult(entities);
	}

	@Override
	public List<PuluoCoupon> getByUserUUID(String user_uuid, boolean is_valid) {
		SqlReader reader = getReader();
		StringBuilder sb = new StringBuilder().append("select * from ")
				.append(super.getFullTableName())
				.append(" where user_uuid = ? and valid = ?");
		String selectSQL = sb.toString();
		log.info(super.sqlRequestLog(selectSQL, user_uuid, is_valid));
		List<PuluoCoupon> entities = reader.query(selectSQL, new Object[] {user_uuid, is_valid},new PuluoCouponMapper());
		return entities;
	}

	@Override
	public boolean insertCoupon(PuluoCoupon coupon) {
		try {
			SqlReader reader = getReader();
			String querySQL = new StringBuilder().append("select count(1) from ")
					.append(super.getFullTableName())
					.append(" where uuid = '" + coupon.uuid() + "'")
					.toString();
			int accountCnt = reader.queryForInt(querySQL);
			log.info(Strs.join("SQL:",querySQL));
			if (accountCnt==0) {
				StringBuilder sb = new StringBuilder().append("insert into ")
						.append(super.getFullTableName())
						.append(" (uuid, type, amount, user_uuid, valid)")
						.append(" values ('" + coupon.uuid() + "', '" + coupon.couponType().name() + "', "
								+ coupon.amount() + ", '" + coupon.ownerUUID() + "', " + coupon.isValid() + ")");
				String insertSQL = sb.toString();
				log.info(Strs.join("SQL:",insertSQL));
				getWriter().update(insertSQL);
				return true;
			} else {
				throw new PuluoDatabaseException("已存在对应的coupon，不能插入！");
			}
		} catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}
	}

	@Override
	public boolean updateCoupon(PuluoCoupon coupon) {
		try {
			SqlReader reader = getReader();
			String querySQL = new StringBuilder().append("select count(1) from ")
					.append(super.getFullTableName())
					.append(" where uuid = '" + coupon.uuid() + "'")
					.toString();
			int accountCnt = reader.queryForInt(querySQL);
			log.info(Strs.join("SQL:",querySQL));
			if (accountCnt==1) {
				StringBuilder sb = new StringBuilder().append("update ")
						.append(super.getFullTableName())
						.append(" set type = '" + coupon.couponType().name())
						.append("', amount = " + coupon.amount())
						.append(", user_uuid = '" + coupon.ownerUUID())
						.append("', valid = " + coupon.isValid())
						.append(" where uuid = '" + coupon.uuid() + "'");
				String updateSQL = sb.toString();
				log.info(Strs.join("SQL:",updateSQL));
				getWriter().update(updateSQL);
				return true;
			} else {
				throw new PuluoDatabaseException("不存在对应的coupon，不能修改！");
			}
		} catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}
	}
	
	class PuluoCouponMapper implements RowMapper<PuluoCoupon> {
		@Override
		public PuluoCoupon mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			PuluoCoupon coupon = new PuluoCouponImpl(
					rs.getString("uuid"),
					CouponType.valueOf(rs.getString("type")),
					rs.getDouble("amount"),
					rs.getString("user_uuid"),
					rs.getBoolean("valid"));
			return coupon;
		}
	}
	
	public boolean deleteByCouponUUID(String uuid) {
		return super.deleteByUniqueKey("uuid", uuid);
	}
}
