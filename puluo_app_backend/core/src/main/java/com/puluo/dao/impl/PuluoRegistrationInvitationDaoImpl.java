package com.puluo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.puluo.dao.PuluoRegistrationInvitationDao;
import com.puluo.entity.PuluoRegistrationInvitation;
import com.puluo.entity.impl.PuluoRegistrationInvitationImpl;
import com.puluo.jdbc.DalTemplate;
import com.puluo.jdbc.SqlReader;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.Strs;
import com.puluo.util.TimeUtils;

public class PuluoRegistrationInvitationDaoImpl extends DalTemplate implements
		PuluoRegistrationInvitationDao {
	private static Log log = LogFactory
			.getLog(PuluoRegistrationInvitationDaoImpl.class);

	@Override
	public boolean createTable() {
		try {
			String createSQL = new StringBuilder().append("create table ")
					.append(super.getFullTableName())
					.append(" (id serial primary key, ")
					.append("uuid text unique, ")
					.append("from_user_uuid text, ")
					.append("to_user_uuid text, ").append("coupon_uuid text, ")
					.append("created_at timestamp, ")
					.append("updated_at timestamp); ").toString();
			log.info(createSQL);
			getWriter().execute(createSQL);
			super.createIndex("from_user_uuid");
			super.createIndex("to_user_uuid");
			super.createIndex("coupon_uuid");
			return true;
		} catch (Exception e) {
			log.debug(e.getMessage());
			return false;
		}
	}
	
	public boolean deleteByUUID(String uuid) {
		return super.deleteByUniqueKey("uuid", uuid);
	}
	
	@Override
	public PuluoRegistrationInvitation getByUUID(String uuid) {
		try {
			SqlReader reader = getReader();
			String selectSQL = new StringBuilder().append("select * from ")
					.append(super.getFullTableName()).append(" where uuid = ?")
					.toString();
			log.info(super.sqlRequestLog(selectSQL, uuid));
			List<PuluoRegistrationInvitation> entities = reader.query(
					selectSQL.toString(), new Object[] { uuid },
					new PuluoRegistrationInvitationMapper());
			return verifyUniqueResult(entities);
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	@Override
	public List<PuluoRegistrationInvitation> getUserSentInvitations(
			String fromUUID) {
		SqlReader reader = getReader();
		String selectSQL = new StringBuilder().append("select * from ")
				.append(super.getFullTableName()).append(" where from_user_uuid = ? and coupon_uuid = ''")
				.toString();
		log.info(super.sqlRequestLog(selectSQL, fromUUID));
		List<PuluoRegistrationInvitation> entities = reader.query(
				selectSQL.toString(), new Object[] { fromUUID },
				new PuluoRegistrationInvitationMapper());
		return entities;

	}

	@Override
	public List<PuluoRegistrationInvitation> getUserReceivedInvitations(
			String toUUID) {
			SqlReader reader = getReader();
			String selectSQL = new StringBuilder().append("select * from ")
					.append(super.getFullTableName()).append(" where to_user_uuid = ? and coupon_uuid = ''")
					.toString();
			log.info(super.sqlRequestLog(selectSQL, toUUID));
			List<PuluoRegistrationInvitation> entities = reader.query(
					selectSQL.toString(), new Object[] { toUUID },
					new PuluoRegistrationInvitationMapper());
			return entities;
	}

	@Override
	public boolean updateCoupon(String invitationUUID, String couponUUID) {
		try {
			if (invitationUUID == null || couponUUID == null)
				return false;
			StringBuilder sb = new StringBuilder();
			String updatedSQL = sb.append("update ")
					.append(super.getFullTableName())
					.append(" set coupon_uuid='").append(couponUUID)
					.append("', ").append(" updated_at=now()::timestamp")
					.append(" where uuid='").append(invitationUUID).append("'")
					.toString();
			log.info(String.format("updatedSQL:%s", updatedSQL));
			getWriter().execute(updatedSQL);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean insertInvitation(PuluoRegistrationInvitation invitation) {
		try {
			if (invitation == null)
				return false;
			if (invitation.uuid() == null)
				return false;

			String uuid;
			String fromUUID;
			String toUUID;
			String couponUUID;
			String createdAt = "now()::timestamp";
			String updatedAt = "now()::timestamp";

			uuid = invitation.uuid();
			if (invitation.fromUUID() == null)
				fromUUID = "";
			else
				fromUUID = invitation.fromUUID();
			if (invitation.toUUID() == null)
				toUUID = "";
			else
				toUUID = invitation.toUUID();
			if (invitation.couponUUID() == null)
				couponUUID = "";
			else
				couponUUID = invitation.couponUUID();

			StringBuilder sb = new StringBuilder();
			String insertSQL = sb
					.append("insert into ")
					.append(super.getFullTableName())
					.append(" (uuid,from_user_uuid,to_user_uuid,coupon_uuid,created_at,updated_at) ")
					.append(" values(").append("'").append(uuid).append("','")
					.append(fromUUID).append("','")
					.append(toUUID).append("','")
					.append(couponUUID).append("',").append(createdAt)
					.append(",").append(updatedAt).append(")").toString();

			log.info(Strs.join("SQL:", insertSQL));
			getWriter().update(insertSQL);
			return true;
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
	}

}

class PuluoRegistrationInvitationMapper implements
		RowMapper<PuluoRegistrationInvitation> {
	@Override
	public PuluoRegistrationInvitation mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		PuluoRegistrationInvitationImpl invitation = new PuluoRegistrationInvitationImpl(
				rs.getString("uuid"), rs.getString("from_user_uuid"),
				rs.getString("to_user_uuid"), rs.getString("coupon_uuid"),
				TimeUtils.parseDateTime(TimeUtils.formatDate(rs
						.getTimestamp("created_at"))),
				TimeUtils.parseDateTime(TimeUtils.formatDate(rs
						.getTimestamp("updated_at"))));
		return invitation;
	}
}
