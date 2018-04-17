package com.exam.portal.dto;

import lombok.Data;

@Data
public class AnswerReponseDTO {
	
	private String answerText;
	
	private Long answerId;
	
	private Boolean hasImage = Boolean.FALSE;
	
}
