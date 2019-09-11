package com.example.demo.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "page")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Page {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(length = 260)
	private String uri;
	
	@Column(length = 3)
	private String type;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "page")
	private List<ComicPage> comics = new ArrayList<>();

	public Page(String uri, String type, List<ComicPage> comics) {
		super();
		this.uri = uri;
		this.type = type;
		this.comics = comics;
	}
	
}
