package com.example.demo.utility;

import org.springframework.http.ResponseEntity;

import com.example.demo.dto.StatusResponse;

public class ResponseEntityUtility {

	public static ResponseEntity<?> checkStatusResponse(StatusResponse<?> statusResponse) {
		if(statusResponse.isSuccess()) {
			return new ResponseEntity<>(statusResponse.getObject(), statusResponse.getStatus());
		} else {
			return new ResponseEntity<>(statusResponse.getMessage(), statusResponse.getStatus());
		}
	}
	
}
