package com.example.demo.dto.response;

import com.example.demo.entity.Tag;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagResponse {
	
	private int id;
	private String name;
	
	public Tag tagResponseToTag(TagResponse tag) {
		return new Tag(tag.getId(), tag.getName());
	}
	
}
