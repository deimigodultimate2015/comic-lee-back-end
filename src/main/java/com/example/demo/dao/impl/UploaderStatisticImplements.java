package com.example.demo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.dao.UploaderStatisticRepository;
import com.example.demo.dto.MyDate;
import com.example.demo.dto.response.UploaderComicSummary;
import com.example.demo.dto.response.ViewDay;
import com.example.demo.utils.MyCalendarUtils;

@Repository
public class UploaderStatisticImplements implements UploaderStatisticRepository {
	
	@Autowired
	JdbcTemplate jdbcTemplate ;
	
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplateParam;
	
	
	public List<UploaderComicSummary> getUploaderComicSummary(String displayName) {
		
		String query = "select * from uploader_comic_summary(?)";
		return jdbcTemplate.query(query, new Object[] {displayName}, new UploaderStatisticRowMapper());
		
	}
	
	public List<ViewDay> getComicViewsPrevious7Days(int comicId) {
		List<ViewDay> list = new ArrayList<>();
		
		String query = "select get_comic_view_on_specific_day(:comicId, :day, :month, :year)";
		List<MyDate> myDateList = MyCalendarUtils.getPrevious7Days();
		
		Map<String, Object> params = new HashMap<>();
		myDateList.forEach(dateToGet -> {
			params.put("comicId", comicId);
			params.put("day", dateToGet.getViewDay());
			params.put("month", dateToGet.getViewMonth()+1);
			params.put("year", dateToGet.getViewYear());
			
			Integer views = jdbcTemplateParam.queryForObject(query,params ,Integer.class);
			if(views == null) views = 0;
			
			ViewDay viewDay = new ViewDay();
			viewDay.setViews(views);
			viewDay.setDay(dateToGet.getViewDay());
			
			list.add(viewDay);
		});
		
		return list;
		
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
		ucs.setTotalView(rs.getLong(7));
		
		return ucs;
	}
	
}

