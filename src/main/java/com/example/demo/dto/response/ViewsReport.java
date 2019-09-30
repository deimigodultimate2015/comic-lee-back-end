package com.example.demo.dto.response;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViewsReport {
	
	int totalViews;
	List<ViewDay> viewDays = new ArrayList<>();
	
}
