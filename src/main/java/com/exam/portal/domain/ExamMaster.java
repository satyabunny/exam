package com.exam.portal.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper=false)
public class ExamMaster extends Root {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1677892841542413685L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "examMasterSeq")
	@SequenceGenerator(name = "examMasterSeq", sequenceName = "EXAM_MASTER_ID_SEQ", allocationSize = 1)
	private Long examMasterId;
	
	private Long noOfQuestions;
	
	private Long arthimaticQuestions;
	
	private Long noOfReasoningQuestions;
	
	private Long noOfEnglishQuestions;
	
	private Long noOfCoreQuestions;
	
	private Long totalTimeInMillis;
}
