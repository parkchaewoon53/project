package com.example.project.user.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDto {
	@NotNull
	@Size(max=40)
	private String id;
	
	@NotNull
	@Size(max=100)
	private String password;
	
	private String totalMileage;
	
	@NotNull
	@Size(max=40)
	private String email;
	
	private List<String> authorities;
}
