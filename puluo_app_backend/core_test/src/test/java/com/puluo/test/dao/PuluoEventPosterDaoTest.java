package com.puluo.test.dao;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.puluo.dao.PuluoEventPosterDao;
import com.puluo.dao.impl.DaoTestApi;
import com.puluo.entity.PuluoEventPoster;
import com.puluo.entity.impl.PuluoEventPosterImpl;
import com.puluo.jdbc.DalTemplate;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;

public class PuluoEventPosterDaoTest {
	
	public static Log log = LogFactory.getLog(PuluoEventPosterDaoTest.class);

	@BeforeClass
	public static void setUpDB() {
		log.info("setUpDB start!");
		PuluoEventPosterDao posterDao = DaoTestApi.eventPosterDevDao;
		posterDao.createTable();
		PuluoEventPoster photo = new PuluoEventPosterImpl("uuid_0","image_url_0","thumbnail_0","event_info_uuid_0");
		posterDao.saveEventPhoto(photo);
		log.info("setUpDB done!");
	}

	@AfterClass
	public static void cleanUpDB() {
		log.info("cleanUpDB start!");
		DalTemplate dao = (DalTemplate) DaoTestApi.eventPosterDevDao;
		dao.getWriter().execute("drop table " + dao.getFullTableName());
		log.info("dropped table " + dao.getFullTableName());
		log.info("cleanUpDB done!");
	}

	@Test
	public void testSaveDupPoster() {
		log.info("testSaveDupPoster start!");
		boolean success = DaoTestApi.eventPosterDevDao.saveEventPhoto(new PuluoEventPosterImpl("uuid_0","image_url_0","thumbnail_0","event_info_uuid_0"));
		Assert.assertFalse("save a duplicate poster should fail!", success);
		log.info("testSaveDupPoster done!");
	}

	@Test 
	public void testGetEventPoster() {
		log.info("testGetEventPoster start!");
		PuluoEventPosterDao posterDao = DaoTestApi.eventPosterDevDao;
		posterDao.saveEventPhoto(new PuluoEventPosterImpl("uuid_1","image_url_0","thumbnail_0","event_info_uuid_0"));
		posterDao.saveEventPhoto(new PuluoEventPosterImpl("uuid_2","image_url_0","thumbnail_0","event_info_uuid_0"));
		List<PuluoEventPoster> posters = posterDao.getEventPoster("event_info_uuid_0");
		Assert.assertEquals("posters' size should be 3!", 3, posters.size());
		log.info("testGetEventPoster done!");
	}

	@Test 
	public void testUpdateEventPoster() {
		log.info("testUpdateEventPoster start!");
		PuluoEventPosterDao posterDao = DaoTestApi.eventPosterDevDao;
		posterDao.saveEventPhoto(new PuluoEventPosterImpl("uuid_3","image_url_0","thumbnail_0","event_info_uuid_1"));
		posterDao.updateEventPhoto(new PuluoEventPosterImpl("uuid_3","image_url_1",null,null));
		List<PuluoEventPoster> posters = posterDao.getEventPoster("event_info_uuid_1");
		PuluoEventPoster poster = posters.get(0);
		Assert.assertEquals("poster's image url should be image_url_1!", "image_url_1", poster.imageURL());
		Assert.assertEquals("poster's thumbnail should be thumbnail_0", "thumbnail_0", poster.thumbnail());
		log.info("testUpdateEventPoster done!");
	}
}
