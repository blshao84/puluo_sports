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
				.append("image_url text not null, ")
				.append("thumbnail text not null, ")
				.append("event_info_uuid text not null)")
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
	public boolean saveEventPhoto(PuluoEventPoster photo) {
		try {
			String insertSQL = new StringBuilder().append("insert into ")
					.append(super.getFullTableName())
					.append(" (event_poster_uuid, image_url, thumbnail, event_info_uuid)")
					.append(" values ('" + photo.imageId() + "', '" + photo.imageURL() + "', '" + photo.thumbnail() + "', '" + photo.eventInfoUUID() + "')")
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
	public List<PuluoEventPoster> getEventPoster(String event_info_uuid) {
		SqlReader reader = getReader();
		StringBuilder selectSQL = new StringBuilder().append("select * from ")
				.append(super.getFullTableName()).append(" where event_info_uuid = ?");
		List<PuluoEventPoster> entities = reader.query(selectSQL.toString(), new Object[]{event_info_uuid},
				new RowMapper<PuluoEventPoster>() {
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
				});
		return entities;
	}
}
