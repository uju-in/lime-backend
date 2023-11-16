package com.programmers.bucketback.mail;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EmailSender {

	private final JavaMailSender javaMailSender;

	@Value("${spring.mail.username}")
	private String fromEmail;

	public String send(final String email) throws MessagingException {
		final String code = createCode();
		final MimeMessage message = createMessage(email, code);
		javaMailSender.send(message);

		return code;
	}

	private String createCode() {
		return UUID.randomUUID()
			.toString()
			.replace("-", "")
			.substring(0, 10);
	}

	private MimeMessage createMessage(
		final String toEmail,
		final String code
	) throws MessagingException {

		final String subject = "Bucket-Back 회원가입 인증 코드";
		String text = "";
		text += "<p>안녕하세요.</p>";
		text += "<p>Bucket-Back 인증 코드는 다음과 같습니다.</p>";
		text += "<h3>" + code + "</h3>";
		text += "<p>감사합니다.</p>";

		final MimeMessage message = javaMailSender.createMimeMessage();
		message.addRecipients(Message.RecipientType.TO, toEmail);
		message.setSubject(subject);
		message.setFrom(fromEmail);
		message.setText(text, "utf-8", "html");

		return message;
	}
}
