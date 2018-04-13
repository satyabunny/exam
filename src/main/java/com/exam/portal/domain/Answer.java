package com.exam.portal.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import lombok.Data;

@Entity
@Data
public class Answer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "answerSeq")
	@SequenceGenerator(name = "answerSeq", sequenceName = "ANSWER_ID_SEQ", allocationSize = 1)
	private Long answerId;
	
	private String answertext;
	
	private Boolean hasImage;
	
	@ManyToOne
	private Question question;

}
