package com.example.project.email.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="AUTHENTICATION_FIRST")
public class EmailVerification {
	@Id
	private String email;
	
	private String token;
	
	@Column(name ="AUTHENTICATION_ACCEPT")
	private String isVerified;
}
