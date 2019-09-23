package com.example.demo.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.response.PageLocalInfo;
import com.example.demo.dto.response.PageResponse;
import com.example.demo.service.PageService;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class PageAPI {
	
	@Autowired
	@Qualifier("BasePageService")
	PageService pageService;
	
	@GetMapping("/page/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable("id") int id) throws IOException {
    	
    	PageLocalInfo page = pageService.getPageLocalInfo(id);
    	File file = new File(page.getPath()+"/"+page.getName());
    	
        byte[] bytes = StreamUtils.copyToByteArray(new FileInputStream(file));

        return ResponseEntity
                .ok()
                .contentType(page.getMimeType().equalsIgnoreCase("png") ? MediaType.IMAGE_PNG : MediaType.IMAGE_JPEG)
                .body(bytes);
    }
	
    @PreAuthorize("hasRole('UPLOADER')")
	@PostMapping("/page")
	public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile page, @RequestParam("comicId") int comicId, @RequestParam("index") int index) {
		pageService.pageUpload(page, index, comicId);
		return new ResponseEntity<>("Page uploaded succesful", HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/pages/{comic_id}")
	public ResponseEntity<List<PageResponse>> getAllPages(@PathVariable("comic_id") int comicId) {
		return new ResponseEntity<>(pageService.getAllPages(comicId), HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/pages/{comic_id}")
	@PreAuthorize("hasRole('UPLOADER')")
	public ResponseEntity<String> updatePageStatus(@PathVariable("comic_id")int id, @RequestBody List<Integer> pages) {
		return new ResponseEntity<>(pageService.updateAllPages(id, pages), HttpStatus.ACCEPTED);
	}
	
}
