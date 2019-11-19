package com.example.demo.service.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.demo.dao.ComicInfoRepository;
import com.example.demo.dao.CommentInteractionRepository;
import com.example.demo.dao.CommentRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.dto.request.AddComment;
import com.example.demo.dto.request.AddReply;
import com.example.demo.dto.request.InteractComment;
import com.example.demo.dto.request.RemoveComment;
import com.example.demo.dto.response.ResponseComment;
import com.example.demo.dto.response.ResponseReply;
import com.example.demo.entity.ComicInfo;
import com.example.demo.entity.Comment;
import com.example.demo.entity.CommentInteraction;
import com.example.demo.entity.User;
import com.example.demo.error.custom.CustomInvalidAuthenticator;
import com.example.demo.error.custom.CustomInvalidInputException;
import com.example.demo.error.custom.ObjectNotFoundException;
import com.example.demo.service.CommentService;

@Service
public class BasicCommentService implements CommentService{

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ComicInfoRepository comicRepository;
	
	@Autowired
	CommentRepository commentRepository;
	
	@Autowired
	CommentInteractionRepository intCommentRepository;
	
	@Override
	public ResponseComment addComment(AddComment addComment) {
		Comment comment = new Comment();
		Optional<User> user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		
		Optional<ComicInfo> comic = comicRepository.findById(addComment.getComicId());
		if(!comic.isPresent()) {
			throw new ObjectNotFoundException("Comic id '" + addComment.getComicId() + "' not exist!");
		}
		comment.setUser(user.get());
		comment.setParentId(0);
		comment.setCreatedDate(new Date());
		comment.setContent(addComment.getContent());
		comment.setComic(comic.get());
		
		commentRepository.save(comment);
		return new ResponseComment(comment);
	}

	@Override
	public ResponseReply addReply(AddReply addReply) {
		Comment comment = new Comment();
		Optional<Comment> commentToValid = commentRepository.findById(addReply.getCommentId());
		if(!commentToValid.isPresent()) {
			throw new ObjectNotFoundException("Comment id '" + addReply.getCommentId() + "' not exist!");
		} else if(commentToValid.get().getParentId() != 0) {
			throw new CustomInvalidInputException("Comment id '" + addReply.getCommentId() + "' actually is a reply! You cannot reply to a reply");
		}
		
		Optional<User> user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		
		comment.setUser(user.get());
		comment.setParentId(addReply.getCommentId());
		comment.setCreatedDate(new Date());
		comment.setContent(addReply.getContent());
		comment.setComic(commentToValid.get().getComic());
		comment.setRemoveFlag(false);
		
		commentRepository.save(comment);
		return new ResponseReply(comment);
	}

	@Override
	public String removeComment(RemoveComment removeComment) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		Optional<Comment> comment = commentRepository.findById(removeComment.getCommentId());
		if(!comment.isPresent()) {
			throw new ObjectNotFoundException("Not found comment with id '"+removeComment.getCommentId()+"'");
		} else if (!comment.get().getUser().getUsername().equals(authentication.getName())) {
			throw new CustomInvalidAuthenticator("You're not the original post of this comment");
		}
		
		comment.get().setRemoveFlag(true);
		commentRepository.save(comment.get());
		return "Comment has id '"+removeComment.getCommentId()+"' is removed successful!";
	}

	@Override
	public String interactComment(InteractComment interactComment) {
		Optional<Comment> comment = commentRepository.findById(interactComment.getCommentId());
		short stateDe = interactComment.getState();
		if(stateDe > 1 || stateDe < -1) stateDe = 0;
		
		if(!comment.isPresent()) {
			throw new ObjectNotFoundException("Not found comment with id '"+interactComment.getCommentId()+"'");
		}
		
		Optional<User> user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		
		Optional<CommentInteraction> commentInt = 
				intCommentRepository.getCommentIntByUserIdAndCommentId( user.get().getId(), interactComment.getCommentId());
		
		if(commentInt.isEmpty()) {
			CommentInteraction newCommentInt = new CommentInteraction();
			newCommentInt.setComment(comment.get());
			newCommentInt.setUser(user.get());
			newCommentInt.setState(stateDe);
			intCommentRepository.save(newCommentInt);
		}  else {
			if(stateDe == commentInt.get().getState()) stateDe = 0;
			commentInt.get().setState(stateDe);
			intCommentRepository.save(commentInt.get());
		}
		
		String state = stateDe == -1? "dislike" : (stateDe == 0 ? "unset" : "like");
		return state + " comment '" + interactComment.getCommentId() + "' successful!";
	}

	@Override
	public List<ResponseComment> getCommentByComicId(int id) {
		
		String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
		
		List<ResponseComment> commentList = new ArrayList<>();
		List<ResponseReply> replyList = new ArrayList<>();
		commentRepository.getResponseCommentFromComicsId(id).forEach(comment -> {
			if(comment.getParentId() == 0) {
				commentList.add(new ResponseComment(comment, currentUsername));
			} else {
				replyList.add(new ResponseReply(comment, currentUsername));
			}
		});
		
		commentList.forEach(comment -> {
			replyList.forEach(reply -> {
				if(reply.getParentId() == comment.getId()) {
					comment.getReplies().add(reply);
				}
			});
		});
		
		return commentList;
	}
	

}
