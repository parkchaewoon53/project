package com.example.project.email.controller;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.email.dto.EmailRequest;
import com.example.project.email.entity.EmailVerification;
import com.example.project.email.repository.EmailVerificationRepository;
import com.example.project.email.service.MailService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mail")
public class MailController {
    private final MailService mailService;
    private final EmailVerificationRepository emailVerificationRespository;

    @PostMapping("/send")
    public ResponseEntity<String> sendMail(@RequestBody EmailRequest request) {
        String email = request.getEmail();
        
        Optional<EmailVerification> existing = emailVerificationRespository.findByEmail(email);
        if(existing.isPresent() && "T".equals(existing.get().getIsVerified())) {
        	return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 인증된 이메일 입니다.");
        }
        String token = UUID.randomUUID().toString();
        String link = "http://localhost:8080/mail/verify?token=" + token;

        String content = "<p style='margin-bottom: 12px;'> 안녕하세요 풀내음입니다. </p>" +"<p style='margin-bottom: 12px;'>아래 버튼을 눌러 이메일 인증을 완료하세요</p>" +
                "<a href='" + link + "' style='padding:10px 5px; margin-top: 20px; background:#4CAF50; color:#fff; text-decoration:none;'>이메일 인증</a>";

        // From JPA to Mybatis
        EmailVerification emailVerification = new EmailVerification();
        emailVerification.setEmail(email);
        emailVerification.setToken(token);
        emailVerification.setIsVerified("F");
        emailVerificationRespository.save(emailVerification);
       
        mailService.sendMail(email, "이메일 인증 요청", content);
        return ResponseEntity.ok("메일 전송 완료");
    }

    @GetMapping("/verify")
    public void verify(@RequestParam("token") String token, HttpServletResponse response) throws IOException {
        Optional<EmailVerification> opt = emailVerificationRespository.findByToken(token);
        if(opt.isPresent()) {
            EmailVerification verification = opt.get();
                verification.setIsVerified("T");
                emailVerificationRespository.save(verification);
                response.sendRedirect("http://localhost:5173/verify-success");
                return;
            }
        response.sendRedirect("http://localhost:5173/error");
    }
}
