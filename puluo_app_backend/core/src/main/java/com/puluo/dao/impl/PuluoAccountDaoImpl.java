package com.puluo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.puluo.dao.PuluoAccountDao;
import com.puluo.entity.PuluoAccount;
import com.puluo.entity.PuluoCoupon;
import com.puluo.entity.impl.PuluoAccountImpl;
import com.puluo.jdbc.DalTemplate;
import com.puluo.jdbc.SqlReader;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.PuluoDatabaseException;
import com.puluo.util.Strs;

public class PuluoAccountDaoImpl extends DalTemplate implements PuluoAccountDao {

	public static Log log = LogFactory.getLog(PuluoAccountDaoImpl.class);

	@Override
	public boolean createTable() {
		try {
			String createSQL = new StringBuilder().append("create table ")
				.append(super.getFullTableName())
				.append(" (uuid text primary key, ")
				.append("user_uuid text unique, ")
				.append("balance double precision, ")
				.append("credit int, ")
				.append("coupon_uuids text[])")
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
	public PuluoAccount getByAccountUUID(String uuid) {
		SqlReader reader = getReader();
		StringBuilder sb = new StringBuilder().append("select * from ").append(super.getFullTableName()).append(" where uuid = ?");
		String selectSQL = sb.toString();
		log.info(super.sqlRequestLog(selectSQL, uuid));
		List<PuluoAccount> entities = reader.query(selectSQL, new Object[] {uuid},new PuluoAccountMapper());
		return verifyUniqueResult(entities);
	}

	@Override
	public PuluoAccount getByUserUUID(String user_uuid) {
		SqlReader reader = getReader();
		StringBuilder sb = new StringBuilder().append("select * from ").append(super.getFullTableName()).append(" where user_uuid = ?");
		String selectSQL = sb.toString();
		log.info(super.sqlRequestLog(selectSQL, user_uuid));
		List<PuluoAccount> entities = reader.query(selectSQL, new Object[] {user_uuid},new PuluoAccountMapper());
		return verifyUniqueResult(entities);
	}

	@Override
	public boolean insertAccount(PuluoAccount account) {
		try {
			SqlReader reader = getReader();
			String querySQL = new StringBuilder().append("select count(1) from ")
					.append(super.getFullTableName())
					.append(" where uuid = '" + account.accountUUID() + "' or user_uuid = '" + account.ownerUUID() + "'")
					.toString();
			int accountCnt = reader.queryForInt(querySQL);
			log.info(Strs.join("SQL:",querySQL));
			if (accountCnt==0) {
				StringBuilder sb = new StringBuilder().append("insert into ")
						.append(super.getFullTableName())
						.append(" (uuid, user_uuid, balance, credit, coupon_uuids)")
						.append(" values ('" + account.accountUUID() 
								+ "', '" + account.ownerUUID() 
								+ "', " + account.balance() 
								+ ", " + account.credit() + ", ");
				if (account.coupons()!=null && account.coupons().size()>0) {
					sb.append("text[");
					boolean isFirst = true;
					for (PuluoCoupon coupon: account.coupons()) {
						if (isFirst) {
							isFirst = false;
						} else {
							sb.append(", ");
						}
						sb.append("'" + coupon.uuid() + "'");
					}
					sb.append("]");
				} else {
					sb.append("null");
				}
				sb.append(")");
				String insertSQL = sb.toString();
				log.info(Strs.join("SQL:",insertSQL));
				getWriter().update(insertSQL);
				return true;
			} else {
				throw new PuluoDatabaseException("已存在对应的account，不能插入！");
			}
		} catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}
	}

	@Override
	public boolean updateBalance(PuluoAccount account, Double newBalance) {
		try {
			SqlReader reader = getReader();
			String querySQL = new StringBuilder().append("select count(1) from ")
					.append(super.getFullTableName())
					.append(" where uuid = '" + account.accountUUID() + "' and user_uuid = '" + account.ownerUUID() + "'")
					.toString();
			int accountCnt = reader.queryForInt(querySQL);
			log.info(Strs.join("SQL:",querySQL));
			if (accountCnt==1) {
				StringBuilder sb = new StringBuilder().append("update ")
						.append(super.getFullTableName())
						.append(" set balance = " + newBalance)
						.append(" where uuid = '" + account.accountUUID() + "'");
				String updateSQL = sb.toString();
				log.info(Strs.join("SQL:",updateSQL));
				getWriter().update(updateSQL);
				return true;
			} else {
				throw new PuluoDatabaseException("不存在对应的account，不能修改！");
			}
		} catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}
	}

