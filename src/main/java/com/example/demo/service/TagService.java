package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.response.TagResponse;

@Service
public interface TagService {
	public List<TagResponse> getAllTags();
}
