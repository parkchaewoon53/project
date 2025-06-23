package com.example.project.email.service;

import java.io.UnsupportedEncodingException;
import java.util.Random;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.project.email.dto.AuthenticationCode;
import com.example.project.email.repository.AuthenticationCodeMapper;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
	private final AuthenticationCodeMapper mapper;
	private final JavaMailSender mailSender;

	@Override
	public void sendAuthenticationCode(String email) {
		String uId = mapper.findUserIdByEmail(email);
		if (uId == null) {
			throw new IllegalArgumentException("등록된 이메일이 없습니다.");
		}
		String code = generateCode(); // 인증번호 생성 메서드

		AuthenticationCode authCode = new AuthenticationCode();
		authCode.setUId(uId);
		authCode.setEmail(email);
		authCode.setCode(code);
		authCode.setAccept("F");

		mapper.insertAuthenticationCode(authCode);

		String subject = "[풀내음] 이메일 인증번호 발송";
		String content = "안녕하세요 풀내음입니다.\n\n" + "회원님의 인증번호는 " + code + " 입니다.\n" + "해당 인증번호를 입력하여 이메일 인증을 완료해주세요.";

		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");
			helper.setTo(email);
			helper.setFrom(new InternetAddress("lyg0095@naver.com", "풀내음"));
			helper.setSubject(subject);
			helper.setText(content);

			mailSender.send(message);
		} catch (MessagingException | UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException("이메일 전송 중 오류가 발생했습니다.", e);
		} 
	}

	public boolean verifyCode(String email, String inputCode) {
		String uId = mapper.findUserIdByEmail(email);
		if (uId == null) {
			throw new IllegalArgumentException("등록된 이메일이 없습니다.");
		}
		String currentAccept = mapper.findAuthenticationAccept(uId);
		if ("T".equals(currentAccept)) {
			throw new IllegalStateException("이미 인증된 아이디입니다.");
		}

		int updated = mapper.updateAuthenticationAccept(uId, inputCode);
		return updated > 0; // 인증번호 일치 시 1이 반환됨
	}

	private String generateCode() {
		Random random = new Random();
		int code = 100000 + random.nextInt(900000); // 6자리 숫자 생성
		return String.valueOf(code);
	}
}
