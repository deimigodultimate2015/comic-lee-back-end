package com.example.demo.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reader")
@Data @NoArgsConstructor
@AllArgsConstructor
public class Reader {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id ;  
	
	@Column(length = 36)
	private String uuid;
	
	@ManyToOne(fetch =  FetchType.LAZY)
	private ComicInfo comic;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "reader")
	private List<ComicView> comicViews = new ArrayList<>();
	
	@Column(name = "latest_view")
	private long latestView;
	
	public Reader(String uuid, int comicId, long latestView) {
		this.uuid = uuid;
		this.comic = new ComicInfo();
		this.comic.setId(comicId);
		this.latestView = latestView;
	}

	public Reader(String uuid, ComicInfo comic, long latestView) {
		super();
		this.uuid = uuid;
		this.comic = comic;
		this.latestView = latestView;
	}
	
	
	
}
