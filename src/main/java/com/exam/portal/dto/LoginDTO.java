package com.exam.portal.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class LoginDTO {
	
	private String username;
	
	private String password;

}
