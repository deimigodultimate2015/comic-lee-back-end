package com.example.demo.service.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.ComicInfoRepository;
import com.example.demo.dao.UploaderRepository;
import com.example.demo.dto.request.ComicRequest;
import com.example.demo.dto.response.ComicResponse;
import com.example.demo.dto.response.TagResponse;
import com.example.demo.entity.ComicInfo;
import com.example.demo.entity.Tag;
import com.example.demo.error.custom.CustomObjectAlreadyExist;
import com.example.demo.error.custom.CustomeObjectConstraintException;
import com.example.demo.error.custom.ObjectNotFoundException;
import com.example.demo.service.ComicService;

@Service("BaseComicService")
public class BaseComicService implements ComicService {

	@Autowired
	ComicInfoRepository comicInfoRepository;
	
	@Autowired
	UploaderRepository uploaderRepository;
	
	@Override
	public ComicResponse saveComicDTO(ComicRequest comicDTO) {
		
		ComicInfo comicInfo = comicRequestToComicInfo(comicDTO);
		if(comicInfoRepository.existsByTitle(comicDTO.getTitle())) {
			throw new CustomObjectAlreadyExist("\"" + comicDTO.getTitle() + "\" title already exist");
		}
		
		try {
			
			comicInfo.setUploader(uploaderRepository.findById(comicDTO.getUploaderId()).get());
			comicInfo = comicInfoRepository.save(comicInfo);
			
		} catch (ConstraintViolationException ex) {
			throw new CustomeObjectConstraintException(ex);
		}
		
		return new ComicResponse(comicInfo);
	}

	//=============================================================================================================
	
	public ComicInfo comicRequestToComicInfo(ComicRequest comicRequest) {
		
		ComicInfo comicInfo ; 
		
		//Port list<TagResponse> to set<Tag>
		Set<Tag> list = new HashSet<>();
		if(!comicRequest.getTags().isEmpty()) {
			comicRequest.getTags().forEach(tag -> 
			list.add(new Tag(tag.getId(), tag.getName())));
		}
		
		if(!uploaderRepository.findById(comicRequest.getUploaderId()).isPresent()) {
			
			throw new ObjectNotFoundException("Not found uploader with ID: " + comicRequest.getUploaderId());
			
		}
		
		comicInfo = new ComicInfo();
		comicInfo.setTags(list);
		comicInfo.setTitle(comicRequest.getTitle());
		comicInfo.setUploadTime(new Date());
		comicInfo.setArtist(comicRequest.getArtist());
		
		return comicInfo;
	}
	
	public List<ComicResponse> getAllComics() {
		List<ComicResponse> list = new ArrayList<>();
		comicInfoRepository.findAll().forEach(comic -> {
			comic.setUploader(comic.getUploader());
			list.add(new ComicResponse(comic));
		});
		
		return list;
	}
	
//====================================================================================================
	@Override
	public ComicResponse getComicById(int id) {
		Optional<ComicInfo> comicInfo = comicInfoRepository.findById(id);
		if(!comicInfo.isPresent()) {
			throw new ObjectNotFoundException("Can not find comic with id "+id);
		}
		
		return new ComicResponse(comicInfo.get());
	}
	
//=====================================================================================================
	@Override
	public ComicResponse updateComicInfoById(ComicRequest comicInfo , int comicId) {
		Optional<ComicInfo> comicInf = comicInfoRepository.findById(comicId);
		if(!comicInf.isPresent()) {
			throw new ObjectNotFoundException("Not found comic with id: " + comicId);
		} else {
			
			if(! comicInf.get().getTitle().equalsIgnoreCase(comicInfo.getTitle())) {
				if (comicInfoRepository.existsByTitle(comicInfo.getTitle())) {
					throw new CustomObjectAlreadyExist("\"" + comicInfo.getTitle() + "\" title already exist");
				}
			}
			
			comicInf.get().setTitle(comicInfo.getTitle());
			comicInf.get().setArtist(comicInfo.getArtist());
			Set<Tag> tags = new HashSet<>();
			
			comicInfo.getTags().forEach(tag -> 
				tags.add(new TagResponse().tagResponseToTag(tag))
			);
			
			comicInf.get().setTags(tags);
			
			comicInfoRepository.save(comicInf.get());
			
		}
		
		return new ComicResponse(comicInf.get());
		
	}
	

}
