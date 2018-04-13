package com.exam.portal.dto;

import com.exam.portal.domain.Root;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class RegistrationDTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	
	private String email;
	
	private String password;
	
	private String collegeCode;
	
	private String course;
	
	private String mobileNumber;
	
	private String authToken;
	
}
