package com.exam.portal.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class TestDTO implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<QuestionResponseDTO> arthimaticQuestionsDto = new ArrayList<QuestionResponseDTO>();
	
	private List<QuestionResponseDTO> reasoningQuestionsDto = new ArrayList<QuestionResponseDTO>();
	
	private List<QuestionResponseDTO> englishQuestionsDto = new ArrayList<QuestionResponseDTO>();
	
	private List<QuestionResponseDTO> coreQuestionsDto = new ArrayList<QuestionResponseDTO>();
	
	private Long timeRemaining;
	
	private Long currentQuestionId; 
	
	private Long startTime;
	
}
