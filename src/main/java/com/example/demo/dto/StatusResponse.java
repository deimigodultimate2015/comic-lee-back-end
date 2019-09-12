package com.example.demo.dto;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

//To Transfer Object with status to check throught parent
public class StatusResponse<T> {

	private boolean isSuccess;
	private HttpStatus status;
	private String message;
	private String error;
	
	private T object;

	public StatusResponse(HttpStatus status, String message, String error, boolean isSuccess) {
		super();
		this.status = status;
		this.message = message;
		this.error = error;
		this.isSuccess = isSuccess;
	}

	public StatusResponse(HttpStatus status, String message, boolean isSuccess) {
		super();
		this.status = status;
		this.message = message;
		this.isSuccess = isSuccess;
	}
	
	public void getStatusResponseStatus(StatusResponse<?> statusResponse) {
		this.isSuccess = statusResponse.isSuccess;
		this.status = statusResponse.status;
		this.message = statusResponse.message;
		this.error = statusResponse.error;
	}
	
}
