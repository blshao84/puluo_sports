package com.puluo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.puluo.dao.PuluoPaymentDao;
import com.puluo.entity.PuluoPaymentOrder;
import com.puluo.entity.impl.PuluoPaymentOrderImpl;
import com.puluo.entity.payment.impl.PuluoOrderStatus;
import com.puluo.jdbc.DalTemplate;
import com.puluo.jdbc.SqlReader;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.PuluoDatabaseException;
import com.puluo.util.Strs;
import com.puluo.util.TimeUtils;

public class PuluoPaymentDaoImpl extends DalTemplate implements PuluoPaymentDao{

	public static Log log = LogFactory.getLog(PuluoPaymentDaoImpl.class);

	@Override
	public PuluoPaymentOrder getOrderByUUID(String orderUUID) {
		return getOrderByKey("order_uuid", orderUUID);
	}

	@Override
	public PuluoPaymentOrder getOrderByEvent(String eventUUID) {
		return getOrderByKey("event_id", eventUUID);
	}

	@Override
	public boolean updateOrderStatus(PuluoPaymentOrder order,
			PuluoOrderStatus nextStatus) {
		return updateOrderForKey(order.orderUUID(), "status", nextStatus.name());
	}

	@Override
	public boolean upsertOrder(PuluoPaymentOrder order) {
		if (getOrderByUUID(order.orderUUID())!=null) {
			return updateOrder(order);
		} else {
			return saveOrder(order);
		}
	}

	@Override
	public PuluoPaymentOrder getOrderByNumericID(long orderNumericID) {
		return getOrderByKey("order_num_id", orderNumericID);
	}

	@Override
	public boolean updateOrderPaymentInfo(PuluoPaymentOrder order,
			String paymentRef) {
		return updateOrderForKey(order.orderUUID(), "payment_id", paymentRef);
	}

	@Override
	public boolean createTable() {
		try {
			String createSQL = new StringBuilder().append("create table ")
					.append(super.getFullTableName())
					.append(" (order_num_id serial primary key, ")
					.append("order_uuid text unique, ")
					.append("payment_id text, ")
					.append("amount double precision, ")
					.append("payment_time timestamp, ")
					.append("user_id text, ")
					.append("event_id text, ")
					.append("status text)").toString();
			log.info(createSQL);
			getWriter().execute(createSQL);
			// TODO create index
			return true;
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
	}
	
	class PuluoPaymentOrderMapper implements RowMapper<PuluoPaymentOrder> {
		@Override
		public PuluoPaymentOrder mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			PuluoPaymentOrder paymentOrder = new PuluoPaymentOrderImpl(
					rs.getLong("order_num_id"),
					rs.getString("order_uuid"),
					rs.getString("payment_id"),
					rs.getDouble("amount"),
					TimeUtils.parseDateTime(TimeUtils.formatDate(rs.getTimestamp("payment_time"))),
					rs.getString("user_id"),
					rs.getString("event_id"),
					PuluoOrderStatus.valueOf(rs.getString("status")));
			return paymentOrder;
		}
	}
	
	public boolean deleteByOrderUUID(String orderUUID) {
		return super.deleteByUniqueKey("order_uuid", orderUUID);
	}

