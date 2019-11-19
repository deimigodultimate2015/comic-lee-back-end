package com.example.demo.dto.response;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.demo.entity.Comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseComment {

	private int id;
	private String content;
	private Date createdDate;
	private int point;
	private short state;
	private List<ResponseReply> replies = new ArrayList<>();
	
	public ResponseComment(Comment comment) {
		this.state = 0;
		this.id = comment.getId();
		this.content = comment.getContent();
		this.createdDate = comment.getCreatedDate();
		comment.getCommentInteractions().forEach(interaction -> {
			if(interaction.getState() == 1) {
				point += 1;
			}
		});
	}
	
	public ResponseComment(Comment comment, String username) {
		this.state = 0;
		this.id = comment.getId();
		this.content = comment.getContent();
		this.createdDate = comment.getCreatedDate();
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
