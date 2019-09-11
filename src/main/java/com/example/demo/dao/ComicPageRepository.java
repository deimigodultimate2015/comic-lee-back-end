package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.ComicPage;

@Repository
public interface ComicPageRepository extends JpaRepository<ComicPage, Integer>{

}
