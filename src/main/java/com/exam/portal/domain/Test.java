package com.exam.portal.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;

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
	
	private Boolean isTestCompleted;
	
	private Long timeRemaing;
	
	@ManyToMany
	private List<UserInfo> givenBy = new ArrayList<UserInfo>();
}
