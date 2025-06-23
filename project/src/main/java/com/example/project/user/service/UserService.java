package com.example.project.user.service;

import com.example.project.user.dto.CreateUserDto;
import com.example.project.user.dto.KakaoUserDto;
import com.example.project.user.dto.SignInDto;
import com.example.project.user.dto.UpdateUserDto;
import com.example.project.user.dto.UserDto;


public interface UserService {
	UserDto getUser(String id);
	void createUser(CreateUserDto createUserDto);
	String createToken(SignInDto signInDto);
	UserDto kakaoLogin(KakaoUserDto kakaoUserDto);
	boolean checkPassword(UserDto userDto);
	void deleteUser(String uId);
	void updateUserPassword(String id, UpdateUserDto updateUserDto);
	void updateUser(UpdateUserDto updateUserDto);
}
