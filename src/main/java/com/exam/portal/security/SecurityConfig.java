package com.exam.portal.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.exam.portal.domain.UserRole;
import com.exam.portal.service.LoginService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AccessDeniedHandler accessDeniedHandler;

	@Autowired
	private SecurityAuthenticationProvider demoAuthenticationProvider;
	
	@Autowired
	private LoginService loginService;
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/user/register","/user/login");
	}
	
	/*@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		super.configure(http);
		http.csrf().ignoringAntMatchers("/user/register");
	}*/
//
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable().authorizeRequests()
		.antMatchers("/exam/get-questions","/exam/excel-upload","/exam/save-answer","/user/logout").hasAnyAuthority(UserRole.Admin.name(), UserRole.Student.name()).anyRequest().authenticated().and()

		.addFilterBefore(new AuthenticationFilter(loginService),
				BasicAuthenticationFilter.class)
		.exceptionHandling().accessDeniedHandler(accessDeniedHandler);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(demoAuthenticationProvider);
	}

} 


