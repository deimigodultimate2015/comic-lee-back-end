package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Page;

@Repository
public interface PageRepository extends JpaRepository<Page, Integer>{
	
}
