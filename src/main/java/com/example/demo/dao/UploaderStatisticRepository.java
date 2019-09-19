package com.example.demo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.dto.response.UploaderComicSummary;

@Repository
public interface UploaderStatisticRepository {
	
	public List<UploaderComicSummary> getUploaderComicSummary(String displayName);
	
}
