package com.example.project.user.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.project.user.dto.CreateUserDto;
import com.example.project.user.dto.UserDto;

@Mapper
public interface UserMapper {
	 UserDto findUserById(@Param("id") String id);
	 List<String> findAuthoritiesByUserId(@Param("u_id") String u_id);
	 int saveUser(CreateUserDto createUserDto);
	 void saveUserAuthority(CreateUserDto createUserDto);
	 int saveAdminAuthority(CreateUserDto createUserDto);
	 
	 UserDto findByEmail(String email);
	 void insertKakaoUser(CreateUserDto createUserDto);
	 void saveKakaoUser(CreateUserDto createUserDto);
	 void saveUserKakaoAuthority(CreateUserDto createUserDto);
}
