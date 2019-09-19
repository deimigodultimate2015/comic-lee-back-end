package com.example.demo.dto.response;

import com.example.demo.entity.Uploader;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploaderComicResponse {
	
	private int id;
	private String display;
	
	public UploaderComicResponse(Uploader uploader) {
		this.id = uploader.getId();
		this.display = uploader.getDisplayName();
	}
}
