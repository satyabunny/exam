package com.exam.portal.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name="USER_INFO")
@Data
@EqualsAndHashCode(callSuper=false)
public class UserInfo extends Root implements Authentication{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "userSeq")
	@SequenceGenerator(name = "userSeq", sequenceName = "USER_INFO_ID_SEQ", allocationSize = 1)
	private Long userInfoID;

	private String name;

	private String password;
	
	private String salt;

	private String email;
	
	private String mobileNumber;
	
	private String collegeCode;
	
	private UserRole role = UserRole.Student;
	
	private Course course;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();

		// TODO: add appropriate role here

		// list.add(new SimpleGrantedAuthority(role.getRoleName()));

		return list;
	}

	@Override
	public Object getCredentials() {
		// TODO Auto-generated method stub
		return getPassword();
	}

	@Override
	public Object getDetails() {
		// TODO Auto-generated method stub
		return getEmail();
	}

	@Override
	public Object getPrincipal() {
		// TODO Auto-generated method stub
		return getEmail();
	}

	@Override
	public boolean isAuthenticated() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void setAuthenticated(boolean arg0) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}

}
