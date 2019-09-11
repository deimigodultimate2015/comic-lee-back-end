package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comic_page")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComicPage {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "comic_id")
	private ComicInfo comic;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "page_id")
	private Page page;
	
	@Column()
	@Min(0)
	private int index;

	public ComicPage(ComicInfo comic, Page page, @Min(0) int index) {
		super();
		this.comic = comic;
		this.page = page;
		this.index = index;
	}
}
