package com.example.project.email.service;

public interface AuthenticationService {
	void sendAuthenticationCode(String email);
	boolean verifyCode(String email, String inputCode);
}
