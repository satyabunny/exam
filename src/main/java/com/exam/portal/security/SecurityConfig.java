package com.exam.portal.security;

/*@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)*/
public class SecurityConfig/* extends WebSecurityConfigurerAdapter */{

	/*@Autowired
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
	}*/

} 


