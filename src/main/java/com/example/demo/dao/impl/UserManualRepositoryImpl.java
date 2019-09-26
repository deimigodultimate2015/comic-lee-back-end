package com.example.demo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.demo.dao.UserManualRepository;
import com.example.demo.dto.response.UserComics;

@Repository
public class UserManualRepositoryImpl implements UserManualRepository{

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	public List<UserComics> getUserComics() {
		String query = "select * from user_comics_display()";
		return jdbcTemplate.query(query, new UserComicsRowMapper());
	}
	
	public List<UserComics> getUserFavoriteComics(int userId) {
		String query = "select * from specific_user_comics_display(?)";
		return jdbcTemplate.query(query, new Object[] {userId} ,new UserComicsRowMapper());
	}
	

}

class UserComicsRowMapper implements RowMapper<UserComics> {

	@Override
	public UserComics mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserComics ucomics = new UserComics();
		ucomics.setTitle(rs.getString(1));
		ucomics.setComicId(rs.getInt(2));
		ucomics.setCoverId(rs.getInt(4));
		
		return ucomics;
	}
	
}
