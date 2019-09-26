package com.example.demo.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_favorite_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserFavoriteDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private UserFavorite userFavorite;
	
	@Temporal(TemporalType.DATE)
	private Date favorDate;

	public UserFavoriteDetails(UserFavorite userFavorite, Date favorDate) {
		super();
		this.userFavorite = userFavorite;
		this.favorDate = favorDate;
	}
	
}
