package com.example.demo.dto.response;

import com.example.demo.dto.request.Signup;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Signin {
	
	private String username;
	private String email;
	private String displayName;
	
	public Signin(Signup signup) {
		this.username = signup.getUsername();
		this.email = signup.getEmail();
		this.displayName = signup.getDisplayName();
	}
}
