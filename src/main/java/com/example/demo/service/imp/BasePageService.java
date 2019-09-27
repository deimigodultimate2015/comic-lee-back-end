package com.example.demo.service.imp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dao.ComicInfoRepository;
import com.example.demo.dao.PageRepository;
import com.example.demo.dto.response.PageLocalInfo;
import com.example.demo.dto.response.PageResponse;
import com.example.demo.entity.ComicInfo;
import com.example.demo.entity.Page;
import com.example.demo.error.custom.ObjectNotFoundException;
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
	
	
	//To return image info 
	@Override
	public PageLocalInfo getPageLocalInfo(int id) {
		Optional<Page> page = pageRepository.findById(id);
		PageLocalInfo pageLI = new PageLocalInfo();
		
		if(page.isPresent()) {
			pageLI.setMimeType(page.get().getName().replace(".", "|").split("|")[1].toUpperCase());
			pageLI.setName(page.get().getName());
			pageLI.setPath(path);
		} else {
			throw new ObjectNotFoundException("Can't find image with id "+id);
		}
		
		return pageLI;
	}
	
	
	//Store image info to database
	@Override
	public boolean pageUpload(MultipartFile page, int index, int comicId) {
		
		Optional<ComicInfo> comic = comicRepository.findById(comicId);
		if(comic.isPresent()) {
			
			Page page2 = new Page(index,"" ,false,comic.get());
			pageRepository.save(page2);
			
			page2.setName(storeImage(page, page2.getId()));
			pageRepository.save(page2);
			
		}
		
		return false;
	}
	
	//Store image info to local
	public String storeImage(MultipartFile page, int pageId) {
		rootLocation = Paths.get(path); //Get path
		
		try {
			if(page.isEmpty()) { //Check if not exist
				return "notfound.PNG";
			} else if (page.getContentType().equalsIgnoreCase("image/png") &&
					page.getContentType().equalsIgnoreCase("image/jpeg") &&
					page.getContentType().equalsIgnoreCase("image/jpg")) {
				return "wrongtype.PNG";
			}
			
			String fileContentType = page.getContentType();

			Files.copy(page.getInputStream(), this.rootLocation.resolve("PAGE"+pageId+"."+fileContentType.split("/")[1]),
					StandardCopyOption.REPLACE_EXISTING);
			
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
		return "PAGE"+pageId+"."+page.getContentType().split("/")[1];
	}

	//Return all page depend on comic id
	@Override
	public List<PageResponse> getAllPages(int comicId) {

		List<PageResponse> list = new ArrayList<>();
		List<Page> listPage = pageRepository.findAll();
		listPage.removeIf(page -> page.isRemoved());
		listPage.removeIf(page -> page.getComic().getId() != comicId);
		
		if(!listPage.isEmpty()) {
			listPage.forEach(page -> 
				list.add(new PageResponse(page))
			);
		}
		
		list.sort((p1, p2) -> Integer.compare(p1.getIndex(), p2.getIndex()));
		return list;
	}

	
	//Update image index construct in database
	@Override
	public String updateAllPages(int comicId, List<Integer> list) {
		List<Page> page = pageRepository.findAll();
		page.removeIf(pageToRemove 
				-> pageToRemove.getComic().getId() != comicId);
		
		page.forEach(pageToCheck -> 
			pageToCheck.setRemoved(true)
		);
		
		page.forEach(pageToCheck -> 
			list.forEach(id -> {
				if(pageToCheck.getId() == id) {
					pageToCheck.setRemoved(false);
					pageToCheck.setIndex(list.indexOf(id));
				}
			})
		);
		
		
		page.forEach(pageToSave -> 
			pageRepository.save(pageToSave)
		);
		
		ComicInfo cinf = this.comicRepository.findById(comicId).get();
		cinf.setModifiedTime(new Date());
		this.comicRepository.save(cinf);
		
		return "Update successfull";
	}
	
	
}
