package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Uploader;

@Repository
public interface UploaderRepository extends JpaRepository<Uploader, Integer>{

}
