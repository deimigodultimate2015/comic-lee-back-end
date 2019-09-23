package com.example.demo.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dao.UserRepository;
import com.example.demo.dto.request.SigninForm;
import com.example.demo.dto.request.Signup;
import com.example.demo.dto.response.JwtResponse;
import com.example.demo.dto.response.Signin;
import com.example.demo.entity.User;
import com.example.demo.error.custom.CustomObjectAlreadyExist;
import com.example.demo.security.jwt.JwtProvider;
import com.example.demo.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtProvider jwtProvider;
	
	@Override
	public Signin signupNewUser(Signup signupInfo) {
		if(userRepository.existsByUsername(signupInfo.getUsername())) {
			throw new CustomObjectAlreadyExist("Username: '"+signupInfo.getUsername()+"' Already taken");
		}
		
		if(userRepository.existsByEmail(signupInfo.getEmail())) {
			throw new CustomObjectAlreadyExist("Email: '"+signupInfo.getUsername()+"' Already taken");
		}
		
		encoder = new BCryptPasswordEncoder();
		
		User user = new User();
		user.setUsername(signupInfo.getUsername());
		user.setPassword(encoder.encode(signupInfo.getPassword()));
		user.setEmail(signupInfo.getEmail());
		user.setDisplayName(signupInfo.getDisplayName());
		
		userRepository.save(user);
		
		return new Signin(signupInfo);
	}

	@Override
	public JwtResponse signinUser(SigninForm signinForm, String loginRole) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						signinForm.getUsername() + "|" + loginRole,
						signinForm.getPassword()
						)
				);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String jwt = jwtProvider.generateJwtToken(authentication);
		return new JwtResponse(jwt, signinForm.getUsername(), authentication.getAuthorities());
	}
	
}
