package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.dto.request.FavoriteRequest;
import com.example.demo.dto.response.UserFavoriteResponse;

@Service
public interface UserFavoriteService {
	
	public UserFavoriteResponse favorite(FavoriteRequest favoriteRequest);
	
	public boolean isFavorited(FavoriteRequest favoriteRequest);
	
}
