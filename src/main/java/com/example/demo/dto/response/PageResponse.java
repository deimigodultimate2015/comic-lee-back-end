package com.example.demo.dto.response;

import com.example.demo.entity.Page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse {

	private int id;
	private String name;
	private int index;
	
	public PageResponse(Page page) {
		this.id = page.getId();
		this.name = page.getName();
		this.index = page.getIndex();
	}
}
