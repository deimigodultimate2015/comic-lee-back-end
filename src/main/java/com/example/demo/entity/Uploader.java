package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "uploader")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Uploader {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(length = 80)
	@Length(min = 9, max = 80)
	@NaturalId @NotNull
	private String username;
	
	@Column(length = 75)
	private String password;
	
	@Column(length = 270)
	@Email	@NaturalId(mutable = true) @NotNull
	private String email;
	
	@Column(length = 80)
	@Length(min = 3, max = 80)
	private String team;
	
	@Column(name = "display_name", length = 80, unique = true)
	@Length(min = 5, max = 80)
	@NotNull
	private String displayName;

	public Uploader(@Length(min = 9, max = 80) @NotNull String username, String password, @Email @NotNull String email,
			@Length(min = 3, max = 80) String team, @Length(min = 5, max = 80) @NotNull String displayName) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.team = team;
		this.displayName = displayName;
	}
	
	
}
