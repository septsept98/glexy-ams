package com.lawencon.glexy.email;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailHandler {
	
	@Autowired
	protected JavaMailSender mailSender;
	
	@Async
	public void sendSimpleMessage(String to, String subject, String text) throws Exception{

		
		MimeMessage message = mailSender.createMimeMessage();
	     
	    MimeMessageHelper helper = new MimeMessageHelper(message, true);
	    
	    helper.setFrom("noreply@baeldung.com");
	    helper.setTo(to);
	    helper.setSubject(subject);
	    helper.setText("<b>"+text+"</b>",true);
	    mailSender.send(message);
	}
	
	@Async
	@Scheduled(fixedDelay = 1000)
	public void scheduleFixedDelayTask() {
//	    System.out.println(
//	      "Fixed delay task - " + System.currentTimeMillis() / 1000);
	}

}
