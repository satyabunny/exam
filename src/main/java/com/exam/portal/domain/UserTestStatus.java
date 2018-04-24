package com.exam.portal.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper=false)
//@Table(uniqueConstraints= {@UniqueConstraint(columnNames = {"",""})})
public class UserTestStatus extends Root {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "userTestStatusSeq")
	@SequenceGenerator(name = "userTestStatusSeq", sequenceName = "USER_TEST_STATUS_ID_SEQ", allocationSize = 1)
	private Long UserTestStatusId;
	
	@ManyToOne
	private Question question;
	
	@ManyToOne
	private Answer answer;
	
	@ManyToOne
	private UserInfo info;
	
	private Boolean isAnswered;
	
	private Boolean isCorrectAnswered = Boolean.FALSE;

}