	private PuluoPaymentOrder getOrderByKey(String key, Object value) {
		try {
			SqlReader reader = getReader();
			String selectSQL = new StringBuilder()
					.append("select * from ")
					.append(super.getFullTableName())
					.append(" where " + key + " = ?").toString();
			log.info(super.sqlRequestLog(selectSQL,value));
			List<PuluoPaymentOrder> entities = reader.query(
					selectSQL.toString(), new Object[] {value}, new PuluoPaymentOrderMapper());
			return super.verifyUniqueResult(entities);
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}
	
	private boolean saveOrder(PuluoPaymentOrder order) {
		try {
			SqlReader reader = getReader();
			String querySQL = new StringBuilder().append("select count(1) from ")
					.append(super.getFullTableName())
					.append(" where order_uuid = '" + order.orderUUID() + "'")
					.toString();
			log.info(sqlRequestLog(querySQL, order.orderUUID()));
			int resCnt = reader.queryForInt(querySQL);
			String updateSQL;
			if (resCnt==0) {
				updateSQL = new StringBuilder().append("insert into ")
						.append(super.getFullTableName())
						.append(" (order_uuid, payment_id, amount, payment_time, user_id, event_id, status)")
						.append("values ('" + order.orderUUID() + "', ")
						.append(Strs.isEmpty(order.paymentId()) ? "null" : "'" + order.paymentId() + "'").append(", ")
						.append(order.amount()).append(", ")
						.append(Strs.isEmpty(TimeUtils.formatDate(order.paymentTime())) ? "null" : "'" + TimeUtils.formatDate(order.paymentTime()) + "'").append(", ")
						.append(Strs.isEmpty(order.userId()) ? "null" : "'" + order.userId() + "'").append(", ")
						.append(Strs.isEmpty(order.eventId()) ? "null" : "'" + order.eventId() + "'").append(", ")
						.append("'" + order.status().name() + "'").append(")")
						.toString();
				log.info(Strs.join("SQL: ", updateSQL));
				getWriter().update(updateSQL);
				return true;
			} else {
				throw new PuluoDatabaseException("order_uuid为'" + order.orderUUID() + "'已存在不能插入数据！");
			}
		} catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}
	}
	
	private boolean updateOrder(PuluoPaymentOrder order) {
		try {
			SqlReader reader = getReader();
			String querySQL = new StringBuilder().append("select count(1) from ")
					.append(super.getFullTableName())
					.append(" where order_uuid = '" + order.orderUUID() + "'")
					.toString();
			log.info(sqlRequestLog(querySQL, order.orderUUID()));
			int resCnt = reader.queryForInt(querySQL);
			StringBuilder updateSQL;
			if (resCnt>0) {
				updateSQL = new StringBuilder().append("update ")
						.append(super.getFullTableName()).append(" set");
				if (!Strs.isEmpty(order.paymentId())) {
					updateSQL.append(" payment_id = '" + order.paymentId() + "',");
				}
				updateSQL.append(" amount = " + order.amount() + ",");
				if (!Strs.isEmpty(TimeUtils.formatDate(order.paymentTime()))) {
					updateSQL.append(" payment_time = '" + TimeUtils.formatDate(order.paymentTime()) + "',");
				}
				if (!Strs.isEmpty(order.userId())) {
					updateSQL.append(" user_id = '" + order.userId() + "',");
				}
				if (!Strs.isEmpty(order.eventId())) {
					updateSQL.append(" event_id = '" + order.eventId() + "',");
				}
				updateSQL.append(" status = '" + order.status().name() + "'");
				updateSQL.append(" where order_uuid = '" + order.orderUUID() + "'");
				log.info(Strs.join("SQL: ", updateSQL.toString()));
				getWriter().update(updateSQL.toString());
				return true;
			} else {
				throw new PuluoDatabaseException("order_uuid为'" + order.orderUUID() + "'不存在不能更新数据！");
			}
		} catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}
	}

	private boolean updateOrderForKey(String order_uuid, String key, String value) {
		try {
			SqlReader reader = getReader();
			String querySQL = new StringBuilder().append("select count(1) from ")
					.append(super.getFullTableName())
					.append(" where order_uuid = '" + order_uuid + "'")
					.toString();
			log.info(sqlRequestLog(querySQL, order_uuid));
			int resCnt = reader.queryForInt(querySQL);
			StringBuilder updateSQL;
			if (resCnt>0) {
				updateSQL = new StringBuilder().append("update ")
						.append(super.getFullTableName()).append(" set");
				updateSQL.append(" " + key + " = '" + value + "'");
				updateSQL.append(" where order_uuid = '" + order_uuid + "'");
				log.info(Strs.join("SQL: ", updateSQL.toString()));
				getWriter().update(updateSQL.toString());
				return true;
			} else {
				throw new PuluoDatabaseException("order_uuid为'" + order_uuid + "'不存在不能更新数据！");
			}
		} catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}
	}
}
