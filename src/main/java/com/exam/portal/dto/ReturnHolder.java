package com.exam.portal.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class ReturnHolder {

	@JsonIgnore
	public static final String SUCCESS = "SUCCESS";
	@JsonIgnore
	public static final String FAILURE = "FAILURE";

	private Boolean status = Boolean.TRUE;

	private Object result;

	private ErrorObject errorObject;

	private String message;
	
	private String authToken;

	public ReturnHolder(Object result) {
		super();
		this.result = result;
	}

	public ReturnHolder(Boolean status, ErrorObject errorObject) {
		super();
		this.status = status;
		this.errorObject = errorObject;
	}

	public ReturnHolder() {
		super();
		// TODO Auto-generated constructor stub
	}

}