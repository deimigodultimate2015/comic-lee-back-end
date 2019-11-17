package com.example.demo.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(length = 80)
	@Length(min = 9, max = 80, message = "Username must contain at least 9 character")
	@NaturalId @NotNull
	private String username;
	
	@Column(length = 75)
	@NotNull
	private String password;
	
	@Column(length = 270)
	@Email(message = "Invalid email address")	@NaturalId(mutable = true)	@NotNull
	private String email;
	
	@Column(name = "display_name", length = 80, unique = true)
	@Length(min = 5, max = 80)
	@NotNull
	private String displayName;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "comic")
	private List<Comment> comments = new ArrayList<>();
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_comic_favorite",
			joinColumns = @JoinColumn(name = "comic_id"),
			inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Set<ComicInfo> comics = new HashSet<>();

	public User(@Length(min = 9, max = 80) @NotNull String username, @NotNull String password,
			@Email @NotNull String email, @Length(min = 5, max = 80) @NotNull String displayName,
			Set<ComicInfo> comics) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.displayName = displayName;
		this.comics = comics;
	}
	
}
