package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.dto.StatusResponse;
import com.example.demo.dto.request.ComicRequest;
import com.example.demo.dto.response.ComicResponse;

@Service
public interface ComicService {

	public StatusResponse<ComicResponse> saveComicDTO(ComicRequest comicDTO);
	
}