	@Override
	public boolean updateCredit(PuluoAccount account, Integer newCredit) {
		try {
			SqlReader reader = getReader();
			String querySQL = new StringBuilder().append("select count(1) from ")
					.append(super.getFullTableName())
					.append(" where uuid = '" + account.accountUUID() + "' and user_uuid = '" + account.ownerUUID() + "'")
					.toString();
			int accountCnt = reader.queryForInt(querySQL);
			log.info(Strs.join("SQL:",querySQL));
			if (accountCnt==1) {
				StringBuilder sb = new StringBuilder().append("update ")
						.append(super.getFullTableName())
						.append(" set credit = " + newCredit)
						.append(" where uuid = '" + account.accountUUID() + "'");
				String updateSQL = sb.toString();
				log.info(Strs.join("SQL:",updateSQL));
				getWriter().update(updateSQL);
				return true;
			} else {
				throw new PuluoDatabaseException("不存在对应的account，不能修改！");
			}
		} catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}
	}

	@Override
	public boolean addCoupon(PuluoAccount account, String newCoupon) {
		try {
			SqlReader reader = getReader();
			String querySQL = new StringBuilder().append("select count(1) from ")
					.append(super.getFullTableName())
					.append(" where uuid = '" + account.accountUUID() + "' and user_uuid = '" + account.ownerUUID() + "'")
					.toString();
			int accountCnt = reader.queryForInt(querySQL);
			log.info(Strs.join("SQL:",querySQL));
			if (accountCnt==1) {
				StringBuilder sb = new StringBuilder().append("update ")
						.append(super.getFullTableName())
						.append(" set coupon_uuids = array_append(coupon_uuids, '" + newCoupon + "')")
						.append(" where uuid = '" + account.accountUUID() + "'");
				String updateSQL = sb.toString();
				log.info(Strs.join("SQL:",updateSQL));
				getWriter().update(updateSQL);
				return true;
			} else {
				throw new PuluoDatabaseException("不存在对应的account，不能修改！");
			}
		} catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}
	}

	@Override
	public boolean removeCoupon(PuluoAccount account, String newCoupon) {
		try {
			SqlReader reader = getReader();
			String querySQL = new StringBuilder().append("select count(1) from ")
					.append(super.getFullTableName())
					.append(" where uuid = '" + account.accountUUID() + "' and user_uuid = '" + account.ownerUUID() + "'")
					.toString();
			int accountCnt = reader.queryForInt(querySQL);
			log.info(Strs.join("SQL:",querySQL));
			if (accountCnt==1) {
				StringBuilder sb = new StringBuilder().append("update ")
						.append(super.getFullTableName())
						.append(" set coupon_uuids = array_remove(coupon_uuids, '" + newCoupon + "')")
						.append(" where uuid = '" + account.accountUUID() + "'");
				String updateSQL = sb.toString();
				log.info(Strs.join("SQL:",updateSQL));
				getWriter().update(updateSQL);
				return true;
			} else {
				throw new PuluoDatabaseException("不存在对应的account，不能修改！");
			}
		} catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}
	}
	
	class PuluoAccountMapper implements RowMapper<PuluoAccount> {
		@Override
		public PuluoAccount mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			List<String> coupon_uuids = new ArrayList<String>();
			if (rs.getArray("coupon_uuids")!=null) {
				for (String coupon_uuid: (String [])rs.getArray("coupon_uuids").getArray()) {
					coupon_uuids.add(coupon_uuid);
				}
			}
			PuluoAccount account = new PuluoAccountImpl(
					rs.getString("uuid"),
					rs.getString("user_uuid"),
					rs.getDouble("balance"),
					rs.getInt("credit"),
					coupon_uuids);
			return account;
		}
	}

	public boolean deleteByAccountUUID(String uuid) {
		return super.deleteByUniqueKey("uuid", uuid);
	}
}
