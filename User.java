package com.abahyannick.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Table(name = "user")
@Entity
@Component
public class User implements Serializable {

	/**
		 * 
		 */
	private static final long serialVersionUID = 8720572270852052086L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;

	@Column(name = "email")
	private String email;

	@Column(name = "username")
	private String username;

	@Column(name = "firstname")
	private String firstname;

	@Column(name = "lastname")
	private String lastname;

	@Column(name = "password")
	private String password;

	@Column(name = "image_path")
	private String imagePath;

	@Column(name = "enabled")
	private boolean enabled;

	@OneToMany
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private List<CoursCmts> coursCmts;

	@OneToMany
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private List<ReplyCmts> replyCmts;

	public User(Integer id, String email, String username, String firstname, String lastname, String password,
			String imagePath, List<CoursCmts> coursCmts) {
		super();
		this.id = id;
		this.email = email;
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.password = password;
		this.imagePath = imagePath;
		this.coursCmts = coursCmts;
	}

	public User(String email, String username, String firstname, String lastname, String password) {
		this.email = email;
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.password = password;
	}

	public User(Integer id, String email, String username, String firstname, String lastname, String password) {
		this.id = id;
		this.email = email;
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.password = password;
	}

	public User() {
		super();
		this.enabled = false;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public List<ReplyCmts> getReplyCmts() {
		return replyCmts;
	}

	public void setReplyCmts(List<ReplyCmts> replyCmts) {
		this.replyCmts = replyCmts;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public List<CoursCmts> getCoursCmts() {
		return coursCmts;
	}

	public void setCoursCmts(List<CoursCmts> coursCmts) {
		this.coursCmts = coursCmts;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", username=" + username + ", firstname=" + firstname
				+ ", lastname=" + lastname + ", password=" + password + "]";
	}

}
