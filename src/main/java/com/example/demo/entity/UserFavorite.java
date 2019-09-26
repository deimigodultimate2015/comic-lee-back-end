package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_favorite")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserFavorite {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@OneToOne(fetch = FetchType.LAZY)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	private ComicInfo comicInfo;
	
	@Column(name = "still_true")
	private boolean stillTrue;

	public UserFavorite(User user, ComicInfo comicInfo, boolean stillTrue) {
		super();
		this.user = user;
		this.comicInfo = comicInfo;
		this.stillTrue = stillTrue;
	}
	
	public UserFavorite(int userId, int comicInfoId, boolean stillTrue) {
		User userF = new User();
		ComicInfo comic = new ComicInfo();
		
		userF.setId(userId);
		comic.setId(comicInfoId);
		
		this.user = userF;
		this.comicInfo = comic;
		this.stillTrue = stillTrue;
	}
	
}
	