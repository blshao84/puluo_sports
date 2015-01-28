package com.puluo.service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import com.puluo.util.Log;
import com.puluo.util.LogFactory;

public class PuluoEmailService {
	private static final Log LOGGER = LogFactory
			.getLog(PuluoEmailService.class);
	private String host;
	private String username;
	private String password;

	public PuluoEmailService(String host, String username, String password) {
		this.host = host;
		this.username = username;
		this.password = password;
	}

	public boolean sendTextEmail(String from, String to, String subject,
			String content) {
		return sendEmailImpl(from, to, new ArrayList<String>(),
				new ArrayList<String>(), subject, content);
	}

	public boolean sendTextEmailFromSupport(String to, String subject,
			String content) {
		return sendEmailImpl("contact@puluosports.com", to,
				new ArrayList<String>(), new ArrayList<String>(), subject,
				content);
	}

	private boolean sendEmailImpl(String from, String to, List<String> cc,
			List<String> bcc, String subject, String content) {

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", host);

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(to));
			for (String bccItem : bcc) {
				message.setRecipients(Message.RecipientType.BCC,
						InternetAddress.parse(bccItem));
			}
			for (String ccItem : cc) {
				message.setRecipients(Message.RecipientType.CC,
						InternetAddress.parse(ccItem));
			}
			message.setSubject(MimeUtility.encodeText(subject, "utf-8", "Q"));
			message.setText(MimeUtility.encodeText(content, "utf-8", "Q"));
			Transport.send(message);
			LOGGER.info(String.format("成功发送email至%s", to));
			return true;

		} catch (MessagingException e) {
			LOGGER.error("发送email时发生错误");
			return false;
		} catch (UnsupportedEncodingException e) {
			LOGGER.error(String.format("subject=%s,content=%s 非utf-8编码",
					subject, content));
			return false;
		}
	}
}
