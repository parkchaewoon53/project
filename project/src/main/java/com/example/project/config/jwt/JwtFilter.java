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
public class JwtFilter extends GenericFilterBean{
	private final TokenProvider tokenProvider;
    public static final String AUTHORIZATION_HEADER = "Authorization";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String token = extractTokenFromRequestHeader(httpServletRequest);

        if (StringUtils.hasText(token)) {
            try {
                if (tokenProvider.isValidToken(token)) {
                    Authentication authentication = tokenProvider.getAuthentication(token);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception e) {
                // 토큰이 있지만 유효하지 않을 때만 401 반환
                httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                httpServletResponse.setContentType("application/json");

                ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), e.getMessage(), token);

                JavaTimeModule javaTimeModule = new JavaTimeModule();
                javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS")));
                String json = new ObjectMapper().registerModule(javaTimeModule).writeValueAsString(errorDetails);

                httpServletResponse.getWriter().write(json);
                return; // 여기서 멈춰야 에러 페이지 출력
            }
        }

        // 토큰이 없거나, 유효한 경우 정상 흐름
        filterChain.doFilter(servletRequest, servletResponse);
    }


    // Request Header 에서 토큰 정보를 추출
    private String extractTokenFromRequestHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        return StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ") ? bearerToken.substring(7) : null;
    }
}
