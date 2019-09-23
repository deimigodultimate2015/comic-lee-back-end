package com.example.demo.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.request.SigninForm;
import com.example.demo.dto.request.Signup;
import com.example.demo.dto.response.JwtResponse;
import com.example.demo.dto.response.Signin;
import com.example.demo.service.UserService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class UserAPI {
	
	@Autowired
	UserService userService;
	
	@PostMapping("/auth/user/register")
	public ResponseEntity<Signin> signupNewUser(@RequestBody Signup signup) {
		return new ResponseEntity<>(userService.signupNewUser(signup), HttpStatus.ACCEPTED);
	}
	
	@PostMapping("auth/user/login")
	public ResponseEntity<JwtResponse> signinUser(@RequestBody SigninForm form) {
		return new ResponseEntity<>(userService.signinUser(form, "USER"), HttpStatus.ACCEPTED);
	}
	
	@PostMapping("auth/admin/login")
	public ResponseEntity<JwtResponse> signinAdmin(@RequestBody SigninForm form) {
		return new ResponseEntity<>(userService.signinUser(form, "ADMIN"), HttpStatus.ACCEPTED);
	}
}
