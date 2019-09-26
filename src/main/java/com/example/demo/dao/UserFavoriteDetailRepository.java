package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.UserFavoriteDetails;

@Repository
public interface UserFavoriteDetailRepository extends JpaRepository<UserFavoriteDetails, Long>{

}
