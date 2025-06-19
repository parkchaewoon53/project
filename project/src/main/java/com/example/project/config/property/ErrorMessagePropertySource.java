package com.example.project.config.property;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Getter;

@Configuration
@PropertySource(value = "classpath:error-message.properties")
@Getter
public class ErrorMessagePropertySource {
	 @Value("${error.message.alreadyExistedUser}")
	    private String alreadyExistedUser;
	 
	 @Value("${error.message.forbidden}")
	    private String forbidden;

	    @Value("${error.message.invalidSignature}")
	    private String invalidSignature;

	    @Value("${error.message.expiredToken}")
	    private String expiredToken;

	    @Value("${error.message.unsupportedToken}")
	    private String unsupportedToken;

	    @Value("${error.message.invalidToken}")
	    private String invalidToken;
	    
	    @Value("${error.message.badCredentials}")
	    private String badCredentials;
}
