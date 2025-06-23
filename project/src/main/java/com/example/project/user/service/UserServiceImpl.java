package com.example.project.user.service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.project.config.exception.AlreadyExistedUserException;
import com.example.project.config.jwt.TokenProvider;
import com.example.project.config.property.ErrorMessagePropertySource;
import com.example.project.user.dto.CreateUserDto;
import com.example.project.user.dto.KakaoUserDto;
import com.example.project.user.dto.SignInDto;
import com.example.project.user.dto.UpdateUserDto;
import com.example.project.user.dto.UserDto;
import com.example.project.user.repository.UserMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private final UserMapper userMapper;
	private final PasswordEncoder passwordEncoder;
	private final ErrorMessagePropertySource errorMessagePropertySource;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final TokenProvider tokenProvider;

	@Override
	public UserDto getUser(String id) {
		return userMapper.findUserById(id);
	}

	@Override
	public void createUser(CreateUserDto createUserDto) {
		log.info("회원가입 요청 id={}", createUserDto.getId());
		UserDto user = getUser(createUserDto.getId());
		if (user != null) {
			throw new AlreadyExistedUserException(errorMessagePropertySource.getAlreadyExistedUser());
		}

		createUserDto.setPassword(passwordEncoder.encode(createUserDto.getPassword()));
		userMapper.saveUser(createUserDto);
		userMapper.saveUserAuthority(createUserDto);
	}

	@Override
	public String createToken(SignInDto signInDto) {
		try {
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
					signInDto.getId(), signInDto.getPassword());

			Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

			return tokenProvider.createToken(authentication);
		} catch (Exception ex) { 
			throw new BadCredentialsException(errorMessagePropertySource.getBadCredentials());
		}

	}

	@Override
	public UserDto kakaoLogin(KakaoUserDto kakoUserService) {
		UserDto user = userMapper.findByEmail(kakoUserService.getEmail());
		System.out.println("kakaoLogin");
		System.out.println(user);
		String rawPassword;

		int[] intArray = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		String[] lowerArray = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q",
				"r", "s", "t", "u", "v", "w", "x", "y", "z" };
		String[] strArray = { "!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "_", "+", "{", "}" };
		String[] BigArray = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
				"S", "T", "U", "V", "W", "X", "Y", "Z" };

		if (user == null) {
			rawPassword = generateTemporaryPassword(intArray, lowerArray, strArray, BigArray);
			System.out.println(rawPassword);
			CreateUserDto newUser = new CreateUserDto();
			newUser.setId(kakoUserService.getEmail());
			newUser.setEmail(kakoUserService.getEmail());
			newUser.setPassword(passwordEncoder.encode(rawPassword));

			userMapper.saveKakaoUser(newUser);
			userMapper.saveUserAuthority(newUser);

			user = userMapper.findByEmail(kakoUserService.getEmail());
			user.setId(kakoUserService.getEmail());
			user.setEmail(kakoUserService.getEmail());
			user.setPassword(rawPassword);
			System.out.println("카카오로그인완료");
		} else {
			// throw new IllegalStateException("이미 가입된 유저는 카카오 로그인 전용 인증 처리 필요");
			System.out.println("카카오 이미 있음");
			System.out.println(user);
		}

		return user;
	}

	private String generateTemporaryPassword(int[] intArray, String[] lowerArray, String[] strArray,
			String[] bigArray) {
		SecureRandom random = new SecureRandom();
		List<String> passwordChars = new ArrayList<>();

		int passwordLength = random.nextInt(5) + 8; // 0~4 + 8 = 8~12

		passwordChars.add(bigArray[random.nextInt(bigArray.length)]);
		passwordChars.add(lowerArray[random.nextInt(lowerArray.length)]);
		passwordChars.add(String.valueOf(intArray[random.nextInt(intArray.length)]));
		passwordChars.add(strArray[random.nextInt(strArray.length)]);

		List<String> allChars = new ArrayList<>();
		Collections.addAll(allChars, bigArray);
		Collections.addAll(allChars, lowerArray);
		for (int i : intArray) {
			allChars.add(String.valueOf(i));
		}
		Collections.addAll(allChars, strArray);

		while (passwordChars.size() < passwordLength) {
			passwordChars.add(allChars.get(random.nextInt(allChars.size())));
		}

		Collections.shuffle(passwordChars);

		StringBuilder password = new StringBuilder();
		for (String charStr : passwordChars) {
			password.append(charStr);
		}

		return password.toString();
	}

	@Override
	public boolean checkPassword(UserDto userDto) {

		String savedPassword = userMapper.findPasswordById(userDto.getId());
		boolean matches = passwordEncoder.matches(userDto.getPassword(), savedPassword);
		return matches;
	}

	@Transactional
	@Override
	public void deleteUser(String uId) {
		userMapper.deleteRole(uId);
		userMapper.deleteCart(uId);
		userMapper.deleteAddress(uId);
		userMapper.deleteUser(uId);
	}
	
	@Override
	public void updateUserPassword(String id, UpdateUserDto updateUserDto) {
		updateUserDto.setId(id);
		updateUserDto.setPassword(passwordEncoder.encode(updateUserDto.getPassword()));
		userMapper.updatePw(updateUserDto);
	}

	@Override
	public void updateUser(UpdateUserDto updateUserDto) {
		if(updateUserDto.getPassword() != null && !updateUserDto.getPassword().isEmpty()) {
			updateUserDto.setPassword(passwordEncoder.encode(updateUserDto.getPassword()));
			userMapper.updatePw(updateUserDto);
		}
		
		if(updateUserDto.getEmail() != null && !updateUserDto.getEmail().isEmpty()) {
			userMapper.updateEmail(updateUserDto);
		}
	}

}