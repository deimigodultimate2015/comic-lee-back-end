package com.example.demo.api;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;

import com.example.demo.dto.response.TagResponse;
import com.example.demo.service.TagService;

@RestController
@RequestMapping("/api")
public class TagAPI {

	@Autowired
	@Qualifier("BaseTagService")
	TagService tagService ;
	
	@GetMapping("/tags")
	public List<TagResponse> getAllTag() {
		return tagService.getAllTags();
	}
	
	@GetMapping(value = "/image", produces = 
		{MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
	public void getImage(HttpServletResponse response) throws IOException {
		
		InputStream imageFile = new ClassPathResource("uploader/page/Signup.PNG").getInputStream();
		
		response.setContentType(MediaType.IMAGE_PNG_VALUE);
		
		StreamUtils.copy(imageFile, response.getOutputStream());
		
	}
	
}
