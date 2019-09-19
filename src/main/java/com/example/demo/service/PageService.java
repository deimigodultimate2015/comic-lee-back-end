package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.response.PageLocalInfo;
import com.example.demo.dto.response.PageResponse;

@Service
public interface PageService {
	
	public boolean pageUpload(MultipartFile page, int index, int comicId);
	
	public List<PageResponse> getAllPages(int comicId);

	public String updateAllPages(int comicId, List<Integer> list);

	public PageLocalInfo getPageLocalInfo(int id);
	
}
