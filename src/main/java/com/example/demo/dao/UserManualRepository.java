package com.example.demo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.dto.response.UserComics;

@Repository
public interface UserManualRepository {
	
	public List<UserComics> getUserComics();
	
}
