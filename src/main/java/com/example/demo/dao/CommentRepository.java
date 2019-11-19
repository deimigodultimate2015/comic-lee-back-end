package com.example.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer>{

	@Query(nativeQuery = true, value = "select * from comment where comic_id = :comicId order by created_date desc")
	public List<Comment> getResponseCommentFromComicsId(@Param("comicId") int comicId);
	
}
