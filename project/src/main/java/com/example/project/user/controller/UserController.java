package com.example.project.user.controller;

import java.net.URI;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.project.config.helper.CookieHelper;
import com.example.project.config.jwt.TokenDto;
import com.example.project.user.dto.CreateUserDto;
import com.example.project.user.dto.SignInDto;
import com.example.project.user.dto.UserDto;
import com.example.project.user.service.UserService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
	private final UserService userService;
	private final CookieHelper cookieHelper;
	
	@GetMapping("/check-userId")
	public ResponseEntity<Void> checkUserId(@RequestParam("id") String id) {
		System.out.println(id);
		UserDto user = userService.getUser(id);
		if(user != null) {
			return ResponseEntity.status(409).build(); 
		}
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/sign-up")
	public ResponseEntity<Void> signUp(@RequestBody CreateUserDto createUserDto) {
		userService.createUser(createUserDto);
		URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/check-userId").buildAndExpand(createUserDto.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PostMapping("/sign-in")
    public ResponseEntity<TokenDto> signIn(@RequestBody SignInDto signInDto) {
        String token = userService.createToken(signInDto);

        // 쿠키에 토큰 추가
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Set-Cookie", cookieHelper.makeJwtCookie(token));

        return new ResponseEntity<>(TokenDto.builder().token(token).build(), httpHeaders, HttpStatus.OK);
    }
	
	@PostMapping("/sign-out")
	public ResponseEntity<Void> signOut() {
		HttpHeaders httpHeaders = new HttpHeaders();
		cookieHelper.deleteJwtCookie(httpHeaders);
		return ResponseEntity.ok().headers(httpHeaders).body(null);
	}
}
