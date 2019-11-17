package com.example.demo.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.request.ComicRequest;
import com.example.demo.dto.request.ViewRequest;
import com.example.demo.dto.response.ComicResponse;
import com.example.demo.dto.response.PaginatedUserComics;
import com.example.demo.dto.response.UserComics;
import com.example.demo.dto.response.ViewsReport;
import com.example.demo.service.ComicService;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class ComicInfoAPI {
	
	@Autowired
	@Qualifier("BaseComicService")
	ComicService comicService;
	
	@PostMapping("/comic")
	@PreAuthorize("hasRole('UPLOADER')")
	public ResponseEntity<ComicResponse> storeComicInfo(@RequestBody ComicRequest comicInfo) {
		
		ComicResponse comicResponse = comicService.saveComicDTO(comicInfo);
		return new ResponseEntity<>(comicResponse, HttpStatus.CREATED);
		
	}
	
	@GetMapping("/comic/{id}")
	public ResponseEntity<ComicResponse> getComicInfo(@PathVariable("id") int id) {
		
		ComicResponse comicResponse = comicService.getComicById(id);
		return new ResponseEntity<>(comicResponse, HttpStatus.ACCEPTED);
	}
	
	@PreAuthorize("hasRole('UPLOADER')")
	@GetMapping("/comics/{uploaderId}")
	public ResponseEntity<List<ComicResponse>> getAllComics(@PathVariable("uploaderId") int id) {
		return new ResponseEntity<>(comicService.getAllComics(id), HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/comic/{comic_id}")
	public ResponseEntity<ComicResponse> updateComicInfo(@PathVariable("comic_id") int id, @RequestBody ComicRequest comicInfo) {
		return new ResponseEntity<>(comicService.updateComicInfoById(comicInfo, id), HttpStatus.ACCEPTED);
		
	}
	
	@GetMapping("/user/comics")
	public ResponseEntity<PaginatedUserComics> getUserComics(@RequestParam(name = "page", defaultValue = "1") int pageIndex,
															 @RequestParam(name = "search", defaultValue = "") String keyword) {
		return new ResponseEntity<>(comicService.getUserComics(10 , pageIndex, keyword), HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/user/favorite/comics/{username}")
	public ResponseEntity<List<UserComics>> getUserFavorComics(@PathVariable("username") String username) {
		return new ResponseEntity<>(comicService.getUserFavorComics(username), HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/view")
	public ResponseEntity<String> countView(@RequestBody ViewRequest viewRequest) {
		comicService.countView(viewRequest);
		return ResponseEntity.ok("Sent");
	}
	
	@GetMapping("/views/{comicId}")
	public ResponseEntity<ViewsReport> countView(@PathVariable("comicId") int comicId) {
		return new ResponseEntity<>(comicService.getViewReport(comicId), HttpStatus.ACCEPTED);	
	}
}
