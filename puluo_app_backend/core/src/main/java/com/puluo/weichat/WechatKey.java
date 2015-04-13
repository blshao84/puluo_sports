package com.puluo.weichat;

public class WechatKey {
	public String wechat_key;
	public String wechat_id;
	
	public WechatKey(String wechat_key, String wechat_id) {
		super();
		this.wechat_key = wechat_key;
		this.wechat_id = wechat_id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((wechat_id == null) ? 0 : wechat_id.hashCode());
		result = prime * result
				+ ((wechat_key == null) ? 0 : wechat_key.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WechatKey other = (WechatKey) obj;
		if (wechat_id == null) {
			if (other.wechat_id != null)
				return false;
		} else if (!wechat_id.equals(other.wechat_id))
			return false;
		if (wechat_key == null) {
			if (other.wechat_key != null)
				return false;
		} else if (!wechat_key.equals(other.wechat_key))
			return false;
		return true;
	}
	
	
}
