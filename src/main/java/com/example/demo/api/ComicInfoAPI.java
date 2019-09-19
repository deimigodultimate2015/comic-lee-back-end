package com.example.demo.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.request.ComicRequest;
import com.example.demo.dto.response.ComicResponse;
import com.example.demo.service.ComicService;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class ComicInfoAPI {
	
	@Autowired
	@Qualifier("BaseComicService")
	ComicService comicService;
	
	@PostMapping("/comic")
	public ResponseEntity<ComicResponse> storeComicInfo(@RequestBody ComicRequest comicInfo) {
		
		ComicResponse comicResponse = comicService.saveComicDTO(comicInfo);
		return new ResponseEntity<>(comicResponse, HttpStatus.CREATED);
		
	}
	
	@GetMapping("/comic/{id}")
	public ResponseEntity<ComicResponse> getComicInfo(@PathVariable("id") int id) {
		
		ComicResponse comicResponse = comicService.getComicById(id);
		return new ResponseEntity<>(comicResponse, HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/comics")
	public ResponseEntity<List<ComicResponse>> getAllComics() {
		return new ResponseEntity<>(comicService.getAllComics(), HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/comic/{comic_id}")
	public ResponseEntity<ComicResponse> updateComicInfo(@PathVariable("comic_id") int id, @RequestBody ComicRequest comicInfo) {
		return new ResponseEntity<>(comicService.updateComicInfoById(comicInfo, id), HttpStatus.ACCEPTED);
		
	}
}
