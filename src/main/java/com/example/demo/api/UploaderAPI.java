package com.example.demo.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.StatisticService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class UploaderAPI {
	
	@Autowired
	StatisticService statisticServ;

	@GetMapping("/statistic/uploadercomic/{display_name}")
	@PreAuthorize("hasRole('UPLOADER')")
	public ResponseEntity<byte[]> getUploaderComicResponse(@PathVariable("display_name") String name) {
		
		byte[] fileExcel =  statisticServ.getUploaderComicSummary(name);
		
		return ResponseEntity.ok().contentLength(fileExcel.length)
				.header("Content-disposition", "attachment;filename=uploader_comic_"+name+".xlsx")
				.contentType(MediaType.APPLICATION_OCTET_STREAM).body(fileExcel);
	}
	
	
}
