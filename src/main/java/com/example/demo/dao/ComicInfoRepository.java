package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.ComicInfo;

@Repository
public interface ComicInfoRepository extends JpaRepository<ComicInfo, Integer>{
	
	public boolean existsByTitle(String title);
	
	@Query(nativeQuery = true, value = "select comic_all_like(:comicId) ")
	public int countComicFavorites(@Param("comicId") int comicId);
	
	@Query(nativeQuery = true, value = "select get_comic_total_view(:comicId)")
	public Integer getTotalView(@Param("comicId") int comicId);
}
