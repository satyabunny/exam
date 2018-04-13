package com.exam.portal.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserInfoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4470520764921841044L;
	
	private Long userInfoId;
	
	private String name;
	
	private String email;
	
	private String collegeCode;
	
	private String course;
	
	private String mobileNumber;
	
	private String authToken;
 
}
