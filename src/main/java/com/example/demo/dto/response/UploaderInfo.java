package com.example.demo.dto.response;

import com.example.demo.entity.Uploader;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploaderInfo {
	
	private int id;
	private String username;
	private String email;
	private String displayName;
	private String team;
	
	public UploaderInfo(Uploader uploader) {
		this.id = uploader.getId();
		this.username = uploader.getUsername();
		this.email = uploader.getEmail();
		this.displayName = uploader.getDisplayName();
		this.team = uploader.getTeam();
	}
	
}
