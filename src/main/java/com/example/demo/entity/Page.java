package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;

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
	
	@Column
	@Min(0)
	private int index;
	
	@Column(length = 260)
	private String uri;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private ComicInfo comic ;

	public Page(@Min(0) int index, String uri, ComicInfo comic) {
		super();
		this.index = index;
		this.uri = uri;
		this.comic = comic;
	}

}