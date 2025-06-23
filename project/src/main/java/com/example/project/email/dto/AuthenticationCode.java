package com.example.project.email.dto;

import lombok.Data;

@Data
public class AuthenticationCode {
	private String uId;
	private String email;
	private String code;
	private String accept;
}
