package com.exam.portal.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserInfoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4470520764921841044L;
	
	private Long userInfoID;
	
	private String name;
	
	private String email;
	
	private String collegeCode;
	
	private String course;
	
	private String userRole;
	
	private String mobileNumber;
	
	private String authToken;
 
}
