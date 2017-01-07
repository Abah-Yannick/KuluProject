package com.abahyannick.DAO;

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

@Table(name = "cours")
@Entity
@Component
public class Cours implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -3498935346475066614L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "description")
	private char[] description;
	
	@Column(name = "c_when")
	private int cwhen;
	
	@Column(name = "comments_count")
	private int comments_count;
	
	@OneToMany
	@JoinColumn(name="cours_id", referencedColumnName="id")
	private List<CourArgs> courArgs ;
	

	
	

	public Cours(Integer id, String title, char[] description, int cwhen, int comments_count,
			List<CourArgs> courArgs ) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.cwhen = cwhen;
		this.comments_count = comments_count;
		this.courArgs = courArgs;

	}



	public Cours() {

	}



	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public char[] getDescription() {
		return description;
	}



	public void setDescription(char[] description) {
		this.description = description;
	}



	public int getWhen() {
		return cwhen;
	}



	public void setWhen(int cwhen) {
		this.cwhen = cwhen;
	}



	public int getComments_count() {
		return comments_count;
	}



	public void setComments_count(int comments_count) {
		this.comments_count = comments_count;
	}



	public List<CourArgs> getCourArgs() {
		return courArgs;
	}



	public void setCourArgs(List<CourArgs> courArgs) {
		this.courArgs = courArgs;
	}



	




	
	
	

	
	
}
