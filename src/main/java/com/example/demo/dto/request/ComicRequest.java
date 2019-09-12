package com.example.demo.dto.request;

import java.util.List;

import com.example.demo.dto.response.TagResponse;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ComicRequest {
	
	private String title;
	private String artist;
	private List<TagResponse> tags;
	private int uploaderId;
	
}
