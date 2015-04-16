package com.puluo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.puluo.dao.WechatMediaResourceDao;
import com.puluo.entity.WechatMediaResource;
import com.puluo.entity.impl.WechatMediaResourceImpl;
import com.puluo.jdbc.DalTemplate;
import com.puluo.jdbc.SqlReader;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.Strs;

public class WechatMediaResourceDaoImpl extends DalTemplate implements
		WechatMediaResourceDao {
	public static Log log = LogFactory.getLog(WechatMediaResourceDaoImpl.class);

	@Override
	public boolean createTable() {
		try {
			String createSQL = new StringBuilder().append("create table ")
					.append(super.getFullTableName())
					.append(" (id serial primary key, ")
					.append("media_news_id text, ")
					.append("media_title text, ")
					.append("media_id text unique, ")
					.append("media_name text not null, ")
					.append("media_type text, ").append("media_link text)")
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
	public boolean saveMediaResource(String mediaID, String mediaName,
			String mediaType, String mediaLink, String mediaItemTitle,
			String medianNewsID) {
		try {
			String insertSQL = new StringBuilder()
					.append("insert into ")
					.append(super.getFullTableName())
					.append(" (media_id, media_name, media_type,media_link,media_title,media_news_id)")
					.append(" values ('" + mediaID + "', '" + mediaName
							+ "', '" + mediaType + "', '" + mediaLink + "', '"
							+ mediaItemTitle + "', '" + medianNewsID + "')")
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
	public WechatMediaResource getResourceByMediaID(String mediaID) {
		SqlReader reader = getReader();
		String selectSQL = new StringBuilder().append("select * from ")
				.append(super.getFullTableName()).append(" where media_id = ?")
				.toString();
		log.info(super.sqlRequestLog(selectSQL, mediaID));
		List<WechatMediaResource> entities = reader.query(selectSQL,
				new Object[] { mediaID }, new WechatMediaResourceMapper());
		return verifyUniqueResult(entities);
	}
	
	@Override
	public List<WechatMediaResource> getResourceByNewsID(String mediaNewID) {
		SqlReader reader = getReader();
		String selectSQL = new StringBuilder().append("select * from ")
				.append(super.getFullTableName()).append(" where media_news_id = ?")
				.toString();
		log.info(super.sqlRequestLog(selectSQL, mediaNewID));
		List<WechatMediaResource> entities = reader.query(selectSQL,
				new Object[] { mediaNewID }, new WechatMediaResourceMapper());
		return entities;
	}

	public boolean obliterateResource(String mediaID) {
		try {
			String updateSQL = new StringBuilder().append("delete from ")
					.append(super.getFullTableName())
					.append(" where media_id = '" + mediaID + "'").toString();
			log.info(Strs.join("SQL:", updateSQL));
			getWriter().update(updateSQL);
		} catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}
		return true;
	}

}

class WechatMediaResourceMapper implements RowMapper<WechatMediaResource> {
	@Override
	public WechatMediaResource mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		WechatMediaResource wechatMedia = new WechatMediaResourceImpl(
				rs.getString("media_id"), rs.getString("media_name"),
				rs.getString("media_type"), rs.getString("media_link"),
				rs.getString("media_title"), rs.getString("media_news_id"));
		return wechatMedia;
	}
}