 package com.example.project.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.example.project.config.exception.JwtAccessDeniedHandler;
import com.example.project.config.exception.JwtAuthenticationEntryPoint;
import com.example.project.config.jwt.JwtFilter;
import com.example.project.config.jwt.TokenProvider;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityFilterConfiguration {
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
	private final TokenProvider tokenProvider;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(req -> {
            req
                .requestMatchers("/api/user/sign-up").permitAll()
                .requestMatchers("/api/user/sign-in").permitAll()
                .requestMatchers("/api/user/sign-out").permitAll()
                .requestMatchers("/api/auth/kakao").permitAll()
                .requestMatchers("/mail/**").permitAll()
                .requestMatchers("/api/user/users/**").hasRole("USER")
                .requestMatchers("/api/user/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/user/check-userId").permitAll()  
                .requestMatchers("/api/green-object-list").permitAll()
                .requestMatchers("/api/FirstEnergy").permitAll()
                .requestMatchers("/api/product/**").permitAll()
                .requestMatchers("/*.jpg").permitAll()
                .requestMatchers("/api/cart").hasRole("USER")
                .requestMatchers("/api/user/checkPassword").permitAll()
                .requestMatchers("/api/user/byebye").permitAll()
                .requestMatchers("/api/user/deleteAddress").permitAll()
                .requestMatchers("/api/user/address").permitAll()
                .anyRequest().authenticated();
        });

        http.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.csrf(AbstractHttpConfigurer::disable);
        http.formLogin(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));
        
        http.exceptionHandling(ex-> ex.authenticationEntryPoint(jwtAuthenticationEntryPoint).accessDeniedHandler(jwtAccessDeniedHandler));
        http.addFilterBefore(new JwtFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}