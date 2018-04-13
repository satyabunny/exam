package com.exam.portal.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import com.exam.portal.domain.UserInfo;
import com.exam.portal.domain.UserRole;
import com.exam.portal.dto.ErrorObject;
import com.exam.portal.dto.ReturnHolder;
import com.exam.portal.service.LoginService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AuthenticationFilter extends OncePerRequestFilter {

	private LoginService loginService;

	private String userRole;

	public AuthenticationFilter(LoginService loginService, String userRole) {
		super();
		this.loginService = loginService;
		this.userRole = userRole;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String xAuth = request.getHeader("authToken");
		Authentication auth = null;
		try {
			if (!isValid(xAuth)) {
				throw new SecurityException("Unauthorized");
			}
			UserInfo appUser = loginService.getUser(xAuth);
			if (appUser == null) {
				throw new UsernameNotFoundException("Please login to continue");
			}

			if (appUser.getRole() != null && !appUser.getRole().name().isEmpty()) {
				Function<UserRole, SimpleGrantedAuthority> mapper = a -> {
					return new SimpleGrantedAuthority(a.name());
				};
				auth = new DemoAuthenticationToken(Arrays.asList(appUser.getRole()).stream().map(mapper).collect(Collectors.toSet()),
						appUser, appUser.getUserInfoID());
			} else {
				auth = new AnonymousAuthenticationToken(xAuth, xAuth,
						Arrays.asList(new SimpleGrantedAuthority("NO_ROLE")));
			}
			SecurityContextHolder.getContext().setAuthentication(auth);
			filterChain.doFilter(request, response);
		} catch (RuntimeException e) {
			System.out.println("Entered into catch Runtime Exception");
			e.printStackTrace();
			ReturnHolder customResponse = new ReturnHolder(false, new ErrorObject("ERR401", e.getMessage()));
			response.setContentType("application/json; charset=utf-8");
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().write(new ObjectMapper().writeValueAsString(customResponse));
		} catch (Exception e) {
			e.printStackTrace();
			ReturnHolder customResponse = new ReturnHolder(false, new ErrorObject("ERR500", "Something went wrong"));
			response.setContentType("application/json; charset=utf-8");
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().write(new ObjectMapper().writeValueAsString(customResponse));
		}

	}

	private boolean isValid(String authToken) {
		if (authToken != null && authToken != "")
			return true;
		else
			return false;
	}

}
