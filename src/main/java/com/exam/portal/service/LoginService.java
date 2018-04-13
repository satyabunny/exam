package com.exam.portal.service;

import com.exam.portal.domain.UserInfo;
import com.exam.portal.dto.RegistrationDTO;
import com.exam.portal.dto.UserInfoDTO;

public interface LoginService {

	UserInfoDTO login(String email, String password) throws Exception;

	boolean isLoggedIn(String xAuthToken) throws Exception;

	void changePassword(String xAuthToken, UserInfoDTO appUserBean) throws Exception;

	void logout(String token) throws Exception;

	UserInfo getUser(String xAuthToken) throws Exception;
	
	String encryptPasswordSHA256(String password);
	
	UserInfoDTO registerUser(RegistrationDTO registrationDTO);
}
