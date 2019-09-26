package com.example.demo.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.UserFavorite;

@Repository
public interface UserFavoriteRepository extends JpaRepository<UserFavorite, Integer>{

	@Query(nativeQuery = true, value = "select count(user_id)>0 from user_favorite where user_id = :userId and comic_info_id = :comicId")
	public boolean existsByUserId(@Param("userId") int userId, @Param("comicId") int comicId);
	
	@Query(nativeQuery = true, value = "select * from user_favorite where user_id = :userId and comic_info_id = :comicId")
	public Optional<UserFavorite> findByUserId(@Param("userId") int userId, @Param("comicId") int comicId);
	
	@Query(nativeQuery = true, value = "select count(user_id)>0 from user_favorite where user_id = :userId and comic_info_id = :comicId and still_true")
	public boolean checkFavoriteState(@Param("userId") int userId, @Param("comicId") int comicId);
	
	
}
