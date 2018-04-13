package com.exam.portal.domain;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
public class LoginData extends Root{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3098098342920926217L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "loginSeq")
	@SequenceGenerator(name = "loginSeq", sequenceName = "LOGIN_DATA_ID_SEQ", allocationSize = 1)
	private Long loginDataId;

	private String authToken;

	@ManyToOne
	private UserInfo userInfo;

	private Long expiryTime;

}