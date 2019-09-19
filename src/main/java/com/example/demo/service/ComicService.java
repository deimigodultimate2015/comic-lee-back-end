package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.request.ComicRequest;
import com.example.demo.dto.response.ComicResponse;

@Service
public interface ComicService {

	public ComicResponse saveComicDTO(ComicRequest comicDTO);
	
	public List<ComicResponse> getAllComics();
	
	public ComicResponse getComicById(int id);

	public ComicResponse updateComicInfoById(ComicRequest comicInfo , int comicId);
}
