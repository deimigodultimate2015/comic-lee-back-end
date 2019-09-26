package com.example.demo.service.imp;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import com.example.demo.dao.UploaderStatisticRepository;
import com.example.demo.dto.response.UploaderComicSummary;
import com.example.demo.service.StatisticService;

@Service
public class BaseStatisticService implements StatisticService{

	@Value("${excel.template.sourcepath}")
	String sourcePath;
	
	@Value("${excel.template.usepath}")
	String usePath;
	
	@Autowired
	UploaderStatisticRepository statisticRepo ;
	
	@Autowired
    ResourceLoader resourceLoader;
	
	int ucsIndex = 6;
	int totalFavorite = 0;
	int totalComics = 0;
	XSSFRow ucsRow ;
	
	@Override
	public byte[] getUploaderComicSummary(String uploaderName) {
		List<UploaderComicSummary> ucsList =  statisticRepo.getUploaderComicSummary(uploaderName);
		
		XSSFWorkbook workbook = null;
		try {
			workbook = new XSSFWorkbook(getExcelTemplateByName("uploader_comic_statistic.xlsx"));
		} catch (InvalidFormatException e1) {
			e1.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		
		XSSFSheet sheet = workbook.getSheet("comic_statistic");

		ucsList.forEach(ucs -> {
			ucsRow = sheet.createRow(ucsIndex);
			ucsRow.createCell(0).setCellValue(ucs.getTitle());
			ucsRow.createCell(1).setCellValue(ucs.getUploadTime());
			ucsRow.createCell(2).setCellValue(ucs.getModifiedTime());
			ucsRow.createCell(3).setCellValue(ucs.getUsedPage());
			ucsRow.createCell(4).setCellValue(ucs.getUnusedPages());
			ucsRow.createCell(5).setCellValue(ucs.getTotalFavorite());
			totalFavorite += ucs.getTotalFavorite();
			totalComics += 1;
			ucsIndex += 1;
		});
		
		//Summary
		XSSFRow row1 = sheet.createRow(1);
		row1.createCell(0).setCellValue("Uploader: " + uploaderName);
		row1.createCell(3).setCellValue("Total favorite: " + totalFavorite);
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
		
		XSSFRow row2 = sheet.createRow(2);
		row2.createCell(0).setCellValue("Date print: " + sdf.format(new Date()));
		row2.createCell(3).setCellValue("Total view: On coming! This fall! 9/27/2019");
		
		XSSFRow row3 = sheet.createRow(3);
		row3.createCell(0).setCellValue("Total comics: " + totalComics);
		row3.createCell(3).setCellValue("Rank: On coming! Nex fall !");
		
		totalFavorite = 0;
		totalComics = 0;
		ucsIndex = 6;
		
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
	
	
	//Create new file to keep template not be modified
	public File getExcelTemplateByName(String fileName) throws IOException {
		Files.copy(new File(this.sourcePath+fileName).toPath(), new File(this.usePath+fileName).toPath()
				, StandardCopyOption.REPLACE_EXISTING);
		return new File(usePath+fileName);
		
	}

}
