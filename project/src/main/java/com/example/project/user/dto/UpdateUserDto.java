package com.example.project.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserDto {
	private String id;
	
	@NotNull
	@Size(max = 40)
	private String password;
	
	@NotNull
	@Size(max = 40)
	private String email;
}
