package com.example.demo.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.response.UploaderInfo;
import com.example.demo.service.StatisticService;
import com.example.demo.service.UploaderService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class UploaderAPI {
	
	@Autowired
	StatisticService statisticServ;
	
	@Autowired
	UploaderService uploaderServ;

	@PreAuthorize("hasRole('UPLOADER')")
	@GetMapping("/statistic/uploadercomic/{display_name}")
	public ResponseEntity<byte[]> getUploaderComicResponse(@PathVariable("display_name") String name) {
		
		byte[] fileExcel =  statisticServ.getUploaderComicSummary(name);
		
		return ResponseEntity.ok().contentLength(fileExcel.length)
				.header("Content-disposition", "attachment;filename=uploader_comic_"+name+".xlsx")
				.contentType(MediaType.APPLICATION_OCTET_STREAM).body(fileExcel);
	}	
	
	@GetMapping("/uploader/{username}")
	public ResponseEntity<UploaderInfo> getUploaderInfo(@PathVariable("username") String username) {
		return new ResponseEntity<>(uploaderServ.getUploaderInfoByUsername(username), HttpStatus.ACCEPTED);
	}
	
	
}
