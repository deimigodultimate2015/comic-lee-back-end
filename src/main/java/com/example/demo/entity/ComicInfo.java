package com.example.demo.entity;

import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comic_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComicInfo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id ;
	
	@Column(length = 255)
	@Length(min = 6, max = 255)
	@NotNull(message = "Comic title must not be null")
	private String title;
	
	@Column(length = 70)
	@Length(min = 2, max = 70)
	private String artist;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "timestamp without time zone", name="upload_time")
	private Date uploadTime;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "timestamp without time zone", name="modified_time")
	private Date modifiedTime;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "uploader_id")
	private Uploader uploader;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "comic_tag",
		joinColumns = @JoinColumn(name = "comic_id"),
		inverseJoinColumns = @JoinColumn(name="tag_id"))
	private Set<Tag> tags = new HashSet<>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "comic")
	private List<Page> pages = new ArrayList<>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "comic")
	private List<Reader> views = new ArrayList<>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "comic")
	private List<Comment> comments = new ArrayList<>();
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_comic_favorite",
			joinColumns = @JoinColumn(name = "comic_id"),
			inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Set<User> users = new HashSet<>();

	public ComicInfo(@Length(min = 6, max = 255) @NotNull String title, @Length(min = 6, max = 70) String artist,
			Date uploadTime, Uploader uploader) {
		super();
		this.title = title;
		this.artist = artist;
		this.uploadTime = uploadTime;
		this.uploader = uploader;
	}

	
}
