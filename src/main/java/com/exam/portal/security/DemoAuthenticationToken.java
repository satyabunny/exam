package com.exam.portal.security;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import com.exam.portal.domain.UserInfo;

public class DemoAuthenticationToken extends AbstractAuthenticationToken {

	private static final long serialVersionUID = -1949976839306453197L;
	private UserInfo authenticatedUser;
	private Long id;

	public DemoAuthenticationToken(Long id) {
		super(Arrays.asList());
		this.id = id;
	}

	public DemoAuthenticationToken(Collection<? extends GrantedAuthority> authorities, UserInfo authenticatedUser,
			Long id) {
		super(authorities);
		this.id = id;
		this.authenticatedUser = authenticatedUser;
	}

	@Override
	public Object getCredentials() {
		return authenticatedUser.getPassword();
	}

	@Override
	public boolean isAuthenticated() {
		return true;
	}

	@Override
	public Object getPrincipal() {
		return authenticatedUser;
	}

	public Long getId() {
		return id;
	}

}