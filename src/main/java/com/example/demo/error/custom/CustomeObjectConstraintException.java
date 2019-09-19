package com.example.demo.error.custom;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

public class CustomeObjectConstraintException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public CustomeObjectConstraintException(String message) {
		super(message);
	}
	
	public CustomeObjectConstraintException(ConstraintViolationException ex) {
		
	    
	    super(converConstraintViolationException(ex));
	}
	
	public static String converConstraintViolationException(ConstraintViolationException ex) {
		StringBuilder message = new StringBuilder();
	    Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
	    for (ConstraintViolation<?> violation : violations) {
	      message.append(violation.getMessage()+"; ");
	    }
	    return message.toString();
	}
}
