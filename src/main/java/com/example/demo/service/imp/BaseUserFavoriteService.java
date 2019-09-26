package com.example.demo.service.imp;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.ComicInfoRepository;
import com.example.demo.dao.UserFavoriteDetailRepository;
import com.example.demo.dao.UserFavoriteRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.dto.request.FavoriteRequest;
import com.example.demo.dto.response.UserFavoriteResponse;
import com.example.demo.entity.ComicInfo;
import com.example.demo.entity.User;
import com.example.demo.entity.UserFavorite;
import com.example.demo.entity.UserFavoriteDetails;
import com.example.demo.error.custom.ObjectNotFoundException;
import com.example.demo.service.UserFavoriteService;

@Service
public class BaseUserFavoriteService implements UserFavoriteService {

	@Autowired
	UserFavoriteRepository userFavoriteRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ComicInfoRepository comicRepository;
	
	@Autowired
	UserFavoriteDetailRepository userFavoriteDetailRepository;
	
	@Override
	public UserFavoriteResponse favorite(FavoriteRequest favoriteRequest) {
		Optional<User> user = userRepository.findByUsername(favoriteRequest.getUsername());
		Optional<ComicInfo> comic = comicRepository.findById(favoriteRequest.getComicId());
		UserFavoriteResponse favoriteResponse ;
		
		if(!user.isPresent() && !comic.isPresent()) {
			throw new ObjectNotFoundException("Can not found user with username: " + 
					favoriteRequest.getUsername() + " or comic with id: " + favoriteRequest.getComicId());
		} else {
			
			//Save new to repository
			if(!userFavoriteRepository.existsByUserId(user.get().getId(), comic.get().getId())) {
				
				UserFavorite userFavorite = userFavoriteRepository.save(new UserFavorite(user.get(), comic.get(), true));
				favoriteResponse = new UserFavoriteResponse(userFavorite);
				
			} else {
				Optional<UserFavorite> userFavor = userFavoriteRepository.findByUserId(user.get().getId(), comic.get().getId());
				userFavor.get().setStillTrue(
						userFavor.get().isStillTrue() ? false : true);
				userFavoriteRepository.save(userFavor.get());
				
				favoriteResponse = new UserFavoriteResponse(userFavor.get());
				
				UserFavoriteDetails favorDetails = new UserFavoriteDetails(userFavor.get(), new Date());
				userFavoriteDetailRepository.save(favorDetails);
			}
			
		}
		
		return favoriteResponse;
	}

	@Override
	public boolean isFavorited(FavoriteRequest favoriteRequest) {
		Optional<User> user = userRepository.findByUsername(favoriteRequest.getUsername());
		Optional<ComicInfo> comic = comicRepository.findById(favoriteRequest.getComicId());
		
		if(!user.isPresent() && !comic.isPresent()) {
			throw new ObjectNotFoundException("Can not found user with username: " + 
					favoriteRequest.getUsername() + " or comic with id: " + favoriteRequest.getComicId());
		} else {
			return userFavoriteRepository.checkFavoriteState(user.get().getId(), comic.get().getId()); 
		}
	}
}
