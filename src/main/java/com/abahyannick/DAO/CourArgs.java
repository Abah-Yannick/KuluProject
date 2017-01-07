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


@Table(name = "cour_args")
@Entity
@Component
public class CourArgs implements Serializable {


			/**
	 * 
	 */
	private static final long serialVersionUID = -7715144816722363658L;

			@Id
			@GeneratedValue(strategy=GenerationType.AUTO)
			@Column(name = "id")
			private Integer id;
			
			@Column(name = "title")
			private String title;
			
			@Column(name = "cours_id")
			private Integer coursId;
			
			
			@Column(name = "path_cours")
			private String pathCours;
			
			@OneToMany
			@JoinColumn(name="cour_args_id", referencedColumnName="id")
			private List<CoursCmts> coursCmts ;

			

			public CourArgs() {

			}

			public CourArgs(Integer id, String title, Integer coursId, String pathCours, List<CoursCmts> coursCmts) {
				super();
				this.id = id;
				this.title = title;
				this.coursId = coursId;
				this.pathCours = pathCours;
				this.coursCmts = coursCmts;
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

			public Integer getCoursId() {
				return coursId;
			}

			public void setCoursId(Integer coursId) {
				this.coursId = coursId;
			}

			public String getPathCours() {
				return pathCours;
			}

			public void setPathCours(String pathCours) {
				this.pathCours = pathCours;
			}

			public List<CoursCmts> getCoursCmts() {
				return coursCmts;
			}

			public void setCoursCmts(List<CoursCmts> coursCmts) {
				this.coursCmts = coursCmts;
			}


			

			

			
			
			
			
			

}
