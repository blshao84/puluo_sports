package com.puluo.test.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import main.java.com.UpYun;

import org.junit.Test;

import com.puluo.service.PuluoImageService;
 

public class ImageServiceResultTest {

	@Test
	public void testSaveImage() {
		UpYun mockUpYun = mock(UpYun.class);
		when(mockUpYun.writeFile(anyString(),any(byte[].class),anyBoolean())).thenReturn(true);
		PuluoImageService mockImageService = new PuluoImageService(mockUpYun);
		byte[] data = new byte[128];
		String filePath = "test.jpg";
		String json = mockImageService.saveImage(data, filePath);
		String expected = "{\"filePath\":\"test.jpg\",\"success\":true}";
		String msg = String.format("PuluoImageService.saveImage should return %s", expected);
		assertEquals(msg,json,expected);
	}
}
