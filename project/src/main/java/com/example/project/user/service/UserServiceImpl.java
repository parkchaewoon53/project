package com.example.project.user.service;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.project.user.dto.CreateUserDto;
import com.example.project.user.dto.SignInDto;
import com.example.project.user.dto.UserDto;
import com.example.project.user.repository.UserMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
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
		if(user != null) {
			throw new AlreadyExistedUserException(errorMessagePropertySource.getAlreadyExistedUser());
		}
		
		createUserDto.setPassword(passwordEncoder.encode(createUserDto.getPassword()));
		userMapper.saveUser(createUserDto);
		userMapper.saveUserAuthority(createUserDto);
	}

	@Override
	public String createToken(SignInDto signInDto) {
		try {
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(signInDto.getId(), signInDto.getPassword());
			
			Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
			
			return tokenProvider.createToken(authentication);
		} catch (Exception ex) {
			throw new BadCredentialsException(errorMessagePropertySource.getBadCredentials());
		}
			
		}

	@Override
	public UserDto getUserByEmail(String email) {
		return userMapper.findByEmail(email);
	}

	@Override
	public void saveUser(CreateUserDto createUserDto) {
		UserDto user = getUserByEmail(createUserDto.getEmail());
		if(user != null) {
			throw new AlreadyExistedUserException(errorMessagePropertySource.getAlreadyExistedUser());
		}
		createUserDto.setPassword(passwordEncoder.encode(createUserDto.getPassword()));
		userMapper.createUser(createUserDto);
		userMapper.saveUserAuthority(createUserDto);
	}

//	@Override
//	public User findByEmail(String email) {
//		return userMapper.findByEmail(email);
//	}
//
//	@Override
//	public String loginWithKakao(String email) {
//		log.info("카카오 로그인 요청 email={}", email);
//		
//		User user = findByEmail(email);
//		String rawPassword;
//		
//		int[] intArray = {0,1,2,3,4,5,6,7,8,9};
//		String[] lowerArray = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
//		String[] strArray = {"!", "@", "#", "$", "%","^","&","*","(",")","_","+","{","}"};
//		String[] BigArray = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
//		
//		if(user == null) {
//			rawPassword = generateTemporaryPassword(intArray, lowerArray, strArray, BigArray);
//			System.out.println(rawPassword);
//			CreateUserDto newUser = new CreateUserDto();
//			newUser.setId(email);
//			newUser.setEmail(email);
//			newUser.setPassword(rawPassword);
//			
//			userMapper.saveUser(newUser);
//			
//			user = findByEmail(email);
//		} else {
//			throw new IllegalStateException("이미 가입된 유저는 카카오 로그인 전용 인증 처리 필요");
//		}
//		
//		if(user == null) {
//			UsernamePasswordAuthenticationToken authenticationToken =
//					new UsernamePasswordAuthenticationToken(user.getId(), user.getPassword());
//			
//			Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
//			return tokenProvider.createToken(authentication);
//		}
//		
//
//		    return "로그인성공";
//	}
//
//	@Override
//	public UserDto getUserByEmail(String email) {
//		return userMapper.findUserByEmail(email);
//	}
//	
//	@Override
//	public Authentication authenticate(UsernamePasswordAuthenticationToken authenticationToken) {
//	    try {
//	        return authenticationManagerBuilder.getObject().authenticate(authenticationToken);
//	    } catch (Exception e) {
//	        throw new BadCredentialsException(errorMessagePropertySource.getBadCredentials());
//	    }
//	}
//	
//	 private String generateTemporaryPassword(int[] intArray, String[] lowerArray, String[] strArray, String[] bigArray) {
//	        SecureRandom random = new SecureRandom();
//	        List<String> passwordChars = new ArrayList<>();
//
//	        int passwordLength = random.nextInt(5) + 8; // 0~4 + 8 = 8~12
//
//	        passwordChars.add(bigArray[random.nextInt(bigArray.length)]);
//	        passwordChars.add(lowerArray[random.nextInt(lowerArray.length)]);
//	        passwordChars.add(String.valueOf(intArray[random.nextInt(intArray.length)]));
//	        passwordChars.add(strArray[random.nextInt(strArray.length)]);
//
//	        List<String> allChars = new ArrayList<>();
//	        Collections.addAll(allChars, bigArray);
//	        Collections.addAll(allChars, lowerArray);
//	        for (int i : intArray) {
//	            allChars.add(String.valueOf(i));
//	        }
//	        Collections.addAll(allChars, strArray);
//
//	        while (passwordChars.size() < passwordLength) {
//	            passwordChars.add(allChars.get(random.nextInt(allChars.size())));
//	        }
//
//	        Collections.shuffle(passwordChars);
//
//	        StringBuilder password = new StringBuilder();
//	        for (String charStr : passwordChars) {
//	            password.append(charStr);
//	        }
//
//	        return password.toString();
//	    }
}