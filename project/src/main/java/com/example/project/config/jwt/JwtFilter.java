package com.example.project.config.jwt;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import com.example.project.config.exception.ErrorDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {
	private final TokenProvider tokenProvider;
	public static final String AUTHORIZATION_HEADER = "Authorization";

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
		HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
		String token = extractTokenFromRequestHeader(httpServletRequest);
		String requestURI = httpServletRequest.getRequestURI();
		
		System.out.println(token);
		try {
			System.out.println(tokenProvider.isValidToken(token));
		} catch (Exception e) {
			e.printStackTrace();
		}

		// ✅ 특정 경로는 토큰 검사 없이 통과시키기
		if (requestURI.equals("/api/user/checkPassword")) {
			filterChain.doFilter(servletRequest, servletResponse);
			return;
		}

		if (StringUtils.hasText(token)) {
			try {
				if (tokenProvider.isValidToken(token)) {
					Authentication authentication = tokenProvider.getAuthentication(token);
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			} catch (Exception e) {
				httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				httpServletResponse.setContentType("application/json");

				ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), e.getMessage(), token);

				JavaTimeModule javaTimeModule = new JavaTimeModule();
				javaTimeModule.addSerializer(LocalDateTime.class,
						new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS")));
				String json = new ObjectMapper().registerModule(javaTimeModule).writeValueAsString(errorDetails);

				httpServletResponse.getWriter().write(json);
				return;
			}
		}

		filterChain.doFilter(servletRequest, servletResponse);
	}

	// Request Header 에서 토큰 정보를 추출
	private String extractTokenFromRequestHeader(HttpServletRequest request) {
		String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
		System.out.println("헤더에 전달되어 온 토큰: " + bearerToken);
		return StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ") ? bearerToken.substring(7) : null;
	}
}
