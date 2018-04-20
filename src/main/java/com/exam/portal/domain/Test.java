package com.exam.portal.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.exam.portal.dto.TestStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper=false)
public class Test {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "testSeq")
	@SequenceGenerator(name = "testSeq", sequenceName = "TEST_ID_SEQ", allocationSize = 1)
	private Long testId;
	
	private Date testDate;
	
	private Double totalMarks;
	
	private TestStatus testStatus;
	
	private Long timeRemaining;
	
	@ManyToOne
	private UserInfo givenBy;
}
