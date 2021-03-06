package com.exam.portal.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

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
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "testSeq")
	@SequenceGenerator(name = "testSeq", sequenceName = "TEST_ID_SEQ", allocationSize = 1)
	private Long questionId;
	
	@Column(columnDefinition="TEXT")
	private String name;
	
	private Boolean hasImage = Boolean.FALSE;
	
	@OneToOne
	private Answer answer;
	
	@Enumerated
	private ExamConstants questionType;
	
	@OneToMany(mappedBy="question", fetch=FetchType.EAGER)
	private List<Answer> answerList = new ArrayList<Answer>();
	
}
