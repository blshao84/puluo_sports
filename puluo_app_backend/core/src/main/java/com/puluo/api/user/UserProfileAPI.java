package com.puluo.api.user;

import java.util.ArrayList;
import java.util.List;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.ApiErrorResult;
import com.puluo.api.result.MessageResult;
import com.puluo.api.result.RequestFriendResult;
import com.puluo.api.result.UserPrivateProfileResult;
import com.puluo.api.result.UserProfileResult;
import com.puluo.api.result.UserPublicProfileResult;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.PuluoUserDao;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoPrivateMessage;
import com.puluo.entity.PuluoUser;
import com.puluo.entity.PuluoFriendRequest;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.TimeUtils;

public class UserProfileAPI extends PuluoAPI<PuluoDSI, UserProfileResult> {
	public static Log log = LogFactory.getLog(UserProfileAPI.class);
	private final String mobileOrUUID;
	private final String reqUserUUID;// uuid of user that sends the request

	public UserProfileAPI(String mobileOrUUID, String reqUserUUID) {
		this(mobileOrUUID, reqUserUUID, DaoApi.getInstance());
	}

	public UserProfileAPI(String mobileOrUUID, String reqUserUUID, PuluoDSI dsi) {
		this.dsi = dsi;
		this.mobileOrUUID = mobileOrUUID;
		this.reqUserUUID = reqUserUUID;
	}

	@Override
	public void execute() {
		PuluoUserDao userdao = dsi.userDao();
		PuluoUser user = userdao.getByMobile(mobileOrUUID);
		if (user == null) {
			user = userdao.getByUUID(mobileOrUUID);
		}
		PuluoUser reqUser = userdao.getByUUID(reqUserUUID);
		if (user != null && reqUser!=null) {
			log.info(String.format("找到用户Mobile=%s,UUID=%s", user.mobile(),user.userUUID()));
			UserPublicProfileResult publicInfo = new UserPublicProfileResult(
					user.firstName(), 
					user.lastName(), 
					user.thumbnail(),
					user.largeImage(), 
					user.saying(), 
					user.likes(),
					user.banned(), 
					user.following(reqUserUUID),
					user.isCoach());
			UserPrivateProfileResult privateInfo;
			if(user.userUUID().equals(reqUserUUID)){
				privateInfo = new UserPrivateProfileResult(
				user.email(), 
				String.valueOf(user.sex()),
				TimeUtils.formatDate(user.birthday()), 
				user.occupation(),
				user.country(), 
				user.state(), 
				user.city(), 
				user.zip(),
				pending(user.pending()));
			} else privateInfo = UserPrivateProfileResult.empty();
			
			UserProfileResult result = new UserProfileResult(
					user.userUUID(),
					publicInfo,
					privateInfo,
					TimeUtils.dateTime2Millis(user.createdAt()),
					TimeUtils.dateTime2Millis(user.updatedAt()));
			rawResult = result;
		} else {
			log.error(String.format("用户%s或者%s不存在",mobileOrUUID,reqUserUUID));
			this.error = ApiErrorResult.getError(17);
		}
	}
	
	private List<RequestFriendResult> pending(List<PuluoFriendRequest> requests) {
		List<RequestFriendResult> pending = new ArrayList<RequestFriendResult>();
		List<MessageResult> messages_result;
		RequestFriendResult result;
		for (PuluoFriendRequest request: requests) {
			messages_result =  new ArrayList<MessageResult>();
			for(int i=0;i<request.messages().size();i++) {
				PuluoPrivateMessage msg = request.messages().get(i);
				PuluoUser fromUser = msg.fromUser();
				PuluoUser toUser = msg.toUser();
				messages_result.add(new MessageResult(msg.messageUUID(),
						fromUser.userUUID(),toUser.userUUID(),
						fromUser.firstName(),toUser.firstName(),
						fromUser.lastName(),toUser.lastName(),
						fromUser.thumbnail(),toUser.thumbnail(),
						msg.content(),TimeUtils.dateTime2Millis(msg.createdAt())));
			}
			result = new RequestFriendResult(request.requestUUID(),request.requestStatus().name(),
					messages_result,TimeUtils.dateTime2Millis(request.createdAt()),TimeUtils.dateTime2Millis(request.updatedAt()));
			pending.add(result);
		}
		return pending;
	}
}
