package com.example.demo.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.response.TagResponse;
import com.example.demo.service.TagService;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class TagAPI {

	@Autowired
	@Qualifier("BaseTagService")
	TagService tagService ;
	
	@GetMapping("/tags")
	public List<TagResponse> getAllTag() {
		return tagService.getAllTags();
	}
	
}
