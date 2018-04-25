package com.exam.portal.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class QuestionImageDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long questionId;
	
	private String name;

}
