package com.exam.portal.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

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
		web.ignoring().antMatchers("/exam/get-options","/exam/get-image-questions","/user/register","/user/login");
	}
	
//
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.addFilterBefore(corsFilter(), SessionManagementFilter.class).cors().and().csrf().disable().authorizeRequests()
		.antMatchers("/exam/get-questions","/exam/excel-upload","/exam/save-answer","/user/logout").hasAnyAuthority(UserRole.Admin.name(), UserRole.Student.name()).anyRequest().authenticated().and()

		.addFilterBefore(new AuthenticationFilter(loginService),
				BasicAuthenticationFilter.class)
		.exceptionHandling().accessDeniedHandler(accessDeniedHandler);
	}
	
	@Bean
    org.springframework.web.filter.CorsFilter corsFilter() {
        org.springframework.web.filter.CorsFilter filter = new org.springframework.web.filter.CorsFilter(corsConfigurationSource());
        return filter;
    }

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
	        CorsConfiguration configuration = new CorsConfiguration();
	        configuration.setAllowedOrigins(Arrays.asList("*"));
	        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS", "DELETE", "PUT", "PATCH"));
	        configuration.setAllowedHeaders(Arrays.asList("X-Requested-With", "Origin", "Content-Type", "Accept", "Authorization"));
	        configuration.setAllowCredentials(true);
	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        source.registerCorsConfiguration("/**", configuration);
	        return source;
	    }
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(demoAuthenticationProvider);
	}

} 


