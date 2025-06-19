package com.example.project.user.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.config.jwt.TokenProvider;
import com.example.project.user.service.UserService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class KakaoAuthController {

    private final UserService userService;
    private final TokenProvider tokenProvider;

//    @PostMapping("/kakao")
//    public ResponseEntity<Map<String, String>> kakaoLogin(@RequestBody Map<String, Object> payload) {
//    	String email = String.valueOf(payload.get("email"));
//    	
//    	System.out.println(email);
//    	
//        String token = userService.loginWithKakao(email);
//
//        System.out.println(token);
//        Map<String, String> response = new HashMap<>();
//        response.put("token", token);
//
//        return ResponseEntity.ok(response);
//    }
}
