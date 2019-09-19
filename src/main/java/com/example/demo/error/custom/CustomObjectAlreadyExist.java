package com.example.demo.error.custom;

public class CustomObjectAlreadyExist extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CustomObjectAlreadyExist(String message) {
		super(message);
	}
	
}
