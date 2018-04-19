package com.exam.portal.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class SaveQuestionDTO implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 8040154176831645802L;
	
	private Long questionId;
	
	private Long answerId;
	
	private Long time;

}
