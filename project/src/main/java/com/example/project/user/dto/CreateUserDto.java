package com.example.project.user.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateUserDto {
	@NotNull
	@Size(max = 40)
	private String id;
	
	@NotNull
	@Size(max = 100)
	private String password;
	
	@NotNull
	@Size(max = 40)
	private String email;
	
	@NotNull
	private boolean admin;
}
