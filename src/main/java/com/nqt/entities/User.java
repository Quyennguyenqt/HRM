package com.nqt.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity

@Table(name = "aht_user", uniqueConstraints = { @UniqueConstraint(columnNames = "username"),
		@UniqueConstraint(columnNames = "email") })
//@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long userId;

	private String username;

	private String password;

	private String email;

	private Set<UserRole> userRoles = new HashSet<UserRole>(0);

	public User() {

	}

	public User(String username, String password, String email, Set<UserRole> userRoles) {
		this.setUsername(username);
		this.password = password;
		this.email = email;
		this.userRoles = userRoles;
	}

	public User(String username, String password, String email) {
		this.setUsername(username);
		this.password = password;
		this.email = email;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id", nullable = false)
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@NotBlank(message = "username cannot be empty")
	@Size(max = 50)
	@Column(name = "username", nullable = false)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@NotBlank(message = "password cannot be empty")
	@Size(max = 120)
	@Column(name = "password", nullable = false)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@NotBlank(message = "email cannot be empty")
	@Size(max = 120)
	@Email
	@Column(name = "email", nullable = false)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.REMOVE)
	//@Cascade(value = { org.hibernate.annotations.CascadeType.REMOVE })
	// @JsonProperty(access = Access.WRITE_ONLY)
	public Set<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

}
