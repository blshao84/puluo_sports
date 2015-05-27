package com.puluo.app;

import com.puluo.weichat.PuluoWechatTokenCache;
import com.puluo.weichat.WechatNewsContentItem;
import com.puluo.weichat.WechatNewsItem;
import com.puluo.weichat.WechatTextMediaListResult;
import com.puluo.weichat.WechatUserInfo;
import com.puluo.weichat.WechatUtil;

public class WechatTextViewer {

	public static void main(String[] args) {
		//dumpWechatText();
		String token = PuluoWechatTokenCache.token();
		WechatUserInfo user = WechatUtil.getUserInfo(token, "oNTPZs3zRWRyayWY7NDfBxo0m2dY");
		System.out.println(user);

	}

	public static void dumpWechatText() {
		String token = PuluoWechatTokenCache.token();
//		WechatPermMediaItemResult res = WechatUtil.getTextMedia(token, "jjDWGK-OKR98IkzcySpOMd3flhG2JL7WaHCzKt4IIW4");
		WechatTextMediaListResult res1 = WechatUtil.getTextMediaList(token);
		for (WechatNewsItem item : res1.item) {
			System.out.println(item.media_id);
			for (WechatNewsContentItem ci : item.content.news_item) {
				System.out.println("\t" + ci.title);
			}
		}
		
	}

}
