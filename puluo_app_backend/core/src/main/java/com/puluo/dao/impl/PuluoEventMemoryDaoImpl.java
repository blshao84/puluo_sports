package com.puluo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.puluo.dao.PuluoEventMemoryDao;
import com.puluo.entity.PuluoEventMemory;
import com.puluo.entity.impl.PuluoEventMemoryImpl;
import com.puluo.jdbc.DalTemplate;
import com.puluo.jdbc.SqlReader;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.PuluoDatabaseException;
import com.puluo.util.Strs;


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
	
	public boolean deleteByMemoryUUID(String uuid){
		return super.deleteByUniqueKey("memory_uuid", uuid);
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
			if (reader.queryForInt(querySQL)>0) {
				return this.updateEventMemory(memory);
			} else {
				return this.saveEventMemory(memory);
			}
		} catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}
	}

	@Override
	public List<PuluoEventMemory> getEventMemoryByUUID(String event_uuid) {
		SqlReader reader = getReader();
		StringBuilder selectSQL = new StringBuilder().append("select * from ")
				.append(super.getFullTableName()).append(" where event_uuid = ?");
		List<PuluoEventMemory> entities = reader.query(selectSQL.toString(), new Object[]{event_uuid},
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

	@Override
	public boolean saveEventMemory(PuluoEventMemory memory) {
		try {
			SqlReader reader = getReader();
			String querySQL = new StringBuilder().append("select count(1) from ")
					.append(super.getFullTableName())
					.append(" where memory_uuid = '" + memory.imageId() + "'")
					.toString();
			log.info(reader.queryForInt(querySQL));
			String updateSQL;
			if (reader.queryForInt(querySQL)==0) {
				updateSQL = new StringBuilder().append("insert into ")
						.append(super.getFullTableName())
						.append(" (memory_uuid, image_url, thumbnail, event_uuid, user_uuid, timeline_uuid)")
						.append(" values ('" + memory.imageId() + "', ")
						.append(Strs.isEmpty(memory.imageURL()) ? "null" : "'" + memory.imageURL() + "'").append(", ")
						.append(Strs.isEmpty(memory.thumbnail()) ? "null" : "'" + memory.thumbnail() + "'").append(", ")
						.append(Strs.isEmpty(memory.eventId()) ? "null" : "'" + memory.eventId() + "'").append(", ")
						.append(Strs.isEmpty(memory.userId()) ? "null" : "'" + memory.userId() + "'").append(", ")
						.append(Strs.isEmpty(memory.timelineId()) ? "null" : "'" + memory.timelineId() + "'").append(")")
						.toString();
				log.info(updateSQL);
				getWriter().update(updateSQL);
				return true;
			} else {
				throw new PuluoDatabaseException("memory_uuid为'" + memory.imageId() + "'已存在不能插入数据！");
			}
		} catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}
	}

	@Override
	public boolean updateEventMemory(PuluoEventMemory memory) {
		try {
			SqlReader reader = getReader();
			String querySQL = new StringBuilder().append("select count(1) from ")
					.append(super.getFullTableName())
					.append(" where memory_uuid = '" + memory.imageId() + "'")
					.toString();
			log.info(reader.queryForInt(querySQL));
			StringBuilder updateSQL;
			if (reader.queryForInt(querySQL)>0) {
				updateSQL = new StringBuilder().append("update ")
						.append(super.getFullTableName()).append(" set");
				boolean comma = false;
				if (!Strs.isEmpty(memory.imageURL())) {
					updateSQL.append(" image_url = '" + memory.imageURL() + "',");
					comma = true;
				}
				if (!Strs.isEmpty(memory.thumbnail())) {
					updateSQL.append(" thumbnail = '" + memory.thumbnail() + "',");
					comma = true;
				}
				if (!Strs.isEmpty(memory.eventId())) {
					updateSQL.append(" event_uuid = '" + memory.eventId() + "',");
					comma = true;
				}
				if (!Strs.isEmpty(memory.userId())) {
					updateSQL.append(" user_uuid = '" + memory.userId() + "',");
					comma = true;
				}
				if (!Strs.isEmpty(memory.timelineId())) {
					updateSQL.append(" timeline_uuid = '" + memory.timelineId() + "',");
					comma = true;
				}
				if (comma) {
					updateSQL.deleteCharAt(updateSQL.length()-1);
				}
				updateSQL.append(" where memory_uuid = '" + memory.imageId() + "'");
				log.info(updateSQL.toString());
				if (comma) {
					getWriter().update(updateSQL.toString());
				}
				return true;
			} else {
				throw new PuluoDatabaseException("memory_uuid为'" + memory.imageId() + "'不存在不能更新数据！");
			}
		} catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}
	}
}
