package com.example.demo.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.TagRepository;
import com.example.demo.dto.response.TagResponse;
import com.example.demo.entity.Tag;
import com.example.demo.service.TagService;

@Service("BaseTagService")
public class BaseTagService implements TagService{
	
	@Autowired
	TagRepository tagRepository;
	
	public List<TagResponse> getAllTags() {
		List<TagResponse> tagRList = new ArrayList<>() ;
		
		tagRepository.findAll().forEach(tag -> 
			tagRList.add(mappedTagToTagResponse(tag)));
		
		return tagRList;
	}
	
	//==================================================================
	//Transform tag to tagDTO for response
	public TagResponse mappedTagToTagResponse(Tag tag) {
		if(tag != null) {
			return new TagResponse(
					tag.getId(), tag.getName());
		} else {
			return new TagResponse();
		}
	}

	
}
