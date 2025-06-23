package com.example.project.email.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.project.email.dto.AuthenticationCode;

@Mapper
public interface AuthenticationCodeMapper {
	// 1) 이메일로 사용자 아이디 조회
    String findUserIdByEmail(String email);

    // 2) 인증코드 저장
    int insertAuthenticationCode(AuthenticationCode authCode);

    // 3) 인증번호가 맞으면 AUTHENTICATION_ACCEPT 'T'로 업데이트
    int updateAuthenticationAccept(@Param("uId")String uId,@Param("code") String code);

    // 4) 인증여부 확인
    String findAuthenticationAccept(String uId);
}
