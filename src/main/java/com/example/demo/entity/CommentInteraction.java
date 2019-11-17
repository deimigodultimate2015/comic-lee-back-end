package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Table(name = "comment_interaction")
@Entity
public class CommentInteraction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	//Include 3 state: -1(dislike) 0(none) 1(like) another value == 0;
	@Column
	@Min(-1)
	@Max(1)
	private short state;
	
	@Column(name = "active_flag")
	private boolean activeFlag;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@NotNull
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@NotNull
	private Comment comment;

	public CommentInteraction(@Min(-1) @Max(1) short state, boolean activeFlag, User user, Comment comment) {
		super();
		this.state = state;
		this.activeFlag = activeFlag;
		this.user = user;
		this.comment = comment;
	}
	
}
