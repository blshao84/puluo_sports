package com.puluo.service.util;

import com.google.gson.Gson;
import com.puluo.util.Strs;

public class JuheSMSResult {

	public static class JuheSMSResultDetail {
		public int count;
		public int fee;
		public long sid;

		public JuheSMSResultDetail(int count, int fee, long sid) {
			this.count = count;
			this.fee = fee;
			this.sid = sid;
		}
		@Override
		public String toString() {
			return Strs.join("count:",count,";","fee:",fee,";","sid:",sid);
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + count;
			result = prime * result + fee;
			result = prime * result + (int) (sid ^ (sid >>> 32));
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
			JuheSMSResultDetail other = (JuheSMSResultDetail) obj;
			if (count != other.count)
				return false;
			if (fee != other.fee)
				return false;
			if (sid != other.sid)
				return false;
			return true;
		}
		
	}

	public String reason;
	public JuheSMSResultDetail result;
	public int error_code;// we use '_' to name value because the returned json use 'error_code' as key
	
	public JuheSMSResult(String reason, int count, int fee, long sid,
			int errorCode) {
		JuheSMSResultDetail result = new JuheSMSResultDetail(count,fee,sid);
		this.result = result;
		this.reason = reason;
		this.error_code = errorCode;
	}

	public static JuheSMSResult fromJson(String json) {
		Gson gson = new Gson();
		return gson.fromJson(json, JuheSMSResult.class);
	}

	public static JuheSMSResult error = new JuheSMSResult("", -1, -1, -1, -1);

	public String toJson() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
	
	@Override
	public String toString() {
		String resStr;
		if(result==null) resStr = "null"; else resStr = result.toString();
		return Strs.join("reason:",reason,";","errorCode:",error_code,";","result:{",resStr,"}");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + error_code;
		result = prime * result + ((reason == null) ? 0 : reason.hashCode());
		result = prime * result
				+ ((this.result == null) ? 0 : this.result.hashCode());
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
		JuheSMSResult other = (JuheSMSResult) obj;
		if (error_code != other.error_code)
			return false;
		if (reason == null) {
			if (other.reason != null)
				return false;
		} else if (!reason.equals(other.reason))
			return false;
		if (result == null) {
			if (other.result != null)
				return false;
		} else if (!result.equals(other.result))
			return false;
		return true;
	}

	public boolean isSuccess() {
		return error_code == 0;
	}
	
	

}
