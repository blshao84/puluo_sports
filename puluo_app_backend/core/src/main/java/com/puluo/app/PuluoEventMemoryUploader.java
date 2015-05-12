package com.puluo.app;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import com.puluo.dao.PuluoEventDao;
import com.puluo.dao.PuluoEventMemoryDao;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoEvent;
import com.puluo.entity.PuluoEventMemory;
import com.puluo.entity.PuluoUser;
import com.puluo.entity.impl.PuluoEventMemoryImpl;
import com.puluo.result.ImageUploadServiceResult;
import com.puluo.service.PuluoService;
import com.puluo.util.FileIOUtil;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;

public class PuluoEventMemoryUploader {
	public static Log log = LogFactory.getLog(PuluoEventMemoryUploader.class);

	public static void main(String[] args) {
		PuluoEventDao dao = DaoApi.getInstance().eventDao();
		PuluoEvent e = dao.getEventByUUID("e1327090-d1ba-4d20-81ee-a505e05d8473");
		for(PuluoEventMemory m:e.memory()){
			System.out.println(m.imageURL());
		}
		
	}

	public static void upload() {
		File dir = new File("/Users/blshao/Desktop/puluo/events");
		PuluoEventDao dao = DaoApi.getInstance().eventDao();
		log.info("loading events ...");
		List<PuluoEvent> events = dao.findEvents(null, null, null, null, null,
				null, 0.0, 0.0, 0.0, null, null, 100000, 0);
		log.info(events.size() + " events are loaded!!");
		PuluoUser user = DaoApi.getInstance().userDao()
				.getByMobile("18646655333");
		String user_uuid = user.userUUID();
		PuluoEventMemoryDao memDao = DaoApi.getInstance().eventMemoryDao();
		for (File f : dir.listFiles()) {
			try {
				byte[] data = FileIOUtil.read(f.getAbsolutePath());
				String image_url = UUID.randomUUID().toString();
				ImageUploadServiceResult res = PuluoService.image.saveImage(
						data, image_url);
				if (res.isSuccess()) {
					log.info("successfully save image " + res.image_link);
					for (PuluoEvent e : events) {
						PuluoEventMemory mem = new PuluoEventMemoryImpl(UUID
								.randomUUID().toString(), image_url,
								e.eventUUID(), user_uuid, null);
						memDao.saveEventMemory(mem);
						log.info("save event mem " + mem.imageURL()
								+ " for event " + e.eventUUID());
					}
				} else {
					log.error("saving img " + image_url + " failed!");
				}
			} catch (IOException e) {
				log.error("unable to process file " + f.getAbsolutePath());
				e.printStackTrace();
			}
		}
	}

}
