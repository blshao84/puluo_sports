package com.puluo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.puluo.dao.PuluoEventPosterDao;
import com.puluo.entity.PuluoEventPoster;
import com.puluo.entity.impl.PuluoEventPosterImpl;
import com.puluo.jdbc.DalTemplate;
import com.puluo.jdbc.SqlReader;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.PuluoDatabaseException;
import com.puluo.util.Strs;

public class PuluoEventPosterDaoImpl extends DalTemplate implements
		PuluoEventPosterDao {
	
	public static Log log = LogFactory.getLog(PuluoEventPosterDaoImpl.class);

	@Override
	public boolean createTable() {
		try {
			String createSQL = new StringBuilder().append("create table ")
				.append(super.getFullTableName())
				.append(" (id serial primary key, ")
				.append("event_poster_uuid text unique, ")
				.append("image_url text, ")
				.append("thumbnail text, ")
				.append("event_info_uuid text)")
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
	
	public boolean deleteByPosterUUID(String uuid){
		return super.deleteByUniqueKey("event_poster_uuid", uuid);
	}

	@Override
	public boolean saveEventPhoto(PuluoEventPoster photo) {
		try {
			SqlReader reader = getReader();
			String querySQL = new StringBuilder().append("select count(1) from ")
					.append(super.getFullTableName())
					.append(" where event_poster_uuid = '" + photo.imageId() + "'")
					.toString();
			log.info(reader.queryForInt(querySQL));
			String updateSQL;
			if (reader.queryForInt(querySQL)==0) {
				updateSQL = new StringBuilder().append("insert into ")
						.append(super.getFullTableName())
						.append(" (event_poster_uuid, image_url, thumbnail, event_info_uuid)")
						.append(" values ('" + photo.imageId() + "', ")
						.append(Strs.isEmpty(photo.imageURL()) ? "null" : "'" + photo.imageURL() + "'").append(", ")
						.append(Strs.isEmpty(photo.thumbnail()) ? "null" : "'" + photo.thumbnail() + "'").append(", ")
						.append(Strs.isEmpty(photo.eventInfoUUID()) ? "null" : "'" + photo.eventInfoUUID() + "'").append(")")
						.toString();
				log.info(updateSQL);
				getWriter().update(updateSQL);
				return true;
			} else {
				throw new PuluoDatabaseException("event_poster_uuid为'" + photo.imageId() + "'已存在不能插入数据！");
			}
		} catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}
	}

	@Override
	public List<PuluoEventPoster> getEventPosterByInfoUUID(String event_info_uuid) {
		SqlReader reader = getReader();
		StringBuilder selectSQL = new StringBuilder().append("select * from ")
				.append(super.getFullTableName()).append(" where event_info_uuid = ?");
		List<PuluoEventPoster> entities = reader.query(selectSQL.toString(), new Object[]{event_info_uuid},
				new PuluoEventPosterMapper());
		return entities;
	}

	@Override
	public boolean updateEventPhoto(PuluoEventPoster photo) {
		try {
			SqlReader reader = getReader();
			String querySQL = new StringBuilder().append("select count(1) from ")
					.append(super.getFullTableName())
					.append(" where event_poster_uuid = '" + photo.imageId() + "'")
					.toString();
			log.info(reader.queryForInt(querySQL));
			StringBuilder updateSQL;
			if (reader.queryForInt(querySQL)>0) {
				updateSQL = new StringBuilder().append("update ")
						.append(super.getFullTableName()).append(" set");
				boolean comma = false;
				if (!Strs.isEmpty(photo.imageURL())) {
					updateSQL.append(" image_url = '" + photo.imageURL() + "',");
					comma = true;
				}
				if (!Strs.isEmpty(photo.thumbnail())) {
					updateSQL.append(" thumbnail = '" + photo.thumbnail() + "',");
					comma = true;
				}
				if (!Strs.isEmpty(photo.eventInfoUUID())) {
					updateSQL.append(" event_info_uuid = '" + photo.eventInfoUUID() + "',");
					comma = true;
				}
				if (comma) {
					updateSQL.deleteCharAt(updateSQL.length()-1);
				}
				updateSQL.append(" where event_poster_uuid = '" + photo.imageId() + "'");
				log.info(updateSQL.toString());
				if (comma) {
					getWriter().update(updateSQL.toString());
				}
				return true;
			} else {
				throw new PuluoDatabaseException("event_poster_uuid为'" + photo.imageId() + "'不存在不能更新数据！");
			}
		} catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}
	}

	@Override
	public PuluoEventPoster getEventPosterByUUID(String uuid) {
		SqlReader reader = getReader();
		StringBuilder selectSQL = new StringBuilder().append("select * from ")
				.append(super.getFullTableName()).append(" where event_poster_uuid = ?");
		List<PuluoEventPoster> entities = reader.query(selectSQL.toString(), new Object[]{uuid},
				new PuluoEventPosterMapper());
		if (entities.size() == 1)
			return entities.get(0);
		else if (entities.size() > 1)
			throw new PuluoDatabaseException("通过event uuid查到多个event！");
		else
			return null;
	}
}

class PuluoEventPosterMapper implements RowMapper<PuluoEventPoster> {
	
	@Override
	public PuluoEventPoster mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		PuluoEventPosterImpl eventPoster = new PuluoEventPosterImpl(
				rs.getString("event_poster_uuid"),
				rs.getString("image_url"),
				rs.getString("thumbnail"),
				rs.getString("event_info_uuid"));
		return eventPoster;
	}
}