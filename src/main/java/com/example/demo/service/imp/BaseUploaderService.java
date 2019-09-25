package com.example.demo.service.imp;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.UploaderRepository;
import com.example.demo.dto.response.UploaderInfo;
import com.example.demo.entity.Uploader;
import com.example.demo.error.custom.ObjectNotFoundException;
import com.example.demo.service.UploaderService;

@Service
public class BaseUploaderService implements UploaderService{

	@Autowired
	UploaderRepository uploaderRepository;

	@Override
	public UploaderInfo getUploaderInfoById(int id) {

		Optional<Uploader> uploader = uploaderRepository.findById(id);
		if(!uploader.isPresent()) {
			throw new ObjectNotFoundException("Not found uploader with id: " + id);
		} else {
			return new UploaderInfo(uploader.get());
		}
	}

	@Override
	public UploaderInfo getUploaderInfoByUsername(String username) {
		Optional<Uploader> uploader = uploaderRepository.findByUsername(username);
		if(!uploader.isPresent()) {
			throw new ObjectNotFoundException("Not found uploader with id: " + username);
		} else {
			return new UploaderInfo(uploader.get());
		}
	}
	
	
}
