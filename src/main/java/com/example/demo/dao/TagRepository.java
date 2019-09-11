package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer>{
	boolean existsByName(String name);
}
