package com.example.demo.error.custom;

public class CustomInvalidInputException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public CustomInvalidInputException(String message) {
		super(message);
	}
	

}
