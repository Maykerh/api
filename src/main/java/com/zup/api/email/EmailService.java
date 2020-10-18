package com.zup.api.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * Serviço responsável pelo envio de emails
 */
@Component
public class EmailService {
	@Autowired
	private JavaMailSender emailSender;

	public void send(EmailMessage email) {
		SimpleMailMessage message = new SimpleMailMessage();

		message.setFrom("naoresponda@nossobanco.com");
		message.setTo(email.getTo());
		message.setSubject(email.getSubject());
		message.setText(email.getBody());

		this.emailSender.send(message);
	}
}
