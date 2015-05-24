package com.puluo.api.service;

import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;

import com.puluo.api.PuluoAPI;
import com.puluo.config.Configurations;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.PuluoEventDao;
import com.puluo.dao.PuluoEventMemoryDao;
import com.puluo.dao.PuluoEventPosterDao;
import com.puluo.dao.PuluoUserDao;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoEvent;
import com.puluo.entity.PuluoEventInfo;
import com.puluo.entity.PuluoEventMemory;
import com.puluo.entity.PuluoEventPoster;
import com.puluo.entity.PuluoUser;
import com.puluo.entity.impl.PuluoEventMemoryImpl;
import com.puluo.entity.impl.PuluoEventPosterImpl;
import com.puluo.enumeration.PuluoImageType;
import com.puluo.result.ApiErrorResult;
import com.puluo.result.ImageUploadServiceResult;
import com.puluo.service.PuluoService;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.Strs;

public class ImageUploadServiceAPI extends
		PuluoAPI<PuluoDSI, ImageUploadServiceResult> {
	private static final Log log = LogFactory.getLog(ImageUploadServiceAPI.class);
	private final PuluoImageType image_type;
	private final List<ImageUploadInput> images;
	private final String user_uuid;
	private final String event_uuid;
	private final boolean mock;

	public ImageUploadServiceAPI(PuluoImageType image_type, String user_uuid,
			String event_uuid, List<ImageUploadInput> images) {
		this(image_type, user_uuid, event_uuid, images, false, DaoApi
				.getInstance());
	}

	public ImageUploadServiceAPI(PuluoImageType image_type, String user_uuid,
			String event_uuid, List<ImageUploadInput> images, boolean mock) {
		this(image_type, user_uuid, event_uuid, images, mock, DaoApi
				.getInstance());
	}

	public ImageUploadServiceAPI(PuluoImageType image_type, String user_uuid,
			String event_uuid, List<ImageUploadInput> images, boolean mock,
			PuluoDSI dsi) {
		this.dsi = dsi;
		this.image_type = image_type;
		this.images = images;
		this.user_uuid = user_uuid;
		this.event_uuid = event_uuid;
		this.mock = mock;
	}

	@Override
	public void execute() {
		int size = images.size();
		if (size == 0) {
			this.error = ApiErrorResult.getError(20);
		} else if (size > 1) {
			this.error = ApiErrorResult.getError(21);
		} else {
			//
			byte[] image = images.get(0).data;
			String imageUUID = UUID.randomUUID().toString();
			if (mock) {
				String fileName = images.get(0).fileName;
				this.rawResult = new ImageUploadServiceResult(
						Strs.join(Configurations.imageServer,fileName),"success",image.length/1000);
			} else {
				String fileName = images.get(0).fileName;
				log.info(Strs.join("save image ",fileName," as ",imageUUID));
				this.rawResult = PuluoService.image.saveImage(image, imageUUID);
			}
			if (this.rawResult.isSuccess()) {
				PuluoUserDao userDao = dsi.userDao();
				PuluoUser user = userDao.getByUUID(user_uuid);
				if (user == null) {
					this.rawResult = null;
					this.error = ApiErrorResult.getError(45);
				} else {
					switch (image_type) {
					case UserProfile:
						userDao.updateProfile(user, null, null,imageUUID, null,
								null, null, null, null, null, null, null);
						break;
					case EventMemory:
						PuluoEventDao eventDao = dsi.eventDao();
						PuluoEventMemoryDao eventMemoryDao = dsi
								.eventMemoryDao();
						PuluoEvent event = eventDao.getEventByUUID(event_uuid);
						if (event == null) {
							this.rawResult = null;
							this.error = ApiErrorResult.getError(46);
						} else {
							PuluoEventMemory mem = new PuluoEventMemoryImpl(
									UUID.randomUUID().toString(), imageUUID,
									event_uuid, user_uuid, "");
							eventMemoryDao.saveEventMemory(mem);
						}
						break;
					case EventPoster:
						PuluoEventDao eventDao2 = dsi.eventDao();
						PuluoEventPosterDao eventPoserDao = dsi
								.eventPosterDao();
						PuluoEvent event2 = eventDao2
								.getEventByUUID(event_uuid);
						if (event2 == null) {
							this.rawResult = null;
							this.error = ApiErrorResult.getError(46);
						} else {
							PuluoEventInfo info = event2.eventInfo();
							if (info == null) {
								this.rawResult = null;
								this.error = ApiErrorResult.getError(46);
							} else {
								PuluoEventPoster poster = new PuluoEventPosterImpl(
										UUID.randomUUID().toString(), imageUUID,
										info.eventInfoUUID(), DateTime.now());
								eventPoserDao.saveEventPhoto(poster);
							}
						}
						break;
					default:
						break;
					}
				}
			} else {
				this.rawResult = null;
				this.error = ApiErrorResult.getError(44);
			}
		}
	}
}