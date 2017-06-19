package com.example.demo.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service(value ="emailNotificationService")
public class NotificationService implements INotificationService{
	
	private static final Logger log = Logger.getLogger(NotificationService.class);
	
	 @Autowired
	 private MailSender mailSender;
	 
	 @Autowired
	 private SimpleMailMessage templateMessage;
	 
	 @Value("${email.sendto}")
	 private String sendTo;

	 public void setMailSender(MailSender mailSender) {
	      this.mailSender = mailSender;
	 }

	 public void setTemplateMessage(SimpleMailMessage templateMessage) {
	      this.templateMessage = templateMessage;
	 }


	@Override
	public void notifyUsers() {		
		boolean sendMail=false;
		
		if(sendMail){
			SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
	        msg.setTo(sendTo);
	        msg.setText("Please verify the following files updated in the last hour");
	        try{
	            this.mailSender.send(msg);
	        }
	        catch (MailException ex) {
	           //Proceed
	           log.error("Error occuered while sending mail : "+ ex.getMessage());	 
	        }
		}else {
			log.info("No new files added in last hour");
		}
		
	}

}
