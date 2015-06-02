package com.puluo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.puluo.dao.PuluoDSI;
import com.puluo.dao.PuluoOrderEventDao;
import com.puluo.entity.payment.OrderEvent;
import com.puluo.entity.payment.impl.OrderEventImpl;
import com.puluo.enumeration.OrderEventType;
import com.puluo.jdbc.DalTemplate;
import com.puluo.jdbc.SqlReader;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.Strs;
import com.puluo.util.TimeUtils;

public class PuluoOrderEventDaoImpl extends DalTemplate implements
		PuluoOrderEventDao {

	public static Log log = LogFactory.getLog(PuluoOrderEventDaoImpl.class);

	@Override
	public boolean saveOrderEvent(OrderEvent event) {
		return saveOrderEvent(event, DaoApi.getInstance());
	}
	
	public boolean saveOrderEvent(OrderEvent event, PuluoDSI dsi) {
		try {
			String updateSQL = new StringBuilder()
					.append("insert into ")
					.append(super.getFullTableName())
					.append(" (uuid, created_at, order_uuid, type)")
					.append("values ('" + event.eventUUID() + "', ")
					.append("'" + TimeUtils.formatDate(event.createdAt()) + "', ")
					.append("'" + event.orderUUID() + "', ")
					.append("'" + event.eventType().name() + "')").toString();
			log.info(Strs.join("SQL:",updateSQL));
			getWriter().update(updateSQL);
			return true;
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
	}

	@Override
	public List<OrderEvent> getOrderEventsByOrderUUID(String orderUUID) {
		try {
			SqlReader reader = getReader();
			String selectSQL = new StringBuilder()
					.append("select * from ")
					.append(super.getFullTableName())
					.append(" where order_uuid = ?").toString();
			log.info(super.sqlRequestLog(selectSQL,orderUUID));
			List<OrderEvent> entities = reader.query(
					selectSQL.toString(), new Object[] {orderUUID}, new OrderEventMapper());
			return entities;
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	@Override
	public boolean createTable() {
		try {
			String createSQL = new StringBuilder().append("create table ")
					.append(super.getFullTableName())
					.append(" (id serial primary key, ")
					.append("uuid text unique, ")
					.append("created_at timestamp, ")
					.append("order_uuid text, ")
					.append("type text)").toString();
			log.info(createSQL);
			getWriter().execute(createSQL);
						
			String indexSQL2 = new StringBuilder().append("create index " + super.getFullTableName() + "_i_uuid on ")
					.append(super.getFullTableName())
					.append(" (uuid)").toString();
			log.info(indexSQL2);
			getWriter().execute(indexSQL2);
			
			String indexSQL3 = new StringBuilder().append("create index " + super.getFullTableName() + "_i_order_uuid on ")
					.append(super.getFullTableName())
					.append(" (order_uuid)").toString();
			log.info(indexSQL3);
			getWriter().execute(indexSQL3);
			
			return true;
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
	}
	
	class OrderEventMapper implements RowMapper<OrderEvent> {
		@Override
		public OrderEvent mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			OrderEvent orderEvent = new OrderEventImpl(
					rs.getString("uuid"),
					TimeUtils.parseDateTime(TimeUtils.formatDate(rs.getTimestamp("created_at"))),
					rs.getString("order_uuid"),
					OrderEventType.valueOf(rs.getString("type")));
			return orderEvent;
		}
	}
	
	public boolean deleteByOrderUUID(String orderUUID) {
		return super.deleteByUniqueKey("order_uuid", orderUUID);
	}

}
