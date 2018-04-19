package com.exam.portal.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class ResultDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long userInfoId;
	
	private String userName;
	
	private String email;
	
	private String course;
	
	private String collegeCode;
	
	private Double marks;

}
