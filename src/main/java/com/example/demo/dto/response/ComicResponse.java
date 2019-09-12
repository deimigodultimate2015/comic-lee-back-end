package com.example.demo.dto.response;

import java.util.List;

import com.example.demo.dto.request.ComicRequest;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ComicResponse {
	
	private int id;
	private String title;
	private String artist;
	private List<TagResponse> tags;
	private int uploaderId;
	
	public ComicResponse(ComicRequest comicRequest, int comicId) {
		this.id = comicId;
		this.title = comicRequest.getTitle();
		this.artist = comicRequest.getArtist();
		this.tags = comicRequest.getTags();
		this.uploaderId = comicRequest.getUploaderId();
	}
	
}