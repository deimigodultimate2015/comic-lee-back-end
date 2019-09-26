package com.example.demo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

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
		
		String query = "select * from uploader_comic_summary(?)";
		return jdbcTemplate.query(query, new Object[] {displayName}, new UploaderStatisticRowMapper());
		
	}
}

class UploaderStatisticRowMapper implements RowMapper<UploaderComicSummary> {

	@Override
	public UploaderComicSummary mapRow(ResultSet rs, int rowNum) throws SQLException {
		UploaderComicSummary ucs = new UploaderComicSummary();
		

		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss.SSS");
		ucs.setTitle(rs.getString(1));
		ucs.setUploadTime(sdf.format(rs.getTimestamp(2)));
		ucs.setModifiedTime(sdf.format(rs.getTimestamp(3)));
		ucs.setTotalFavorite(rs.getLong(4));
		ucs.setUnusedPages(rs.getLong(5));
		ucs.setUsedPage(rs.getLong(6));
		
		return ucs;
	}
	
}
