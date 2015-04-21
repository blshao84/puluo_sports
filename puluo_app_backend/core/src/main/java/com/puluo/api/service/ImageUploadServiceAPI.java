package com.puluo.api.service;

import java.util.List;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.ApiErrorResult;
import com.puluo.api.result.ImageUploadServiceResult;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;
import com.puluo.enumeration.PuluoImageType;
import com.puluo.service.PuluoService;

public class ImageUploadServiceAPI extends
		PuluoAPI<PuluoDSI, ImageUploadServiceResult> {

	public PuluoImageType image_type;
	public List<ImageUploadInput> images;

	public ImageUploadServiceAPI(PuluoImageType image_type,
			List<ImageUploadInput> images) {
		this(image_type, images, DaoApi.getInstance());
	}

	public ImageUploadServiceAPI(PuluoImageType image_type,
			List<ImageUploadInput> images, PuluoDSI dsi) {
		this.dsi = dsi;
		this.image_type = image_type;
		this.images = images;
	}

	@Override
	public void execute() {
		int size = images.size();
		if (size == 0) {
			this.error = ApiErrorResult.getError(20);
		} else if (size > 1) {
			this.error = ApiErrorResult.getError(21);
		} else {
			String fileName = images.get(0).fileName;
			byte[] image = images.get(0).data;
			this.rawResult = PuluoService.image.saveImage(image, fileName);
		}
	}
}