package com.example.demo.service.imp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.UploaderStatisticRepository;
import com.example.demo.dto.response.UploaderComicSummary;
import com.example.demo.service.StatisticService;

@Service
public class BaseStatisticService implements StatisticService{

	@Autowired
	UploaderStatisticRepository statisticRepo ;
	
	int ucsIndex = 2;
	XSSFRow ucsRow ;
	
	@Override
	public byte[] getUploaderComicSummary(String uploaderName) {
		List<UploaderComicSummary> ucsList =  statisticRepo.getUploaderComicSummary(uploaderName);
		XSSFWorkbook  workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("statistic_01");
		
		//Init header
		ucsRow = sheet.createRow(1);
		ucsRow.createCell(0).setCellValue("uploader");
		ucsRow.createCell(1).setCellValue("email");
		ucsRow.createCell(2).setCellValue("team");
		ucsRow.createCell(3).setCellValue("comic title");
		ucsRow.createCell(4).setCellValue("artist");
		ucsRow.createCell(5).setCellValue("total page (use)");
		
		ucsList.forEach(ucs -> {
			ucsRow = sheet.createRow(ucsIndex);
			ucsRow.createCell(0).setCellValue(ucs.getUploader());
			ucsRow.createCell(1).setCellValue(ucs.getEmail());
			ucsRow.createCell(2).setCellValue(ucs.getTeam());
			ucsRow.createCell(3).setCellValue(ucs.getComicTitle());
			ucsRow.createCell(4).setCellValue(ucs.getArtist());
			ucsRow.createCell(5).setCellValue(ucs.getTotal());
			
			ucsIndex += 1;
		});
		
		ucsIndex = 2;
		
		ByteArrayOutputStream output = new ByteArrayOutputStream(1024);
		byte[] bookArray = new byte[1024] ;
		
		try {
			workbook.write(output);
			bookArray = output.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				workbook.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return bookArray;
		
	}

}
