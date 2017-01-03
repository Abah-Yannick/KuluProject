package com.abahyannick.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Table(name = "cours_cmts")
@Entity
@Component
public class CoursCmts implements Serializable{
	
		

		/**
	 * 
	 */
	private static final long serialVersionUID = -1950475256558723750L;

		@Id
		@GeneratedValue(strategy=GenerationType.AUTO)
		@Column(name = "c_id")
		private Integer cId;
		
		@Column(name = "c_text")
		private char[] cText;
		
		@Column(name = "c_ip")
		private String cIp;
		
		@Column(name = "c_name")
		private String cName;
		
		@Column(name = "c_when")
		private Long cWhen;
		
		@Column(name = "cour_args_id")
		private Integer coursArgsId;

		
		@Column(name = "user_id")
		private Integer userId;

		@OneToMany
		@JoinColumns({
		@JoinColumn(name="cours_cmts_c_id", referencedColumnName="c_id"),
		@JoinColumn(name="cours_cmts_cour_args_id", referencedColumnName="cour_args_id"),
		@JoinColumn(name="cours_cmts_user_id", referencedColumnName="user_id")
		})
		private List<ReplyCmts> replyCmts ;
		


		
		



		public CoursCmts() {
		
		}






		public CoursCmts(char[] cText, String cIp, Long cWhen, Integer coursArgsId, Integer userId) {
			super();
			this.cText = cText;
			this.cIp = cIp;
			this.cWhen = cWhen;
			this.coursArgsId = coursArgsId;
			this.userId = userId;
		}






		public CoursCmts(Integer cId, char[] cText, String cIp, String cName, Long cWhen, Integer coursArgsId,
				Integer userId) {
			super();
			this.cId = cId;
			this.cText = cText;
			this.cIp = cIp;
			this.cName = cName;
			this.cWhen = cWhen;
			this.coursArgsId = coursArgsId;
			this.userId = userId;
		}






		public Integer getcId() {
			return cId;
		}






		public void setcId(Integer cId) {
			this.cId = cId;
		}






		public char[] getcText() {
			return cText;
		}






		public void setcText(char[] cText) {
			this.cText = cText;
		}






		public String getcIp() {
			return cIp;
		}






		public void setcIp(String cIp) {
			this.cIp = cIp;
		}






		public String getcName() {
			return cName;
		}






		public void setcName(String cName) {
			this.cName = cName;
		}






		public Long getcWhen() {
			return cWhen;
		}






		public void setcWhen(Long cWhen) {
			this.cWhen = cWhen;
		}






		public Integer getCoursArgsId() {
			return coursArgsId;
		}






		public void setCoursArgsId(Integer coursArgsId) {
			this.coursArgsId = coursArgsId;
		}






		public Integer getUserId() {
			return userId;
		}






		public void setUserId(Integer userId) {
			this.userId = userId;
		}






		public List<ReplyCmts> getReplyCmts() {
			return replyCmts;
		}






		public void setReplyCmts(List<ReplyCmts> replyCmts) {
			this.replyCmts = replyCmts;
		}










       



		
		
		

}
