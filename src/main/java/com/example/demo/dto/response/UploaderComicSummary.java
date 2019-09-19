package com.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploaderComicSummary {

	String uploader;
	String email;
	String team;
	String comicTitle;
	String artist;
	Long total;
	
}
