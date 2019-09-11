package com.example.demo.init.data;

import java.util.ArrayList;
import java.util.Collections;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.dao.TagRepository;
import com.example.demo.entity.Tag;

@Component
public class InitTag {

	@Autowired
	TagRepository tagRepository;
	
	private ArrayList<String> allTagS = new ArrayList<>();
	
	@PostConstruct
	public void initTag() {
		
		addTagS();
		allTagS.forEach(tags -> {
			if(!tagRepository.existsByName(tags)) {
				tagRepository.save(new Tag(tags));
			}
		});
	}
	
	public void addTagS() {
		String[] tagS = {"fantasy","romance","comedy","action","horror","adventure",
				"drama","school","mecha","sci-fi","slice of life"};
		Collections.addAll(allTagS, tagS);
	}
}
