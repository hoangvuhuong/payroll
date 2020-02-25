package adstech.vn.com.payroll.controller;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import adstech.vn.com.payroll.contract.ResponseContract;
import adstech.vn.com.payroll.model.Ticket;
import adstech.vn.com.payroll.repository.TicketRepository;
import adstech.vn.com.payroll.service.TicketService;
import adstech.vn.com.payroll.util.CommonConstant;

@RestController
@RequestMapping("/ticket")
public class TicketController {
	@Autowired
	TicketService ticketService;
	@PostMapping("/create-ticket")
	public ResponseContract<?> createTicket(@RequestBody Ticket ticket){
		return ticketService.create(ticket);
	}
	@PostMapping("/submit-ticket")
	public ResponseContract<?> submit(@RequestBody Map<String, Object> submit){
		return ticketService.submitTicket(submit);
	}
	@PostMapping("/approve-ticket")
	public ResponseContract<?> approve(@RequestBody Map<String, Object>  approve){
		return ticketService.approve(approve);
	}
	private void sendmail() throws AddressException, MessagingException, IOException {
		   Properties props = new Properties();
		   props.put("mail.smtp.auth", "true");
		   props.put("mail.smtp.starttls.enable", "true");
		   props.put("mail.smtp.host", "smtp.gmail.com");
		   props.put("mail.smtp.port", "587");
		   
		   Session session = Session.getInstance(props, new javax.mail.Authenticator() {
		      protected PasswordAuthentication getPasswordAuthentication() {
		         return new PasswordAuthentication("huonghv.adsplus@gmail.com", "huong20031999");
		      }
		   });
		   Message msg = new MimeMessage(session);
		   msg.setFrom(new InternetAddress("huonghv.adsplus@gmail.com", false));

		   msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("hoangvuhuong200399@gmail.com"));
		   msg.setSubject("Tutorials point email");
		   msg.setContent("Tutorials point email", "text/html");
		   msg.setSentDate(new Date());

		   MimeBodyPart messageBodyPart = new MimeBodyPart();
		   messageBodyPart.setContent("Tutorials point email", "text/html");

		   Multipart multipart = new MimeMultipart();
		   multipart.addBodyPart(messageBodyPart);
		   MimeBodyPart attachPart = new MimeBodyPart();

		   attachPart.setText("google.com");;
		   multipart.addBodyPart(attachPart);
		   msg.setContent(multipart);
		   Transport.send(msg);   
		}
}
