package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.request.AddComment;
import com.example.demo.dto.request.AddReply;
import com.example.demo.dto.request.InteractComment;
import com.example.demo.dto.request.RemoveComment;
import com.example.demo.dto.response.ResponseComment;
import com.example.demo.dto.response.ResponseReply;

@Service
public interface CommentService {

	public ResponseComment addComment(AddComment addComment);
	
	public ResponseReply addReply(AddReply addReply);
	
	public String removeComment(RemoveComment removeComment);
	
	public String interactComment(InteractComment interactComment);
	
	public List<ResponseComment> getCommentByComicId(int id);
	
}
