package com.exam.portal.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//	@Autowired
//	private AccessDeniedHandler accessDeniedHandler;

	@Autowired
	private SecurityAuthenticationProvider demoAuthenticationProvider;

//	@Override
//	public void configure(WebSecurity web) throws Exception {
////		web.ignoring().antMatchers("/api/v1/*", "/api/v1/*/*", "/api/v1/get-file/*", "/sample-data-controller/**",
////				"/api/v2/super-admin-register", "/api/v2/super-admin-login", "/api/v2/devotee-register",
////				"/api/v2/gurudwara-register", "/api/v2/devotee-login", "/hello/**", "/api/v2/gurudwara-login",
////				"/api/v2/logout", "/api/v2/gurudwara-verify-otp", "/api/v2/devotee-verify-otp",
////				"/api/v2/forgot-otpverify", "/api/v2/upload-user-picture", "/api/v2/forgot-password",
////				"/api/v2/resend-otp", "/api/v2/user-profile-update", "/api/v2/countries-states",
////				"/api/v2/gurudwara-details", "/api/v2/notification-count", "/api/v2/upload-gurudwara-picture",
////				"/api/v2/file-upload", "/api/v2/get-file/*", "/api/v2/change-password", "/api/v2/force-update",
////				"/api/v2/forgot-username","/excel-upload");
//	}
//
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//
//		/*http.csrf().disable().authorizeRequests()
//				.antMatchers("/api/v2/all-devotees", "/api/v2/all-gurudwaras", "/api/v2/approve-gurudwara",
//						"/api/v2/block-gurudwara", "/api/v2/feeder-details", "/api/v2/admin-details",
//						"/api/v2/star-gurudwara", "/api/v2/get-devotee-details", "/api/v2/devotees-list",
//						"/api/v2/selected-gurudwara-details", "/api/v2/all-feeders", "/api/v2/all-messages",
//						"/api/v2/all-events", "/api/v2/all-surveys", "/api/v2/all-feedbacks",
//						"/api/v2/monthly-registered", "/api/v2/least-active", "/api/v2/superadmin-change-password",
//						"/api/v2/send-message", "/api/v2/upload-file", "/api/v2/get-sections",
//						"/api/v2/all-documents-paath-keerth", "/api/v2/delete-message",
//						"/api/v2/mark-as-popular-gurudwara", "/api/v2/report-messages", "/api/v2/all-counts",
//						"/api/v2/most-least-active-monthly-count", "/api/v2/range-counts", "/api/v2/forgot-password",
//						"/api/v2/super-admin-verify-otp", "/api/v2/sectionwise-documents-path-keerth",
//						"/api/v2/all-documents", "/api/v2/delete-report", "/api/v2/super-admin-create-survey",
//						"/api/v2/super-admin-create-event", "/api/v2/super-admin-event-details",
//						"/api/v2/super-admin-survey-response", "/api/v2/new-posts", "/api/v2/remove-all-messages",
//						"/api/v2/remove-all-feedbacks", "/api/v2/remove-all-events", "/api/v2/remove-all-surveys",
//						"/api/v2/remove-all-reports", "/api/v2/remove-app-user","/api/v2/manage-gurudwaras")
//				.hasAuthority(UserRole.SUPER_ADMIN.name())
//
//				.antMatchers("/api/v2/get-my-gurudwaras", "/api/v2/all-documents-paath-keerth", "/api/v2/all-documents",
//						"/api/v2/user-profile-update", "/api/v2/master-search", "/api/v2/upload-user-picture",
//						"/api/v2/logout", "/api/v2/search-gurudwaras", "/api/v2/follow-gurudwara",
//						"/api/v2/landing-data", "/api/v2/landing-page", "/api/v2/save-device-info",
//						"/api/v2/get-gurudwara-picture", "/api/v2/get-wall-data", "/api/v2/get-user-picture",
//						"/api/v2/locate-gurudwaras", "/api/v2/popular-gurudwara-list-country-statewise","/api/v2/remove-user-picture")
//				.hasAnyAuthority(UserRole.SUPER_ADMIN.name(), UserRole.ADMIN.name(), UserRole.DEVOTEE.name(),
//						UserRole.FEEDER.name(), UserRole.NO_ROLE.name())
//
//				.antMatchers("/api/v2/surveys", "/api/v2/events", "/api/v2/webcontents", "/api/v2/fav-gurudwaras",
//						"/api/v2/local-state-gurudwaras", "/api/v2/save-survey-response", "/api/v2/get-survey-result",
//						"/api/v2/getweb-response-count", "/api/v2/getevent-details", "/api/v2/events-dates",
//						"/api/v2/event-attendance", "/api/v2/save-web-content-response", "/api/v2/save-event-response",
//						"/api/v2/upload-user-picture", "/api/v2/muteNotification", "/api/v2/allow-notification",
//						"/api/v2/get-gurudwara-info", "/api/v2/unsubscribe-gurudwara", "/api/v2/count-post",
//						"/api/v2/master-search", "/api/v2/guru-profile-check", "/api/v2/emo-list", "/api/v2/feedback",
//						"/api/v2/notification-list", "/api/v2/seen-notification", "/api/v2/update-password",
//						"/api/v2/g-profile-check", "/api/v2/user-profile-update", "/api/v2/save-reg-id",
//						"/api/v2/g-dashboard", "/api/v2/count-members-in-month", "/api/v2/search", "/api/v2/get-feeder",
//						"/api/v2/get-devotee", "/api/v2/favorite-gurudwara", "/api/v2/search-bycountry-state",
//						"/api/v2/search-favourite", "/api/v2/add-to-favourite", "/api/v2/remove-fav-gurudwara",
//						"/api/v2/upload-image", "/api/v2/popular-gurudwara-list", "/api/v2/report-message")
//				.hasAnyAuthority(UserRole.ADMIN.name(), UserRole.FEEDER.name(), UserRole.DEVOTEE.name())
//
//				.antMatchers("/api/v2/create-event", "/api/v2/create-webcontent", "/api/v2/save-survey",
//						"/api/v2/create-message", "/api/v2/g-profile-update", "/api/v2/feeder-gurudwaraList")
//				.hasAnyAuthority(UserRole.ADMIN.name(), UserRole.FEEDER.name())
//
//				.antMatchers("/api/v2/delete-Post", "/api/v2/as-devotee", "/api/v2/as-admin", "/api/v2/deleteAdmin",
//						"/api/v2/get-manager", "/api/v2/block-feeder")
//				.hasAuthority(UserRole.ADMIN.name())
//
//				.antMatchers("/api/v2/convert-management", "/api/v2/manage-feeder", "/api/v2/block-devotee",
//						"/api/v2/delete-message", "/api/v2/ekam-sikh-messages", "/api/v2/ekam-sikh-surveys",
//						"/api/v2/ekam-sikh-events", "/api/v2/publish-survey","/api/v2/remove-gurudwara-picture")
//				.hasAnyAuthority(UserRole.SUPER_ADMIN.name(), UserRole.ADMIN.name())
//
//				.anyRequest().authenticated().and()
//
//				.addFilterBefore(new AuthenticationFilter(loginService, appUserRoleRepository),
//						BasicAuthenticationFilter.class)
//				.exceptionHandling().accessDeniedHandler(accessDeniedHandler);*/
//
//	}
//
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(demoAuthenticationProvider);
	}

} 


