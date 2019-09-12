package com.example.demo.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.service.PageService;

@RestController
@RequestMapping("/api")
public class PageAPI {

	@Autowired
	@Qualifier("BasePageService")
	PageService pageService;
	
	public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile page, @RequestParam("comicId") int comicId, @RequestParam("index") int index) {
		return ResponseEntity.accepted().build();
	}
	
}
