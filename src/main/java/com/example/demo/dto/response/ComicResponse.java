package com.example.demo.dto.response;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.example.demo.dto.request.ComicRequest;
import com.example.demo.entity.ComicInfo;
import com.example.demo.entity.Tag;
import com.example.demo.entity.Uploader;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ComicResponse {
	
	private String date;
	private int id;
	private String title;
	private String artist;
	private List<TagResponse> tags;
	private UploaderComicResponse uploader;
	
	public ComicResponse(ComicRequest comicRequest, int comicId, Uploader uploader) {
		this.id = comicId;
		this.title = comicRequest.getTitle();
		this.artist = comicRequest.getArtist();
		this.tags = comicRequest.getTags();
		this.uploader = new UploaderComicResponse(uploader);
	}
	
	public ComicResponse(ComicInfo comicInfo) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm aa");
		this.date = sdf.format(comicInfo.getUploadTime());
		this.id = comicInfo.getId();
		this.title = comicInfo.getTitle();
		this.artist = comicInfo.getArtist();
		this.tags = conversTagToTagResponse(comicInfo.getTags());
		this.uploader = new UploaderComicResponse(comicInfo.getUploader());
	}
	
	public List<TagResponse> conversTagToTagResponse(Set<Tag> list) {
		List<TagResponse> returnList = new ArrayList<>();
		list.forEach(tag -> 
			returnList.add(new TagResponse(tag.getId(), tag.getName()))
		);
		
		return returnList;
	}
	
}