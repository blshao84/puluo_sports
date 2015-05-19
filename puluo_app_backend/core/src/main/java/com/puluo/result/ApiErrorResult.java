package com.puluo.result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.puluo.util.HasJSON;

public class ApiErrorResult extends HasJSON {
	private static HashMap<Integer, ApiErrorResult> error_registry = new HashMap<Integer, ApiErrorResult>();
	private static List<ApiErrorResult> errors = new ArrayList<ApiErrorResult>();

	static {
		errors.add(new ApiErrorResult(0, "no result", "rawResult is null", ""));
		errors.add(new ApiErrorResult(1, "系统支付错误", "Event不存在", ""));
		errors.add(new ApiErrorResult(2, "系统支付错误", "订单已取消", ""));
		errors.add(new ApiErrorResult(3, "系统支付错误", "订单中的用户id与该用户不匹配", ""));
		errors.add(new ApiErrorResult(51, "系统支付错误", "订单中Event已达到最大可注册人数", ""));
		errors.add(new ApiErrorResult(52, "系统支付错误", "订单已过期", ""));
		errors.add(new ApiErrorResult(4, "登陆错误", "invalid user", ""));
		errors.add(new ApiErrorResult(11, "登陆错误", "密码不匹配", ""));
		errors.add(new ApiErrorResult(12, "登陆错误", "保存session时出错", ""));
		errors.add(new ApiErrorResult(5, "注册错误", "用户已存在", ""));
		errors.add(new ApiErrorResult(6, "注册错误", "验证码错误", ""));
		errors.add(new ApiErrorResult(7, "注册错误", "验证码不匹配", ""));
		errors.add(new ApiErrorResult(8, "注册错误", "保存用户时出现错误", ""));
		errors.add(new ApiErrorResult(9, "系统服务错误", "发送短信失败", ""));
		errors.add(new ApiErrorResult(10, "系统服务错误", "保存验证码发送记录出现错误", ""));
		errors.add(new ApiErrorResult(13, "登出错误", "用户已经登出", ""));
		errors.add(new ApiErrorResult(14, "登出错误", "删除session时出错", ""));
		errors.add(new ApiErrorResult(15, "API参数不完整", "API参数不完整", ""));
		errors.add(new ApiErrorResult(16, "API参数错误", "API参数错误", ""));
		errors.add(new ApiErrorResult(17, "用户信息接口错误", "用户不存在", ""));
		errors.add(new ApiErrorResult(18, "更新密码接口错误", "用户不存在", ""));
		errors.add(new ApiErrorResult(19, "更新密码接口错误", "当前验证码错误", ""));
		errors.add(new ApiErrorResult(20, "更新密码接口错误", "更新密码失败", ""));
		// error_registry.put(21, new ApiErrorResult(21, "用户信息接口错误", "查找用户信息失败",
		// ""));
		errors.add(new ApiErrorResult(22, "用户信息接口错误", "更新用户信息失败", ""));
		// error_registry.put(23, new ApiErrorResult(23, "时间线信息接口错误", "查找时间线失败",
		// ""));
		errors.add(new ApiErrorResult(24, "时间线like接口错误", "添加时间线like失败", ""));
		errors.add(new ApiErrorResult(25, "时间线like接口错误", "删除时间线like失败", ""));
		errors.add(new ApiErrorResult(26, "时间线评论接口错误", "添加时间线评论失败", ""));
		errors.add(new ApiErrorResult(27, "时间线评论接口错误", "删除时间线评论失败", ""));
		errors.add(new ApiErrorResult(28, "活动信息接口错误", "活动信息不存在", ""));
		errors.add(new ApiErrorResult(29, "设置信息接口错误", "设置信息不存在", ""));
		errors.add(new ApiErrorResult(30, "设置信息接口错误", "更新设置信息失败", ""));
		errors.add(new ApiErrorResult(31, "发送消息接口错误", "用户发送消息失败", ""));
		errors.add(new ApiErrorResult(32, "请求好友接口错误", "用户不存在", ""));
		errors.add(new ApiErrorResult(33, "请求好友接口错误", "用户已经是好友", ""));
		errors.add(new ApiErrorResult(34, "请求好友接口错误", "已发送过请求给对方，等待对方批准", ""));
		errors.add(new ApiErrorResult(35, "通过好友接口错误", "好友申请不存在", ""));
		errors.add(new ApiErrorResult(36, "通过好友接口错误", "好友申请已被通过", ""));
		errors.add(new ApiErrorResult(37, "通过好友接口错误", "好友申请已被拒绝", ""));
		errors.add(new ApiErrorResult(38, "拒绝好友接口错误", "好友申请不存在", ""));
		errors.add(new ApiErrorResult(39, "拒绝好友接口错误", "好友申请已被通过", ""));
		errors.add(new ApiErrorResult(40, "拒绝好友接口错误", "好友申请已被拒绝", ""));
		errors.add(new ApiErrorResult(42, "请求好友接口错误", "已收到过对方的请求，等待你的批准", ""));
		errors.add(new ApiErrorResult(43, "活动信息接口错误", "EventInfo或EventLocation不存在", ""));
		errors.add(new ApiErrorResult(44, "图片上传接口错误", "上传图片失败", ""));
		errors.add(new ApiErrorResult(45, "图片上传接口错误", "用户不存在", ""));
		errors.add(new ApiErrorResult(46, "图片上传接口错误", "课程不存在", ""));
		errors.add(new ApiErrorResult(47, "黑名单更新接口错误", "请求的用户不存在", ""));
		errors.add(new ApiErrorResult(48, "黑名单更新接口错误", "操作未定义", ""));
		errors.add(new ApiErrorResult(49, "黑名单更新接口错误", "更新黑名单失败", ""));
		errors.add(new ApiErrorResult(50, "黑名单更新接口错误", "无法将用户自己加入黑名单", ""));
		errors.add(new ApiErrorResult(100, "系统错误", "处理请求时出现异常", ""));
		for(ApiErrorResult e:errors){
			error_registry.put(e.id, e);
		}
	}

	public static int totalErrors() {
		return error_registry.size();
	}

	public static List<ApiErrorResult> allErrors() {
		List<ApiErrorResult> dest = new ArrayList<ApiErrorResult>();
		for(ApiErrorResult e:errors){
			dest.add(e);
		}
		return dest;
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
