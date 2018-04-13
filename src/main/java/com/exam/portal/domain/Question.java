package com.exam.portal.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper=false)
public class Question extends Root {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6677511294514383341L;

	@Id
	private Long questionId;
	
	private String name;
	
	private Boolean hasImage;
	
	@OneToOne
	private Answer answer;
	
	@OneToMany(mappedBy="question")
	private List<Answer> answerList = new ArrayList<Answer>();
	
}
