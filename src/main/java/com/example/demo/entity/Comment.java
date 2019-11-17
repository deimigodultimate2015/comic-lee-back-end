package com.example.demo.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "parent_id")
	private int parentId;
	
	@Column(length = 1000)
	@NotBlank
	private String content;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "timestamp without time zone", name="created_date")
	private Date createdDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@NotBlank
	private ComicInfo comic;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@NotBlank
	private User user;

	public Comment(int parentId, @NotBlank String content, Date createdDate, ComicInfo comic, User user) {
		super();
		this.parentId = parentId;
		this.content = content;
		this.createdDate = createdDate;
		this.comic = comic;
		this.user = user;
	}
	
}
