package com.puluo.test.dao;

import java.util.List;
import java.util.UUID;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.puluo.dao.PuluoEventDao;
import com.puluo.dao.PuluoEventInfoDao;
import com.puluo.dao.PuluoEventLocationDao;
import com.puluo.dao.PuluoTimelineCommentDao;
import com.puluo.dao.PuluoTimelineDao;
import com.puluo.dao.PuluoTimelineLikeDao;
import com.puluo.dao.PuluoUserDao;
import com.puluo.dao.impl.DaoTestApi;
import com.puluo.dao.impl.PuluoTimelineDaoImpl;
import com.puluo.entity.PuluoEvent;
import com.puluo.entity.PuluoEventInfo;
import com.puluo.entity.PuluoEventLocation;
import com.puluo.entity.PuluoTimelineComment;
import com.puluo.entity.PuluoTimelineLike;
import com.puluo.entity.PuluoTimelinePost;
import com.puluo.entity.impl.PuluoEventImpl;
import com.puluo.entity.impl.PuluoEventInfoImpl;
import com.puluo.entity.impl.PuluoEventLocationImpl;
import com.puluo.entity.impl.PuluoTimelinePostImpl;
import com.puluo.enumeration.EventStatus;
import com.puluo.enumeration.PuluoEventCategory;
import com.puluo.enumeration.PuluoEventLevel;
import com.puluo.jdbc.DalTemplate;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.TimeUtils;

public class PuluoPostDaoTest {
	
	public static Log log = LogFactory.getLog(PuluoPostDaoTest.class);

	public static String owner_uuid = null;
	public static String from_user_uuid = null;
	public static String to_user_uuid = null;
	public static String password = "luKe20!5";
	public static String event_uuid = UUID.randomUUID().toString();
	public static String timeline_uuid = UUID.randomUUID().toString();

	@BeforeClass
	public static void setUpDB() {
		log.info("setUpDB start!");
		PuluoTimelineDao postDao = DaoTestApi.postDevDao;
		postDao.createTable();
		PuluoTimelineCommentDao postCommentDao = DaoTestApi.postCommentDevDao;
		postCommentDao.createTable();
		PuluoTimelineLikeDao postLikeDao = DaoTestApi.postLikeDevDao;
		postLikeDao.createTable();
		
		PuluoEventInfoDao infoDao = DaoTestApi.eventInfoDevDao;
		infoDao.createTable();
		PuluoEventInfo info0 = new PuluoEventInfoImpl("event_info_uuid_0", "name_0", "description_0",
				"coach_name_0", "coach_uuid_0", "thumbnail_uuid_0", "details_0", 0, PuluoEventLevel.Level1, PuluoEventCategory.Others);
		infoDao.upsertEventInfo(info0);
		
		PuluoEventLocationDao locationDao = DaoTestApi.eventLocationDevDao;
		locationDao.createTable();
		PuluoEventLocation location0 = new PuluoEventLocationImpl("uuid_0",
				"address_0", "zip_0", "name_0", "phone_0", "city_0", 1.0, 1.0, 0, 0, 0);
		locationDao.upsertEventLocation(location0);

		PuluoEventDao eventDao = DaoTestApi.eventDevDao;
		eventDao.createTable();
		PuluoEvent event0 = new PuluoEventImpl(event_uuid, TimeUtils.parseDateTime("2015-03-02 20:35:59"), EventStatus.Open, 
				0, 0, 300.00, 299.99, 
				"event_info_uuid_0", "uuid_0", 0, DaoTestApi.getInstance());
		eventDao.upsertEvent(event0);
		
		PuluoUserDao userDao = DaoTestApi.userDevDao;
		userDao.createTable();
		userDao.save("13262247972", password);
		userDao.save("18521564305", password);
		userDao.save("17721014665", password);
		owner_uuid = userDao.getByMobile("13262247972").userUUID();
		from_user_uuid = userDao.getByMobile("18521564305").userUUID();
		to_user_uuid = userDao.getByMobile("17721014665").userUUID();

		PuluoTimelinePost timeline = new PuluoTimelinePostImpl(timeline_uuid, event_uuid, owner_uuid, "2015/6/22 14:52", TimeUtils.parseDateTime("2015-06-22 14:52:00"), null);
		((PuluoTimelineDaoImpl)postDao).saveTimeline(timeline, DaoTestApi.getInstance());
		
		log.info("setUpDB done!");
	}

	@AfterClass
	public static void cleanUpDB() {
		log.info("cleanUpDB start!");
		DalTemplate dao = (DalTemplate) DaoTestApi.postDevDao;
		dao.getWriter().execute("drop table " + dao.getFullTableName());
		log.info("dropped table " + dao.getFullTableName());
		dao = (DalTemplate) DaoTestApi.postCommentDevDao;
		dao.getWriter().execute("drop table " + dao.getFullTableName());
		log.info("dropped table " + dao.getFullTableName());
		dao = (DalTemplate) DaoTestApi.postLikeDevDao;
		dao.getWriter().execute("drop table " + dao.getFullTableName());
		log.info("dropped table " + dao.getFullTableName());
		dao = (DalTemplate) DaoTestApi.userDevDao;
		dao.getWriter().execute("drop table " + dao.getFullTableName());
		log.info("dropped table " + dao.getFullTableName());
		dao = (DalTemplate) DaoTestApi.eventInfoDevDao;
		dao.getWriter().execute("drop table " + dao.getFullTableName());
		log.info("dropped table " + dao.getFullTableName());
		dao = (DalTemplate) DaoTestApi.eventLocationDevDao;
		dao.getWriter().execute("drop table " + dao.getFullTableName());
		log.info("dropped table " + dao.getFullTableName());
		dao = (DalTemplate) DaoTestApi.eventDevDao;
		dao.getWriter().execute("drop table " + dao.getFullTableName());
		log.info("dropped table " + dao.getFullTableName());
		log.info("cleanUpDB done!");
	}

