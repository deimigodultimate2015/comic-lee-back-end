package com.example.demo.service.imp;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.dao.ComicInfoRepository;
import com.example.demo.dao.UploaderRepository;
import com.example.demo.dto.StatusResponse;
import com.example.demo.dto.request.ComicRequest;
import com.example.demo.dto.response.ComicResponse;
import com.example.demo.entity.ComicInfo;
import com.example.demo.entity.Tag;
import com.example.demo.service.ComicService;

@Service("BaseComicService")
public class BaseComicService implements ComicService {

	@Autowired
	ComicInfoRepository comicInfoRepository;
	
	@Autowired
	UploaderRepository uploaderRepository;
	
	@Override
	public StatusResponse<ComicResponse> saveComicDTO(ComicRequest comicDTO) {
		
		StatusResponse<ComicResponse> comicResponse = new StatusResponse<>();
		StatusResponse<ComicInfo> comicInfo = comicRequestToComicInfo(comicDTO);
		
		if(comicInfo.isSuccess()) {
			try {
				
				comicInfoRepository.save(comicInfo.getObject());
				comicResponse.setObject(new ComicResponse(comicDTO, comicInfo.getObject().getId()));
				comicResponse.setStatus(HttpStatus.CREATED);
				comicResponse.setMessage("Comic "+comicInfo.getObject().getId()+" created");
				
			} catch (Exception exception) {
				return new StatusResponse<>(
						HttpStatus.BAD_REQUEST, "Something wrong in constraint" ,exception.getMessage(), false);
			}
		} else {
			comicResponse.getStatusResponseStatus(comicInfo);
		}
		
		return comicResponse;
	}

	//=============================================================================================================
	
	public StatusResponse<ComicInfo> comicRequestToComicInfo(ComicRequest comicRequest) {
		
		StatusResponse<ComicInfo> statusResponse = new StatusResponse<>(); 
		
		//Port list<TagResponse> to set<Tag>
		Set<Tag> list = new HashSet<>();
		comicRequest.getTags().forEach(tag -> 
			list.add(new Tag(tag.getId(), tag.getName()))
		);
		
		//check if uploader not found
		if(!uploaderRepository.findById(comicRequest.getUploaderId()).isPresent()) {
			return new StatusResponse<>(HttpStatus.NOT_FOUND, "Uploader "+comicRequest.getUploaderId()+" Not Found" , false);
		}
		
		try {
			
			ComicInfo comicInfo = new ComicInfo();
			comicInfo.setTags(list);
			comicInfo.setTitle(comicRequest.getTitle());
			comicInfo.setUploadTime(new Date());
			comicInfo.setArtist(comicRequest.getArtist());

			statusResponse.setObject(comicInfo);
			
			statusResponse.setSuccess(true);
			
		} catch (Exception exception) {
			return new StatusResponse<>(HttpStatus.BAD_REQUEST, "Something wrong in constraint" ,exception.getMessage(), false);
		}
		
		return statusResponse;
	}

}
