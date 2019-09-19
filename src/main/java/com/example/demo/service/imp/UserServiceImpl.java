package com.example.demo.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.UserManualRepository;
import com.example.demo.dto.response.UserComics;
import com.example.demo.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserManualRepository userMRepo;
	
	@Override
	public List<UserComics> getUserComics() {
		return userMRepo.getUserComics();
	}
	
}
