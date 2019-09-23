package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.dto.request.SigninForm;
import com.example.demo.dto.request.Signup;
import com.example.demo.dto.response.JwtResponse;
import com.example.demo.dto.response.Signin;

@Service
public interface UserService {

	public Signin signupNewUser(Signup signupInfo) ;
	public JwtResponse signinUser(SigninForm signinForm, String loginRole);
}
