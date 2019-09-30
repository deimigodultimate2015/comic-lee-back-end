package com.example.demo.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Reader;

@Repository
public interface ReaderRepository extends JpaRepository<Reader, Integer>{
	
	@Query(nativeQuery = true, value = "select count(id)>0 from reader where uuid = :uuid and comic_id = :comicId")
	public boolean existsByUuidAndComicInfoId(@Param("uuid") String uuid, @Param("comicId") int comicId);
	
	@Query(nativeQuery = true, value = "select * from reader where uuid = :uuid and comic_id = :comicId")
	public Optional<Reader> findByUuidAndComicInfoId(@Param("uuid") String uuid, @Param("comicId") int comicId);
	
}
