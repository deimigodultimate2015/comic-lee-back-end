package com.example.demo.init.data;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.dao.UploaderRepository;
import com.example.demo.entity.Uploader;

@Component
public class InitUploader {

	@Autowired
	UploaderRepository uploaderRepository;
	
	@PostConstruct
	public void  fakeUploader() {
		for(int i = 0; i < 50; i++) {
			
			if(!uploaderRepository.existsByUsername("hanazuki"+i)) {

				Uploader uploader = new Uploader(
						"hanazuki"+i,
						"hanazu"+i,
						"hanazuki"+i+"@gmail.com",
						"hanzoteam",
						"hanazuki"+i
						);
				
				uploaderRepository.save(uploader);
				
			}
		}
	}
	
}
