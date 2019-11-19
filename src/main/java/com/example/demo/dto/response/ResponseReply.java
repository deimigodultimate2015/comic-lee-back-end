package com.example.demo.dto.response;

import java.util.Date;

import com.example.demo.entity.Comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseReply {

	private int id;
	private String content;
	private Date createdDate;
	private int point;
	private int parentId;
	private short state;
	
	public ResponseReply(Comment comment) {
		this.state = 0;
		this.id = comment.getId();
		this.content = comment.getContent();
		this.createdDate = comment.getCreatedDate();
		this.parentId = comment.getParentId();
		comment.getCommentInteractions().forEach(interaction -> {
			if(interaction.getState() == 1) {
				point += 1;
			}
		});
	}
	
	
	public ResponseReply(Comment comment, String username) {
		this.state = 0;
		this.id = comment.getId();
		this.content = comment.getContent();
		this.createdDate = comment.getCreatedDate();
		this.parentId = comment.getParentId();
		comment.getCommentInteractions().forEach(interaction -> {
			if(interaction.getState() == 1) {
				point += 1;
			}
			
			if(interaction.getUser().getUsername().equals(username)) {
				state = interaction.getState();
			}
		});
	}
}