package com.example.project.user.service;


import com.example.project.user.dto.CreateUserDto;
import com.example.project.user.dto.SignInDto;
import com.example.project.user.dto.UserDto;


public interface UserService {
	UserDto getUser(String id);
	void createUser(CreateUserDto createUserDto);
	String createToken(SignInDto signInDto);
}