	@Test
	public void testSaveTimeline() {
		log.info("testSaveTimeline start!");
		PuluoTimelineDao postDao = DaoTestApi.postDevDao;
		PuluoTimelinePost timeline = new PuluoTimelinePostImpl(timeline_uuid, event_uuid, owner_uuid, "2015/6/22 14:52", TimeUtils.parseDateTime("2015-06-22 14:52:00"), null);
		Assert.assertFalse("the result should be false!", ((PuluoTimelineDaoImpl)postDao).saveTimeline(timeline, DaoTestApi.getInstance()));
		log.info("testSaveTimeline done!");
	}

	@Test
	public void testGetByUserAndEvent() {
		log.info("testGetByUserAndEvent start!");
		PuluoTimelineDao postDao = DaoTestApi.postDevDao;
		PuluoTimelinePost timeline = postDao.getByUserAndEvent(owner_uuid, event_uuid);
		Assert.assertEquals("content should be 2015/6/22 14:52", "2015/6/22 14:52", timeline.content());
		Assert.assertEquals("createAt should be 2015-06-22 14:52:00", "2015-06-22 14:52:00", TimeUtils.formatDate(timeline.createdAt()));
		log.info("testGetByUserAndEvent done!");
	}

	@Test
	public void testGetByUUID() {
		log.info("testGetByUUID start!");
		PuluoTimelineDao postDao = DaoTestApi.postDevDao;
		PuluoTimelinePost timeline = postDao.getByUUID(timeline_uuid);
		Assert.assertEquals("content should be 2015/6/22 14:52", "2015/6/22 14:52", timeline.content());
		Assert.assertEquals("createAt should be 2015-06-22 14:52:00", "2015-06-22 14:52:00", TimeUtils.formatDate(timeline.createdAt()));
		log.info("testGetByUUID done!");
	}

	@Test
	public void testGetUserTimeline() {
		log.info("testGetUserTimeline start!");
		PuluoTimelineDao postDao = DaoTestApi.postDevDao;
		List<PuluoTimelinePost> timelines = postDao.getUserTimeline(owner_uuid, "2015-06-22 15:00:00", 0, 0);
		Assert.assertEquals("size should be 0", 0, timelines.size());
		timelines = postDao.getUserTimeline(owner_uuid, "", 0, 0);
		Assert.assertEquals("size should be 1", 1, timelines.size());
		Assert.assertEquals("uuid should be " + timeline_uuid, timeline_uuid, timelines.get(0).timelineUUID());
		log.info("testGetUserTimeline done!");
	}

	@Test
	public void testTimelineLikeDao() {
		log.info("testTimelineLikeDao start!");
		PuluoTimelineLikeDao postLikeDao = DaoTestApi.postLikeDevDao;
		String result = postLikeDao.saveTimelineLike(timeline_uuid, owner_uuid);
		Assert.assertEquals("result should be success", "success", result);
		postLikeDao.saveTimelineLike(timeline_uuid, from_user_uuid);
		postLikeDao.saveTimelineLike(timeline_uuid, from_user_uuid);
		postLikeDao.saveTimelineLike(timeline_uuid, to_user_uuid);
		Assert.assertEquals("size should be 4", 4, postLikeDao.getTotalLikes(timeline_uuid).size());
		result = postLikeDao.removeTimelineLike(timeline_uuid, from_user_uuid);
		Assert.assertEquals("result should be success", "success", result);
		postLikeDao.removeTimelineLike(timeline_uuid, to_user_uuid);
		Assert.assertEquals("size should be 1", 1, postLikeDao.getTotalLikes(timeline_uuid).size());
		List<PuluoTimelineLike> likes = postLikeDao.getTotalLikes(timeline_uuid);
		Assert.assertEquals("user uuid should be " + owner_uuid, owner_uuid, likes.get(0).userUUID());
		log.info("testTimelineLikeDao done!");
	}

	@Test
	public void testTimelineCommentDao() {
		log.info("testTimelineCommentDao start!");
		PuluoTimelineCommentDao postCommentDao = DaoTestApi.postCommentDevDao;
		String result = postCommentDao.saveTimelineComment(timeline_uuid, from_user_uuid, to_user_uuid, "1");
		Assert.assertEquals("result should be success", "success", result);
		postCommentDao.saveTimelineComment(timeline_uuid, from_user_uuid, to_user_uuid, "2");
		postCommentDao.saveTimelineComment(timeline_uuid, from_user_uuid, to_user_uuid, "3");
		postCommentDao.saveTimelineComment(timeline_uuid, from_user_uuid, to_user_uuid, "4");
		List<PuluoTimelineComment> comments = postCommentDao.getByTimeline(timeline_uuid);
		Assert.assertEquals("size should be 4", 4, comments.size());
		String comment_uuid = comments.get(0).commentUUID();
		String content = comments.get(0).content();
		PuluoTimelineComment comment = postCommentDao.getByUUID(comment_uuid);
		Assert.assertEquals("content should be " + content, content, comment.content());
		result = postCommentDao.removeTimelineComment(comment_uuid);
		Assert.assertEquals("result should be success", "success", result);
		Assert.assertEquals("size should be 3", 3, postCommentDao.getByTimeline(timeline_uuid).size());
		Assert.assertEquals("size should be 3", 3, postCommentDao.getUnreadCommentsFromUser(from_user_uuid).size());
		log.info("testTimelineCommentDao done!");
	}
}
