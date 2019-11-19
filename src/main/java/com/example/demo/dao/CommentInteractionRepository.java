package com.example.demo.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.CommentInteraction;

@Repository
public interface CommentInteractionRepository extends JpaRepository<CommentInteraction, Integer>{

	@Query(nativeQuery = true, value = "select * from comment_interaction where user_id = :userId and comment_id = :commentId")
	Optional<CommentInteraction> getCommentIntByUserIdAndCommentId(@Param("userId") int userId,@Param("commentId") int commentId);
	
}
