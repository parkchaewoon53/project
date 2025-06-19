package com.example.project.config.security;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.project.user.dto.UserDto;
import com.example.project.user.repository.UserMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService{
	private final UserMapper userMapper;

	@Override
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
		UserDto user = userMapper.findUserById(id);
        List<SimpleGrantedAuthority> grantedAuthorities = user.getAuthorities().stream().map(SimpleGrantedAuthority::new).toList();
        return new User(user.getId(), user.getPassword(), grantedAuthorities);
	}
	
}
