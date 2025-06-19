package com.example.project.config.exception;

public class AlreadyExistedUserException extends RuntimeException{
	public AlreadyExistedUserException(String message) {
        super(message);
    } 
}
