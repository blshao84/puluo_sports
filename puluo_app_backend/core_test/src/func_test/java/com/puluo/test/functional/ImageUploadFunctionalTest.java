package com.puluo.test.functional;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.puluo.config.Configurations;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;
import com.puluo.dao.impl.PuluoEventDaoImpl;
import com.puluo.dao.impl.PuluoEventInfoDaoImpl;
import com.puluo.dao.impl.PuluoEventMemoryDaoImpl;
import com.puluo.dao.impl.PuluoEventPosterDaoImpl;
import com.puluo.dao.impl.PuluoSessionDaoImpl;
import com.puluo.dao.impl.PuluoUserDaoImpl;
import com.puluo.entity.PuluoEvent;
import com.puluo.entity.PuluoEventInfo;
import com.puluo.entity.PuluoEventMemory;
import com.puluo.entity.PuluoEventPoster;
import com.puluo.entity.PuluoUser;
import com.puluo.entity.impl.PuluoEventImpl;
import com.puluo.entity.impl.PuluoEventInfoImpl;
import com.puluo.enumeration.EventStatus;
import com.puluo.enumeration.PuluoEventCategory;
import com.puluo.enumeration.PuluoEventLevel;
import com.puluo.test.functional.util.APIFunctionalTest;
import com.puluo.test.functional.util.PuluoAuthenticatedFunctionalTestRunner;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.Strs;

public class ImageUploadFunctionalTest extends APIFunctionalTest {
	public static Log log = LogFactory.getLog(ImageUploadFunctionalTest.class);
	static String mobile1 = "123456789";
	static String password1 = "abcdefg";
	static String event_uuid = "event_uuid";
	static String event_info_uuid = "event_info_uuid";
	
	@BeforeClass
	public static void setupDB() {
		
		cleanupDB();
		PuluoDSI dsi = DaoApi.getInstance();
		dsi.userDao().save(mobile1, password1);
		dsi.eventInfoDao().saveEventInfo(
				new PuluoEventInfoImpl(event_info_uuid, "test", "test", "test",
						"test", "", "", 45, PuluoEventLevel.Level1,
						PuluoEventCategory.Others));
		dsi.eventDao().saveEvent(
				new PuluoEventImpl(event_uuid, DateTime.now(),
						EventStatus.Open, 0, 20, 50.0, 25.0, event_info_uuid,
						"", 0));
		
	}

	@AfterClass
	public static void cleanupDB() {
		PuluoDSI dsi = DaoApi.getInstance();
		PuluoUserDaoImpl dao = (PuluoUserDaoImpl) dsi.userDao();
		PuluoSessionDaoImpl sessionDao = (PuluoSessionDaoImpl) dsi.sessionDao();
		PuluoEventDaoImpl eventDao = (PuluoEventDaoImpl) dsi.eventDao();
		PuluoEventInfoDaoImpl infoDao = (PuluoEventInfoDaoImpl) dsi
				.eventInfoDao();
		PuluoEventPosterDaoImpl posterDao = (PuluoEventPosterDaoImpl) dsi
				.eventPosterDao();
		PuluoEventMemoryDaoImpl memDao = (PuluoEventMemoryDaoImpl) dsi
				.eventMemoryDao();
		PuluoUser user = dao.getByMobile(mobile1);
		if (user != null) {
			dao.deleteByUserUUID(user.userUUID());
			sessionDao.obliterateAllSessions(user.mobile());
		}
		eventDao.deleteByEventUUID(event_uuid);
		infoDao.deleteByEventInfoUUID(event_info_uuid);
		posterDao.deleteByEventInfoUUID(event_info_uuid);
		memDao.deleteByEventUUID(event_uuid);

	}

