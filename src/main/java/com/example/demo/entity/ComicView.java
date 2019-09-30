package com.example.demo.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comic_view")
@Data @NoArgsConstructor @AllArgsConstructor
public class ComicView {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(columnDefinition = "timestamp without time zone")
	private Date viewDate;
	
	@ManyToOne
	private Reader reader;
	
	public ComicView(Reader reader) {
		this.reader = reader;
		this.viewDate = new Date();
	}
	
	public static boolean isCountLegit(long deviceTime) {
		long currentTime = new Date().getTime() / 1000;
		
		if((deviceTime+5) < currentTime) {
			return true;
		} else {
			return false;
		}
		
	}
}
