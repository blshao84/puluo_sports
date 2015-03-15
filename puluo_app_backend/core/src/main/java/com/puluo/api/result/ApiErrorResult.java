package com.puluo.api.result;

import java.util.HashMap;

import com.puluo.util.HasJSON;

public class ApiErrorResult extends HasJSON {
	private static HashMap<Integer, ApiErrorResult> error_registry = new HashMap<Integer, ApiErrorResult>();
	static {
		error_registry.put(0, new ApiErrorResult(0, "no result",
				"rawResult is null", ""));
		error_registry.put(1, new ApiErrorResult(1, "系统支付错误", "Event不存在", ""));
		error_registry.put(2, new ApiErrorResult(2, "系统支付错误", "订单已取消", ""));
		error_registry.put(3, new ApiErrorResult(3, "系统支付错误",
				"订单中的用户id与该用户不匹配", ""));
		error_registry
				.put(4, new ApiErrorResult(4, "登陆错误", "invalid user", ""));
		error_registry.put(11, new ApiErrorResult(11, "登陆错误", "密码不匹配", ""));
		error_registry.put(12, new ApiErrorResult(12, "登陆错误", "保存session时出错",
				""));
		error_registry.put(5, new ApiErrorResult(5, "注册错误", "用户已存在", ""));
		error_registry.put(6, new ApiErrorResult(6, "注册错误", "验证码错误", ""));
		error_registry.put(7, new ApiErrorResult(7, "注册错误", "验证码不匹配", ""));
		error_registry.put(8, new ApiErrorResult(8, "注册错误", "保存用户时出现错误", ""));
		error_registry.put(9, new ApiErrorResult(9, "系统服务错误", "发送短信失败", ""));
		error_registry.put(10, new ApiErrorResult(10, "系统服务错误",
				"保存验证码发送记录出现错误", ""));
		error_registry.put(13, new ApiErrorResult(13, "登出错误", "用户已经登出", ""));
		error_registry.put(14, new ApiErrorResult(14, "登出错误", "删除session时出错",
				""));
		error_registry.put(15, new ApiErrorResult(15, "API参数不完整", "API参数不完整",
				""));
		error_registry
				.put(16, new ApiErrorResult(16, "API参数错误", "API参数错误", ""));
		error_registry.put(17, new ApiErrorResult(17, "用户信息接口错误", "用户不存在", ""));

	}

	public static ApiErrorResult getError(int id) {
		return error_registry.get(id);
	}

	public final int id;
	public final String error_type;
	public final String message;
	public final String url;

	private ApiErrorResult(int id, String error_type, String message, String url) {
		super();
		this.id = id;
		this.error_type = error_type;
		this.message = message;
		this.url = url;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((error_type == null) ? 0 : error_type.hashCode());
		result = prime * result + id;
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
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
		ApiErrorResult other = (ApiErrorResult) obj;
		if (error_type == null) {
			if (other.error_type != null)
				return false;
		} else if (!error_type.equals(other.error_type))
			return false;
		if (id != other.id)
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

}
