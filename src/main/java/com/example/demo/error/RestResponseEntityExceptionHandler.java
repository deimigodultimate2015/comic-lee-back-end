package com.example.demo.error;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.demo.error.custom.CustomInvalidInputException;
import com.example.demo.error.custom.CustomObjectAlreadyExist;
import com.example.demo.error.custom.CustomeObjectConstraintException;
import com.example.demo.error.custom.ObjectNotFoundException;


public class RestResponseEntityExceptionHandler 
	extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(value
			= {ObjectNotFoundException.class})
	protected ResponseEntity<Object> handleNotFound(
			RuntimeException ex, WebRequest request) {
		
		return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
		
	}

	@ExceptionHandler(value
			= {CustomeObjectConstraintException.class, CustomInvalidInputException.class})
	protected ResponseEntity<Object> handleConsraintViolate(
			CustomeObjectConstraintException  ex, WebRequest request) {
	    
		return handleExceptionInternal(ex, "", new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
		
	}
	
	@ExceptionHandler(value
			= {CustomInvalidInputException.class})
	protected ResponseEntity<Object> handleInvalidInput(
			RuntimeException ex, WebRequest request) {
		
		return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.PRECONDITION_FAILED, request);
		
	}
	
	
	@ExceptionHandler(value
			= {CustomObjectAlreadyExist.class})
	protected ResponseEntity<Object> handleExistedObject(
			RuntimeException ex, WebRequest request) {
		
		return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT, request);
	}
}
