package com.example.demo.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.StatusResponse;
import com.example.demo.dto.request.ComicRequest;
import com.example.demo.dto.response.ComicResponse;
import com.example.demo.service.ComicService;
import com.example.demo.utility.ResponseEntityUtility;

@RestController
@RequestMapping("/api")
public class ComicInfoAPI {
	
	@Autowired
	@Qualifier("BaseComicService")
	ComicService comicService;
	
	@PostMapping("/comic")
	public ResponseEntity<?> storeComicInfo(@RequestBody ComicRequest comicInfo) {
		
		StatusResponse<ComicResponse> comicResponse = comicService.saveComicDTO(comicInfo);
		
		return ResponseEntityUtility.checkStatusResponse(comicResponse);
	}
	
}
