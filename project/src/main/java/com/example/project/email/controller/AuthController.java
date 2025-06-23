package com.example.project.email.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.email.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
public class AuthController {
	private final AuthenticationService authenricationService;
	
	@PostMapping("/sendCode")
    public ResponseEntity<String> sendCode(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        try {
        	authenricationService.sendAuthenticationCode(email);
            return ResponseEntity.ok("인증번호가 이메일로 전송되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/verifyCode")
    public ResponseEntity<String> verifyCode(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String code = body.get("code");
        System.out.println(email);
        System.out.println(code);

        try {
            boolean success = authenricationService.verifyCode(email, code);
            if (success) {
                return ResponseEntity.ok("인증이 완료되었습니다.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("인증번호가 일치하지 않습니다.");
            }
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
