package com.example.demo.security.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.entity.RoleName;
import com.example.demo.entity.Uploader;
import com.example.demo.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserPrinciple implements UserDetails{
	
	private static final long serialVersionUID = 3060869439198593825L;

	private Long id;
	
	private String displayName;
	
	private String username;
	
	private String email;
	
	@JsonIgnore
	private String password;

	private Collection<? extends GrantedAuthority> authorities;
	
	public UserPrinciple(Long id, String displayName, String username, String email, String password,
			Collection<? extends GrantedAuthority> authorities) {
		super();
		this.id = id;
		this.displayName = displayName;
		this.username = username;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
	}

	public static UserPrinciple build(User user) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(RoleName.ROLE_USER.toString()));
		
		return new UserPrinciple(
				Long.valueOf(user.getId()),
				user.getDisplayName(),
				user.getUsername(),
				user.getEmail(),
				user.getPassword(),
				authorities
		);
		
	}
	
	public static UserPrinciple build(Uploader user) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(RoleName.ROLE_UPLOADER.toString()));
		
		return new UserPrinciple(
				Long.valueOf(user.getId()),
				user.getDisplayName(),
				user.getUsername(),
				user.getEmail(),
				user.getPassword(),
				authorities
		);
		
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}
	
	public Long getId() {
		return id;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
