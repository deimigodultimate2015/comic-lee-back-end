package com.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploaderComicSummary {

	String title;
	String uploadTime;
	String modifiedTime;
	long totalFavorite;
	long unusedPages;
	long usedPage;
	long totalView;
	
}
