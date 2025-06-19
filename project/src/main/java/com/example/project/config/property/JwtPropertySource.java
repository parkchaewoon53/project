package com.example.project.config.property;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Getter;

@Configuration
@PropertySource(value = "classpath:application.yml")
@Getter
public class JwtPropertySource {
	@Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.iss}")
    private String iss;

    @Value("${jwt.sub}")
    private String sub;

    @Value("${jwt.expiration-milliseconds}")
    private long expirationSeconds;

    @Value("${cookie.jwt.name}")
    private String cookieName;

    @Value("${cookie.jwt.http-only}")
    private boolean isEnabledHttpOnly;

    @Value("${cookie.jwt.secure}")
    private boolean isEnabledSecure;

    @Value("${cookie.jwt.path}")
    private String path;

    @Value("${cookie.jwt.maxAge}")
    private long maxAge;
}
