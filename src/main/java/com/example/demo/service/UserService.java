package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.response.UserComics;

@Service
public interface UserService {

	public List<UserComics> getUserComics();
	
}
