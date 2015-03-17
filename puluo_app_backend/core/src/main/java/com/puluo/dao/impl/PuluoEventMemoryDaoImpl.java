package com.puluo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.jdbc.core.RowMapper;

import com.puluo.dao.PuluoEventMemoryDao;
import com.puluo.entity.PuluoEventMemory;
import com.puluo.entity.impl.PuluoEventMemoryImpl;
import com.puluo.jdbc.DalTemplate;
import com.puluo.jdbc.SqlReader;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;


public class PuluoEventMemoryDaoImpl extends DalTemplate implements PuluoEventMemoryDao {
	
	public static Log log = LogFactory.getLog(PuluoEventMemoryDaoImpl.class);

	@Override
	public boolean createTable() {
		try {
			String createSQL = new StringBuilder().append("create table ")
				.append(super.getFullTableName())
				.append(" (id serial primary key, ")
				.append("memory_uuid text unique, ")
				.append("image_url text, ")
				.append("thumbnail text, ")
				.append("event_uuid text, ")
				.append("user_uuid text, ")
				.append("timeline_uuid text)")
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
	public boolean upsertEventMemory(PuluoEventMemory memory) {
		try {
			SqlReader reader = getReader();
			String querySQL = new StringBuilder().append("select count(1) from ")
					.append(super.getFullTableName())
					.append(" where memory_uuid = '" + memory.imageId() + "'")
					.toString();
			log.info(reader.queryForInt(querySQL));
			String updateSQL;
			if (reader.queryForInt(querySQL)>0) {
				updateSQL = new StringBuilder().append("update ")
						.append(super.getFullTableName())
						.append(" set image_url = ").append(memory.imageURL()!=null ? "'" + memory.imageURL() + "'" : "null").append(",")
						.append(" thumbnail = ").append(memory.thumbnail()!=null ? "'" + memory.thumbnail() + "'" : "null").append(",")
						.append(" event_uuid = ").append(memory.eventId()!=null ? "'" + memory.eventId() + "'" : "null").append(",")
						.append(" user_uuid = ").append(memory.userId()!=null ? "'" + memory.userId() + "'" : "null").append(",")
						.append(" timeline_uuid = ").append(memory.timelineId()!=null ? "'" + memory.timelineId() + "'" : "null")
						.append(" where memory_uuid = '" + memory.imageId() + "'")
						.toString();
			} else {
				updateSQL = new StringBuilder().append("insert into ")
						.append(super.getFullTableName())
						.append(" (memory_uuid, image_url, thumbnail, event_uuid, user_uuid, timeline_uuid)")
						.append(" values ('" + memory.imageId() + "', ")
						.append(memory.imageURL()!=null ? "'" + memory.imageURL() + "'" : "null").append(", ")
						.append(memory.thumbnail()!=null ? "'" + memory.thumbnail() + "'" : "null").append(", ")
						.append(memory.eventId()!=null ? "'" + memory.eventId() + "'" : "null").append(", ")
						.append(memory.userId()!=null ? "'" + memory.userId() + "'" : "null").append(", ")
						.append(memory.timelineId()!=null ? "'" + memory.timelineId() + "'" : "null").append(")")
						.toString();
			}
			log.info(updateSQL);
			getWriter().update(updateSQL);
			return true;
		} catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}
	}

	@Override
	public ArrayList<PuluoEventMemory> getEventMemoryByUUID(String event_uuid) {
		SqlReader reader = getReader();
		StringBuilder selectSQL = new StringBuilder().append("select * from ")
				.append(super.getFullTableName()).append(" where event_uuid = ?");
		ArrayList<PuluoEventMemory> entities = (ArrayList<PuluoEventMemory>) reader.query(selectSQL.toString(), new Object[]{event_uuid},
				new RowMapper<PuluoEventMemory>() {
					@Override
					public PuluoEventMemory mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						PuluoEventMemoryImpl eventPoster = new PuluoEventMemoryImpl(
								rs.getString("memory_uuid"),
								rs.getString("image_url"),
								rs.getString("thumbnail"),
								rs.getString("event_uuid"),
								rs.getString("user_uuid"),
								rs.getString("timeline_uuid"));
						return eventPoster;
					}
				});
		return entities;
	}
}
