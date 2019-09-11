package com.example.demo.service.imp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dao.ComicInfoRepository;
import com.example.demo.dao.PageRepository;
import com.example.demo.entity.ComicInfo;
import com.example.demo.entity.Page;
import com.example.demo.service.PageService;

@Service("BasePageService")
public class BasePageService implements PageService{

	@Value("${file.page.upload-dir}")
	String path;
	
	@Autowired
	PageRepository pageRepository;
	
	@Autowired
	ComicInfoRepository comicRepository;
	
	private Path rootLocation;
	
	@Override
	public boolean pageUpload(MultipartFile page, int index, int comicId) {
		
		Optional<ComicInfo> comic = comicRepository.findById(comicId);
		if(comic.isPresent()) {
			
			Page page2 = new Page(index,"" ,comic.get());
			pageRepository.save(page2);
			

			
			page2.setUri(path+storeImage(page, page2.getId()));
			
		}
		
		
		
		return false;
	}
	
	public String storeImage(MultipartFile page, int pageId) {
		rootLocation = Paths.get(path);
		
		try {
			if(page.isEmpty()) {
				return "notfound.PNG";
			} else if (page.getContentType().equalsIgnoreCase("PNG") &&
					page.getContentType().equalsIgnoreCase("JPEG") &&
					page.getContentType().equalsIgnoreCase("JPG")) {
				return "wrongtype.PNG";
			}
			
			Files.copy(page.getInputStream(), this.rootLocation.resolve("PAGE"+pageId),
					StandardCopyOption.REPLACE_EXISTING);
			
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
		return page.getName()+"."+page.getContentType();
	}
	
}
