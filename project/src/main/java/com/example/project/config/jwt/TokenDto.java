package com.example.project.config.jwt;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenDto {
	private String token;
}
