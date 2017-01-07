package com.abahyannick.DAO;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Table(name = "reply_cmts")
@Entity
@Component
public class ReplyCmts implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 261354967534824694L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "c_text")
	private char[] cText;
	
	@Column(name = "c_ip")
	private String cIp;
	
	@Column(name = "c_when")
	private Long cWhen;
	
	@Column(name = "cours_cmts_c_id")
	private Integer coursCmtsCId;
	
	@Column(name = "cours_cmts_cour_args_id")
	private Integer coursCmtsCourArgsId;
	
	@Column(name = "cours_cmts_user_id")
	private Integer coursCmtsUserId;
	
	@Column(name = "user_id")
	private Integer userId;
	
	

	public ReplyCmts() {
		super();
	}

	public ReplyCmts(char[] cText, String cIp, Long cWhen, Integer coursCmtsCId,
			Integer coursCmtsCourArgsId, Integer coursCmtsUserId, Integer userId) {
		super();
		this.cText = cText;
		this.cIp = cIp;
		this.cWhen = cWhen;
		this.coursCmtsCId = coursCmtsCId;
		this.coursCmtsCourArgsId = coursCmtsCourArgsId;
		this.coursCmtsUserId = coursCmtsUserId;
		this.userId = userId;
	}

	public ReplyCmts(Integer id, char[] cText, String cIp, Long cWhen, Integer coursCmtsCId,
			Integer coursCmtsCourArgsId, Integer coursCmtsUserId, Integer userId) {
		super();
		this.id = id;
		this.cText = cText;
		this.cIp = cIp;
		this.cWhen = cWhen;
		this.coursCmtsCId = coursCmtsCId;
		this.coursCmtsCourArgsId = coursCmtsCourArgsId;
		this.coursCmtsUserId = coursCmtsUserId;
		this.userId = userId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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


	public Long getcWhen() {
		return cWhen;
	}

	public void setcWhen(Long cWhen) {
		this.cWhen = cWhen;
	}

	public Integer getCoursCmtsCId() {
		return coursCmtsCId;
	}

	public void setCoursCmtsCId(Integer coursCmtsCId) {
		this.coursCmtsCId = coursCmtsCId;
	}

	public Integer getCoursCmtsCourArgsId() {
		return coursCmtsCourArgsId;
	}

	public void setCoursCmtsCourArgsId(Integer coursCmtsCourArgsId) {
		this.coursCmtsCourArgsId = coursCmtsCourArgsId;
	}

	public Integer getCoursCmtsUserId() {
		return coursCmtsUserId;
	}

	public void setCoursCmtsUserId(Integer coursCmtsUserId) {
		this.coursCmtsUserId = coursCmtsUserId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	
	
	
}
