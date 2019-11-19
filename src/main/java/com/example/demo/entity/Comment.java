package com.example.demo.entity;

import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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

	@Column(name = "remove_flag")
	private boolean removeFlag;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "timestamp without time zone", name="created_date")
	private Date createdDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@NotNull
	private ComicInfo comic;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@NotNull
	private User user;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "comment")
	private List<CommentInteraction> commentInteractions = new ArrayList<>();

	public Comment(int parentId, @NotBlank String content, Date createdDate, ComicInfo comic, User user) {
		super();
		this.parentId = parentId;
		this.content = content;
		this.createdDate = createdDate;
		this.comic = comic;
		this.user = user;
	}
	
}
