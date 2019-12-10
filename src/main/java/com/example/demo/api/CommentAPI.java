package com.example.demo.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.request.AddComment;
import com.example.demo.dto.request.AddReply;
import com.example.demo.dto.request.InteractComment;
import com.example.demo.dto.request.RemoveComment;
import com.example.demo.dto.response.ResponseComment;
import com.example.demo.dto.response.ResponseReply;
import com.example.demo.service.CommentService;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class CommentAPI {

	@Autowired
	public CommentService commentService;

	
	@PreAuthorize("hasRole('USER')")
	@PostMapping("/comment")
	public ResponseEntity<ResponseComment> addComment(@RequestBody AddComment addComment) {
		return new ResponseEntity<>(this.commentService.addComment(addComment), HttpStatus.CREATED);
	}

	@PreAuthorize("hasRole('USER')")
	@DeleteMapping("/comment/remove")
	public ResponseEntity<String> removeComment(@RequestBody RemoveComment removeComment) {
		return new ResponseEntity<>(this.commentService.removeComment(removeComment), HttpStatus.ACCEPTED);
	}
	
	@PreAuthorize("hasRole('USER')")
	@PostMapping("/reply")
	public ResponseEntity<ResponseReply> addReply(@RequestBody AddReply addReply) {
		return new ResponseEntity<>(this.commentService.addReply(addReply), HttpStatus.CREATED);
	}
	

	@PreAuthorize("hasRole('USER')")
	@PutMapping("/comment/interaction")
	public ResponseEntity<String> commentInteract(@RequestBody InteractComment interactComment) {
		return new ResponseEntity<>(this.commentService.interactComment(interactComment), HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/comments/{comicId}")
	public ResponseEntity<List<ResponseComment>> getCommentsByComicsId(@PathVariable("comicId") int comicId) {
		return new ResponseEntity<>(this.commentService.getCommentByComicId(comicId), HttpStatus.ACCEPTED);
	}
}
