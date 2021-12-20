package com.lawencon.glexy.email;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.lawencon.glexy.helper.EmailHelper;

@Component
public class EmailHandler {
	
	@Autowired
	protected JavaMailSender mailSender;
	
	@Async
	public void sendSimpleMessage(String to, String subject, String header ,EmailHelper data) throws Exception{

		
		MimeMessage message = mailSender.createMimeMessage();
	     
	    MimeMessageHelper helper = new MimeMessageHelper(message, true);
	    
	    helper.setFrom("noreply@baeldung.com");
	    helper.setTo(to);
	    helper.setSubject(subject);
	    helper.setText("<!DOCTYPE html>"
	    		+ "<html>"
	    		+ "<head>"
	    		+ "    <style>"
	    		+ "        .body {\r\n"
	    		+ "            background-color: #235784;"
	    		+ "            width: 60vw;"
	    		+ "            height: 60vh;"
	    		+ "			   border-radius: 10px;"
	    		+ "            display: inline-block;"
	    		+ "            margin-left:  20px;"
	    		+ "        }"
	    		+ "        .body__email {"
	    		+ "            background-color: white;"
	    		+ "            width: 60%;"
	    		+ "            height: 60%;"
	    		+ "            display: inline-block;"
	    		+ "			   border: 5px #FFCA03 solid;"
	    		+ "			   border-radius: 10px;"
	    		+ "            margin-left:  20%;"
	    		+ "            margin-top:  10%;"
	    		+ "        }"
	    		+ "        .body__email h1 {"
	    		+ "            margin-top: 5%;"
	    		+ "          text-align: center;"
	    		+ "        }"
	    		+ "        .body__email h2 {"
	    		+ "          text-align: center;"
	    		+ "        }"
	    		+ "    </style>"
	    		+ "</head>"
	    		+ "<body>"
	    		+ "    <div class=\"body\">"
	    		+ "        <div class=\"body__email\">"
	    		+ "            <h1>"+header+"</h1>"
	    		+ "            <h2>"+data.getEmployeeName()+"</h2>"
	    		+ "            <h2>"+data.getValueName()+"</h2>"
	    		+ "            <h2>"+data.getExpiredDate()+"</h2>"
	    		+ "        </div>"
	    		+ "    </div>"
	    		+ "</body>"
	    		+ "</html>",true);
	    mailSender.send(message);
	}
	
	@Async
	@Scheduled(fixedDelay = 60000)
	public void scheduleFixedDelayTask() {
//	    System.out.println(
//	      "Fixed delay task - " + System.currentTimeMillis() / 1000);
	}

}
