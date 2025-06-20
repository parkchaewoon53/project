package com.example.project.user.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.config.jwt.TokenDto;
import com.example.project.config.jwt.TokenProvider;
import com.example.project.user.dto.KakaoUserDto;
import com.example.project.user.dto.UserDto;
import com.example.project.user.repository.UserMapper;
import com.example.project.user.service.UserService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class KakaoAuthController {

	private final UserMapper userMapper;
	private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @PostMapping("/kakao")
    public ResponseEntity<TokenDto> kakaoLogin(@RequestBody KakaoUserDto kakaoUserDto) {
        // 1. 유저 로그인 또는 회원가입 처리
        UserDto userDto = userService.kakaoLogin(kakaoUserDto);
        System.out.println("유저 로그인 또는 회원가입 처리 완료");
        System.out.println(userDto);


        // 2. 이메일로 토큰 생성
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(
                userDto.getId(), null, authorities);
    	System.out.println("authenticationToken");
    	System.out.println(authenticationToken);
    	System.out.println(userDto);
    	
        String token = tokenProvider.createToken(authenticationToken);
        System.out.println("이메일로 토큰 생성");
        System.out.println(token);

        // 3. TokenDto 반환
        TokenDto tokenDto = TokenDto.builder().token(token).build();
        System.out.println("TokenDto 반환");
        System.out.println(tokenDto);
        return ResponseEntity.ok(tokenDto);
    }
}
