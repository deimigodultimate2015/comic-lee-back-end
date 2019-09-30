package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.request.ComicRequest;
import com.example.demo.dto.request.ViewRequest;
import com.example.demo.dto.response.ComicResponse;
import com.example.demo.dto.response.UserComics;
import com.example.demo.dto.response.ViewsReport;
import com.example.demo.entity.ComicInfo;

@Service
public interface ComicService {
	
	public ComicResponse saveComicDTO(ComicRequest comicDTO);
	
	public List<ComicResponse> getAllComics(int id);
	
	public ComicResponse getComicById(int id);

	public ComicResponse updateComicInfoById(ComicRequest comicInfo , int comicId);
	
	public List<UserComics> getUserComics();
	
	public List<UserComics> getUserFavorComics(String username);
	
	public void countView(ViewRequest viewRequest);
	
	public ViewsReport getViewReport(int comicId);
}
