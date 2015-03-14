package com.puluo.api.service;

import java.util.HashMap;
import java.util.Random;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.ApiErrorResult;
import com.puluo.api.result.SMSServiceResult;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;
import com.puluo.service.PuluoService;
import com.puluo.service.util.JuheSMSResult;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.Strs;

public class SMSServiceAPI extends PuluoAPI<PuluoDSI, SMSServiceResult> {
	public static Log log = LogFactory.getLog(SMSServiceAPI.class);

	private final PuluoSMSType sms_type;
	private final String mobile;
	private final HashMap<String, String> content;

	public SMSServiceAPI(PuluoSMSType sms_type, String mobile,
			HashMap<String, String> content) {
		this(sms_type, mobile, content, DaoApi.getInstance());
	}

	public SMSServiceAPI(PuluoSMSType sms_type, String mobile,
			HashMap<String, String> content, PuluoDSI dsi) {
		this.dsi = dsi;
		this.sms_type = sms_type;
		this.mobile = mobile;
		this.content = content;
	}

	@Override
	public void execute() {
		switch (sms_type) {
		case UserRegistration:
			processRegistrationRequest();
			break;

		case NamedEvent:

			break;

		case GenericEvent:

			break;

		default:
			break;
		}

	}

	private void processRegistrationRequest() {
		String code = randomCode();
		JuheSMSResult result = PuluoService.sms.sendAuthCode(mobile, code);
		if(result.isSuccess()){
			boolean success = dsi.authCodeRecordDao().upsertRegistrationAuthCode(mobile, code);
			if(success){
				this.rawResult = new SMSServiceResult(mobile,"success");
			} else {
				log.info("保存PuluoAuthCodeRecord, mobile={}失败",mobile);
				this.error = ApiErrorResult.getError(10);
			}
		} else this.error = ApiErrorResult.getError(9);
	}

	private String randomCode() {
		Random rand = new Random();
		int code = rand.nextInt(1000000);
		if (code < 100000)
			return Strs.join(999999 - code);
		else
			return Strs.join(code);
	}
}