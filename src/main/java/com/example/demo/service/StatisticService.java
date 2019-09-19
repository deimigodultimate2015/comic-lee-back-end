package com.example.demo.service;

import org.springframework.stereotype.Service;

@Service
public interface StatisticService {

	public byte[] getUploaderComicSummary(String uploaderName);
	
}
