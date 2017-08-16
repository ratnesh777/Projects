package com.mars.util;
import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.mars.exception.PortalException;

import freemarker.template.Configuration;


@Component
public class SendMailUtil {
	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private SimpleMailMessage passwdResetMail;

	@Autowired
	private Configuration freemarkerConfiguration;
	
	@Value("${base.path.uri}")
    private String BASE_PATH_URI;

	private static ClassPathResource COMPANY_LOGO = new ClassPathResource("template/company-logo.png");

	public String sendEmailwithCompanyLogo(String from, String to, String subject,
									 String freeMarkerHTMLTemplate,
									 HashMap<String, Object> freeMarkerHTMLTemplateParameterMap)
	{

		MimeMessagePreparator mail = new MimeMessagePreparator() {

			public void prepare(MimeMessage mimeMessage) throws Exception {

				try {
					MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true,"UTF-8");
					
					helper.setSubject(subject);
					helper.setFrom(from);
					helper.setTo(to);
					StringBuffer content = new StringBuffer();
					content.append(FreeMarkerTemplateUtils.processTemplateIntoString(
										freemarkerConfiguration.getTemplate(freeMarkerHTMLTemplate),
										freeMarkerHTMLTemplateParameterMap)
					);
					helper.setText(content.toString(), true);
					helper.addInline("logo", COMPANY_LOGO);
				} catch (Exception e) {
					throw new PortalException(e);
				}
			}
		};

		try {
			mailSender.send(mail);
			return "{\"result\":\"sent successfully\"}";
		} catch (MailException ex) {
			throw new PortalException(ex);
		}
	}
	
	/**
	 * sends mail for password reset
	 * 
	 * @param recipientMailId
	 *            - Reciept's Email Id
	 * @param messageContent
	 *            - Reset Password Mail Content
	 * @Return <code>String<code/> indicating send status
	 */
	public String sendPasswdResetMail(String recipientMailId, String messageContent)
			throws PortalException {
		passwdResetMail.setTo(recipientMailId);
		passwdResetMail.setText(messageContent);
		try {
			mailSender.send(passwdResetMail);
			return "{\"result\":\"sent successfully\"}";
		} catch (MailException ex) {
			throw new PortalException("Portal  exception  "+ex);
		}

	}



	public String getFreeMarkerTemplateContent(Map<String, Object> model) {
		StringBuffer content = new StringBuffer();
		try {
			content.append(FreeMarkerTemplateUtils
					.processTemplateIntoString(freemarkerConfiguration.getTemplate("notifyEmail.tfl"), model));
			return content.toString();
		} catch (Exception e) {
			throw new PortalException("Portal  exception  "+ e);
		}
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void setPasswdResetMail(SimpleMailMessage passwdResetMail) {
		this.passwdResetMail = passwdResetMail;
	}

	public void setFreemarkerConfiguration(Configuration freemarkerConfiguration) {
		this.freemarkerConfiguration = freemarkerConfiguration;
	}
	

}
