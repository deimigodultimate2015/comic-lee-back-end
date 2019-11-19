package com.example.demo.error.custom;

public class CustomInvalidAuthenticator extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CustomInvalidAuthenticator(String message) {
		super(message);
	}

}
