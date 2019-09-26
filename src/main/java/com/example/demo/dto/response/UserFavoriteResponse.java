package com.example.demo.dto.response;

import com.example.demo.entity.UserFavorite;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserFavoriteResponse {

	private String username;
	private String comicTitle;
	private boolean stillTrue;
	
	public UserFavoriteResponse(UserFavorite userFavorite) {
		this.username = userFavorite.getUser().getUsername();
		this.comicTitle = userFavorite.getComicInfo().getTitle();
		this.stillTrue = userFavorite.isStillTrue();
	}
}
