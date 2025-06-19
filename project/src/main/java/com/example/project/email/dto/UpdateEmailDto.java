package com.example.project.email.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class UpdateEmailDto {
	@JsonIgnore
	private int id;
	
	private String email;
	private String isVerified;
	private LocalDateTime localDateTime;
	private String token;
}
