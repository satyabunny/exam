package com.exam.portal.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class QuestionResponseDTO {
	
	private String qustionText;
	
	private Long questionId;
	
	private List<AnswerReponseDTO> answerList = new ArrayList<AnswerReponseDTO>();
	
	private Boolean isAnswered = Boolean.FALSE;
	
	private Boolean hasImage = Boolean.FALSE;
	
}
