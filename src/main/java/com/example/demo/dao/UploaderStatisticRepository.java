package com.example.demo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.dto.response.UploaderComicSummary;
import com.example.demo.dto.response.ViewDay;

@Repository
public interface UploaderStatisticRepository {
	
	public List<ViewDay> getComicViewsPrevious7Days(int comicId);
	
	public List<UploaderComicSummary> getUploaderComicSummary(String displayName);
	
}
