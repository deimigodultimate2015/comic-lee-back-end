package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface PageService {
	
	public boolean pageUpload(MultipartFile page, int index, int comicId);
	
	
}
