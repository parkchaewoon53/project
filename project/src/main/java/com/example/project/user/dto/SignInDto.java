package com.example.project.user.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignInDto {
	
	@NotNull
	@Size(min = 1, max = 40)
	private String id;
	
	@NotNull
	@Size(min = 1, max = 100)
	private String password;
}
