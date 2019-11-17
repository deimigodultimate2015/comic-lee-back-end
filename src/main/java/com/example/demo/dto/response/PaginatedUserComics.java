package com.example.demo.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginatedUserComics {
	
	List<UserComics> data;
	double totalPage;
	int pageSize;
	int currentPage;
	String search;
}
