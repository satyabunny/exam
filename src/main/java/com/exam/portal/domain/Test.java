package com.exam.portal.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Test {

	@Id
	private Long testId;
	
	private Date testDate;
	
	private Double totalMarks;
	
	@ManyToMany
	private List<UserInfo> givenBy;
}
