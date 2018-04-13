package com.exam.portal.domain;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class LoginData extends Root{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3098098342920926217L;
	
	@Id
	private Long loginDataId;

	private String authToken;

	@ManyToOne
	private UserInfo userInfo;

	private Long expiryTime;

}