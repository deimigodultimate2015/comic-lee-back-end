package com.example.demo.security.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.dao.UploaderRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.entity.Uploader;
import com.example.demo.entity.User;
import com.example.demo.error.custom.ObjectNotFoundException;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	UploaderRepository uploaderRepository;
	
	@Autowired
	UserRepository userRepository;
	
	private boolean isUploader;
	
	private boolean isItNotLoadDirectlyToLoadUserByUsername = false;
	
	private String currentUsername;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetails userDetails ;
		
		if(!isItNotLoadDirectlyToLoadUserByUsername) {
			decideRoleFromRoleInUsername(username);
			this.currentUsername = username.split("\\|")[0];
		} else {
			this.currentUsername = username;
		}
	
		if(this.isUploader) {
			Uploader uploader = uploaderRepository.findByUsername(this.currentUsername.split("\\|")[0]).orElseThrow(()
					-> new ObjectNotFoundException("Not found username: " + this.currentUsername));
			
			userDetails = UserPrinciple.build(uploader);
		} else {
			User user = userRepository.findByUsername(this.currentUsername.split("\\|")[0]).orElseThrow(()
					-> new ObjectNotFoundException("Not found username: " + this.currentUsername));
			
			userDetails = UserPrinciple.build(user);
			
		}
		
		this.isItNotLoadDirectlyToLoadUserByUsername = false;
		return userDetails;
	
	}
	
	@Transactional
	public UserDetails loadUserByUsernameAndRole(String username, boolean isUploader) {
		this.isItNotLoadDirectlyToLoadUserByUsername = true;
		this.isUploader = isUploader;
		
		
		
		return this.loadUserByUsername(username);
	}
	
	//Username pattern is username|ROLE
	public void decideRoleFromRoleInUsername(String username) {
		String isAdmin = username.split("\\|")[1];
		
		if(isAdmin.equals("ADMIN")) {
			this.isUploader = true;
		} else {
			this.isUploader = false;
		}
	}

}