	@Test
	public void testUploadUserImage() {
		super.runAuthenticatedTest(new ImageUploadFunctionalTestRunner() {

			@Override
			public void run(String session) throws UnirestException {
				HttpResponse<JsonNode> jsonResponse = Unirest
						.post(Strs.join(host, "services/images/user"))
						.header("accept", "application/json")
						.field("token", session).field("mock", "true")
						.field("file", getFile()).asJson();
				JsonNode json = jsonResponse.getBody();
				String actualLink = getStringFromJson(json, "image_link");
				String thumbnail = DaoApi.getInstance().userDao()
						.getByMobile(mobile1).thumbnail();
				String expectedThumbnail = "puluoyundong.png";
				String expectedLink = Strs.join(Configurations.imageServer,
						expectedThumbnail);
				Assert.assertEquals(expectedLink, actualLink);
				Assert.assertEquals(expectedThumbnail, thumbnail);

			}
		});

	}
	
	@Test
	public void testUploadPoster() {
		super.runAuthenticatedTest(new ImageUploadFunctionalTestRunner() {

			@Override
			public void run(String session) throws UnirestException {
				HttpResponse<JsonNode> jsonResponse = Unirest
						.post(Strs.join(host, "services/images/poster"))
						.header("accept", "application/json")
						.field("token", session).field("mock", "true")
						.field("event_uuid",event_uuid)
						.field("file", getFile()).asJson();
				JsonNode json = jsonResponse.getBody();
				String actualLink = getStringFromJson(json, "image_link");
				PuluoEventInfo info = DaoApi.getInstance().eventInfoDao().getEventInfoByUUID(event_info_uuid);
				List<PuluoEventPoster> posters = info.poster();
				Assert.assertEquals(1, posters.size());
				String thumbnailURL = posters.get(0).thumbnailURL();
				String expectedThumbnail = "puluoyundong.png";
				String expectedLink = Strs.join(Configurations.imageServer,
						expectedThumbnail);
				String expectedThumbnailURL = Strs.join(expectedLink,"!small");
				Assert.assertEquals(expectedLink, actualLink);
				Assert.assertEquals(expectedThumbnailURL, thumbnailURL);

			}
		});
	}
	
	@Test
	public void testUploadMemory() {
		super.runAuthenticatedTest(new ImageUploadFunctionalTestRunner() {

			@Override
			public void run(String session) throws UnirestException {
				HttpResponse<JsonNode> jsonResponse = Unirest
						.post(Strs.join(host, "services/images/memory"))
						.header("accept", "application/json")
						.field("token", session).field("mock", "true")
						.field("event_uuid",event_uuid)
						.field("file", getFile()).asJson();
				JsonNode json = jsonResponse.getBody();
				String actualLink = getStringFromJson(json, "image_link");
				PuluoEvent event = DaoApi.getInstance().eventDao().getEventByUUID(event_uuid);
				List<PuluoEventMemory> memories = event.memory();
				Assert.assertEquals(1, memories.size());
				String thumbnailURL = memories.get(0).thumbnailURL();
				String expectedThumbnail = "puluoyundong.png";
				String expectedLink = Strs.join(Configurations.imageServer,
						expectedThumbnail);
				String expectedThumbnailURL = Strs.join(expectedLink,"!small");
				Assert.assertEquals(expectedLink, actualLink);
				Assert.assertEquals(expectedThumbnailURL, thumbnailURL);

			}
		});
	}
	
	private File getFile() {
		URL url = ImageUploadFunctionalTest.class
				.getResource("/com/puluo/test/functional/util/puluoyundong.png");
		try {
			return new File(url.toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return null;
		}
	}

	abstract class ImageUploadFunctionalTestRunner implements
			PuluoAuthenticatedFunctionalTestRunner {

		@Override
		public String inputs(String session) {
			return String.format("{" + "\"token\":\"%s\","
					+ "\"user_uuid\":\"%s\"}", session);
		}

		@Override
		public String mobile() {
			return mobile1;
		}

		@Override
		public String password() {
			return password1;
		}

	}
}
