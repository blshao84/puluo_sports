package com.puluo.api.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.puluo.api.PuluoAPI;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;
import com.puluo.enumeration.PuluoAuthCodeType;
import com.puluo.enumeration.PuluoSMSType;
import com.puluo.result.ApiErrorResult;
import com.puluo.result.SMSServiceResult;
import com.puluo.service.PuluoService;
import com.puluo.service.util.JuheSMSResult;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.Strs;

public class SMSServiceAPI extends PuluoAPI<PuluoDSI, SMSServiceResult> {
	public static Log log = LogFactory.getLog(SMSServiceAPI.class);

	private final PuluoSMSType sms_type;
	private final String mobile;
	private final boolean mock;
	private final Map<String, String> content;

	public SMSServiceAPI(PuluoSMSType sms_type, String mobile, boolean mock,
			Map<String, String> content) {
		this(sms_type, mobile, mock, content, DaoApi.getInstance());
	}

	public SMSServiceAPI(PuluoSMSType sms_type, String mobile, boolean mock,
			Map<String, String> content, PuluoDSI dsi) {
		this.dsi = dsi;
		this.sms_type = sms_type;
		this.mobile = mobile;
		this.mock = mock;
		this.content = content;
	}

	public SMSServiceAPI(PuluoSMSType sms_type, String mobile, boolean mock) {
		this(sms_type, mobile, mock, DaoApi.getInstance());
	}

	public SMSServiceAPI(PuluoSMSType sms_type, String mobile, boolean mock,
			PuluoDSI dsi) {
		this.dsi = dsi;
		this.sms_type = sms_type;
		this.mobile = mobile;
		this.mock = mock;
		this.content = new HashMap<String, String>();
	}

	@Override
	public void execute() {
		switch (sms_type) {
		case UserRegistration:
			processRegistrationRequest();
			break;
			
		case PasswordReset:
			processPasswordResetRequest();
			break;
		case NamedEvent:

			break;

		case GenericEvent:

			break;

		default:
			break;
		}

	}

	private void processPasswordResetRequest() {
		processRequest(PuluoAuthCodeType.PasswordReset);
		
	}

	private void processRegistrationRequest() {
		processRequest(PuluoAuthCodeType.Registration);
		
	}

	private void processRequest(PuluoAuthCodeType authCodeType) {
		String code = randomCode();
		JuheSMSResult result = null;
		if(!mock){
			result = PuluoService.getSms().sendAuthCode(mobile, code);
		}
		if (mock || result.isSuccess()) {
			log.info(String.format("successful send %s sms message to %s", authCodeType,mobile));
			boolean success = dsi.authCodeRecordDao().upsertAuthCode(mobile, code,authCodeType);
			if (success) {
				this.rawResult = new SMSServiceResult(mobile, "success");
			} else {
				log.error("保存PuluoAuthCodeRecord, mobile={}失败", mobile);
				this.error = ApiErrorResult.getError(10);
			}
		} else
			this.error = ApiErrorResult.getError(9);
	}

	private String randomCode() {
		Random rand = new Random();
		int code = rand.nextInt(1000000);
		if (code < 100000)
			return Strs.join(999999 - code);
		else
			return Strs.join(code);
	}
	
	public boolean isSuccess() {
		if(rawResult==null) return false;
		else return rawResult.isSuccess();
	}
}