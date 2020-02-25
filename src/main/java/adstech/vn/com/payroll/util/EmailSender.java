package adstech.vn.com.payroll.util;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailSender {
	private String strUseranme;
	private String strPassword;
	private String strHost;
	private String strPort;
	private String strMailApprover;
	private String strMailFrom;
	private String linkAccess;
	private String strMailSupport;
	private String strMailFromName = "Thông báo xin phép nghỉ";
	
	public EmailSender(String strUseranme, String strPassword, String strHost, String strPort, String strMailApprover,
			String strMailFrom, String linkAccess) {
	
		this.strUseranme = strUseranme;
		this.strPassword = strPassword;
		this.strHost = strHost;
		this.strPort = strPort;
		this.strMailApprover = strMailApprover;
		this.strMailFrom = strMailFrom;
		this.linkAccess = linkAccess;
	}
	public EmailSender() {
		this.strMailSupport ="anhpq.adsplus@gmail.com";
		this.strUseranme = "huonghv.adsplus@gmail.com";
		this.strMailFrom = "no_reply@adsplus.vn";
		this.strPassword = "huong20031999";
		this.strHost = "smtp.gmail.com";
		this.strPort ="587";
	}

	public void sendmail() throws AddressException, MessagingException, IOException {
		   Properties properties = new Properties();
		   properties.setProperty("mail.smtp.auth", "true");
		   properties.setProperty("mail.smtp.starttls.enable", "true");
		   properties.setProperty("mail.smtp.host",this.strHost);
		   properties.setProperty("mail.smtp.port", this.strPort);
		   properties.setProperty("mail.support", strMailSupport);
		   properties.setProperty("mail.smtp.auth", "true");
		   properties.setProperty("mail.smtp.starttls.enable", "true");
		   
		   
		   Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
		      protected PasswordAuthentication getPasswordAuthentication() {
		         return new PasswordAuthentication(strUseranme, strPassword);
		      }
		   });
		   MimeMessage msg = new MimeMessage(session);
		   msg.setFrom(new InternetAddress(strMailFrom, "Thông báo xin nghỉ"));

		   msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(strMailApprover));
		   msg.setRecipients(Message.RecipientType.BCC,properties.getProperty("mail.support"));
		   msg.setSubject("Đơn xin nghỉ","UTF-8");
		   msg.setContent("Đơn xing nghỉ", "text/html");
		   msg.setSentDate(new Date());

		   MimeBodyPart messageBodyPart = new MimeBodyPart();
		   messageBodyPart.setContent("Đương nghỉ", "text/html; charset=utf-8");

		   Multipart multipart = new MimeMultipart();
		   multipart.addBodyPart(messageBodyPart);
		   MimeBodyPart attachPart = new MimeBodyPart();

		   attachPart.setText(linkAccess);
		   multipart.addBodyPart(attachPart);
		   msg.setContent(multipart);
		   Transport.send(msg);   
		}
	public String getStrUseranme() {
		return strUseranme;
	}
	public void setStrUseranme(String strUseranme) {
		this.strUseranme = strUseranme;
	}
	public String getStrPassword() {
		return strPassword;
	}
	public void setStrPassword(String strPassword) {
		this.strPassword = strPassword;
	}
	public String getStrHost() {
		return strHost;
	}
	public void setStrHost(String strHost) {
		this.strHost = strHost;
	}
	public String getStrPort() {
		return strPort;
	}
	public void setStrPort(String strPort) {
		this.strPort = strPort;
	}
	public String getStrMailApprover() {
		return strMailApprover;
	}
	public void setStrMailApprover(String strMailApprover) {
		this.strMailApprover = strMailApprover;
	}
	public String getStrMailFrom() {
		return strMailFrom;
	}
	public void setStrMailFrom(String strMailFrom) {
		this.strMailFrom = strMailFrom;
	}
	public String getLinkAccess() {
		return linkAccess;
	}
	public void setLinkAccess(String linkAccess) {
		this.linkAccess = linkAccess;
	}
	public String getStrMailSupport() {
		return strMailSupport;
	}
	public void setStrMailSupport(String strMailSupport) {
		this.strMailSupport = strMailSupport;
	}
	
	
}
