package com.exam.portal.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class Answer {
	
	@Id
	private Long answerId;
	
	private String answertext;
	
	private Boolean hasImage;
	
	@ManyToOne
	private Question question;

}
