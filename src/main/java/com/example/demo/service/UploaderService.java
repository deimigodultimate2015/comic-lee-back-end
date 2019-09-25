package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.dto.response.UploaderInfo;

@Service
public interface UploaderService {

	public UploaderInfo getUploaderInfoById(int id) ;
	
	public UploaderInfo getUploaderInfoByUsername(String username) ;
	
}
