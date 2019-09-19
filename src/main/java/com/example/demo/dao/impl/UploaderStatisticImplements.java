package com.example.demo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.demo.dao.UploaderStatisticRepository;
import com.example.demo.dto.response.UploaderComicSummary;

@Repository
public class UploaderStatisticImplements implements UploaderStatisticRepository {
	
	@Autowired
	JdbcTemplate jdbcTemplate ;
	
	
	public List<UploaderComicSummary> getUploaderComicSummary(String displayName) {
		
		String query = "select * from uploader_comic_summary('hanazuki20')";
		return jdbcTemplate.query(query, new UploaderStatisticRowMapper());
		
	}
}

class UploaderStatisticRowMapper implements RowMapper<UploaderComicSummary> {

	@Override
	public UploaderComicSummary mapRow(ResultSet rs, int rowNum) throws SQLException {
		UploaderComicSummary ucs = new UploaderComicSummary();
		
		ucs.setUploader(rs.getString(1));
		ucs.setEmail(rs.getString(2));
		ucs.setTeam(rs.getString(3));
		ucs.setComicTitle(rs.getString(4));
		ucs.setArtist(rs.getString(5));
		ucs.setTotal(rs.getLong(6));
		
		return ucs;
	}
	
}
