package com.exam.portal.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@MappedSuperclass
@Data
public class Root implements Serializable {

	private static final long serialVersionUID = -278896838275613557L;
	
	@CreationTimestamp
	private Timestamp createdDate;
	
	@UpdateTimestamp
	private Timestamp updatedOn;
}
