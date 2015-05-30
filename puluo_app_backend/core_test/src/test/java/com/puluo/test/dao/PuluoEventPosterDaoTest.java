package com.puluo.test.dao;

import java.util.List;

import org.joda.time.DateTime;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.puluo.config.Configurations;
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
		PuluoEventPoster photo = new PuluoEventPosterImpl("uuid_0","image_url_0","event_info_uuid_0",DateTime.now());
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
		boolean success = DaoTestApi.eventPosterDevDao.saveEventPhoto(new PuluoEventPosterImpl("uuid_0","image_url_0","event_info_uuid_0",DateTime.now()));
		Assert.assertFalse("save a duplicate poster should fail!", success);
		log.info("testSaveDupPoster done!");
	}

	@Test 
	public void testGetEventPoster() {
		log.info("testGetEventPoster start!");
		PuluoEventPosterDao posterDao = DaoTestApi.eventPosterDevDao;
		posterDao.saveEventPhoto(new PuluoEventPosterImpl("uuid_1","image_url_1","event_info_uuid_0",DateTime.now().plusMinutes(5)));
		posterDao.saveEventPhoto(new PuluoEventPosterImpl("uuid_2","image_url_2","event_info_uuid_0",DateTime.now().plusMinutes(4)));
		posterDao.saveEventPhoto(new PuluoEventPosterImpl("uuid_3","image_url_3","event_info_uuid_0",DateTime.now().plusMinutes(3)));
		posterDao.saveEventPhoto(new PuluoEventPosterImpl("uuid_4","image_url_4","event_info_uuid_0",DateTime.now().plusMinutes(2)));
		posterDao.saveEventPhoto(new PuluoEventPosterImpl("uuid_5","image_url_5","event_info_uuid_0",DateTime.now().plusMinutes(1)));
		List<PuluoEventPoster> posters = posterDao.getEventPosterByInfoUUID("event_info_uuid_0");
		Assert.assertEquals("posters' size should be 5!", 5, posters.size());
		String expectedImglink = Configurations.imgHttpLink("image_url_1!rect");
		Assert.assertEquals("the first image url of the posters should be image_url_1!",expectedImglink , posters.get(0).imageURL());
		log.info("testGetEventPoster done!");
	}

	@Test 
	public void testUpdateEventPoster() {
		log.info("testUpdateEventPoster start!");
		PuluoEventPosterDao posterDao = DaoTestApi.eventPosterDevDao;
		posterDao.saveEventPhoto(new PuluoEventPosterImpl("uuid_6","image_url_6","event_info_uuid_1",DateTime.now()));
		posterDao.updateEventPhoto(new PuluoEventPosterImpl("uuid_6",null,null,DateTime.now()));
		List<PuluoEventPoster> posters = posterDao.getEventPosterByInfoUUID("event_info_uuid_1");
		PuluoEventPoster poster = posters.get(0);
		String expectedImg = Configurations.imgHttpLink("image_url_6!rect");
		String expectedThumbnail = Configurations.imgHttpLink("image_url_6!rect","");
		Assert.assertEquals("poster's image url should be image_url_6!",expectedImg, poster.imageURL());
		Assert.assertEquals("poster's thumbnail should be thumbnail_6", expectedThumbnail, poster.thumbnailURL());
		log.info("testUpdateEventPoster done!");
	}
}
