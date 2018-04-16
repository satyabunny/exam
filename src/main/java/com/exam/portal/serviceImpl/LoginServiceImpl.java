package com.exam.portal.serviceImpl;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Random;

import org.apache.commons.codec.binary.Base64;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exam.portal.domain.Course;
import com.exam.portal.domain.LoginData;
import com.exam.portal.domain.UserInfo;
import com.exam.portal.dto.RegistrationDTO;
import com.exam.portal.dto.UserInfoDTO;
import com.exam.portal.repo.LoginDataRepository;
import com.exam.portal.repo.UserInfoRepository;
import com.exam.portal.service.LoginService;
import com.exam.portal.util.Utility;

@Service
public class LoginServiceImpl implements LoginService {
	
	private static final Mapper mapper = new DozerBeanMapper();
	
	@Autowired
	UserInfoRepository userInfoRepository;
	
	@Autowired
	LoginDataRepository loginDataRepository;
	
	@Override
	public UserInfoDTO login(String email, String password) throws Exception {

		if (!Utility.isValidEmail(email)) {
			throw new Exception("please enter valid email");
		}

		UserInfo user = userInfoRepository.findByEmail(email);
		UserInfoDTO response = null;
		if (user != null) {
				String salt = user.getSalt();
				String passwordWithSalt = password + salt;
				String encPass = encryptPasswordSHA256(passwordWithSalt);
				if (encPass.equals(user.getPassword())) {
					response = mapper.map(user, UserInfoDTO.class);
					LoginData loginData = new LoginData();
					loginData.setUserInfo(user);
				 	loginData.setAuthToken(getAuthToken(user));
					loginDataRepository.save(loginData);
					response.setUserInfoID(user.getUserInfoID());
					response.setAuthToken(loginData.getAuthToken());
				} else {
					throw new Exception("Please enter correct password");
				}
		} else {
			throw new Exception("Email does not exist");
		}
		return response;
	}

	@Override
	public boolean isLoggedIn(String xAuthToken) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void changePassword(String xAuthToken, UserInfoDTO appUserBean) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void logout(String token) throws Exception {
		LoginData loginData = loginDataRepository.findByAuthToken(token);
		if (loginData != null) {
			loginDataRepository.delete(loginData);
		}
	}

	@Override
	public UserInfo getUser(String xAuthToken) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * To encrypt the password
	 * 
	 * @param password
	 * @return encrypted password
	 */
	@Override
	public String encryptPasswordSHA256(String password) {
		String hashPasswordWithSalt = null;
		try {
			final MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
			final byte[] passBytes = password.getBytes();
			hashPasswordWithSalt = Base64.encodeBase64String(sha256.digest(passBytes));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hashPasswordWithSalt;

	}
	
	public String getAuthToken(UserInfo user) {
		final String key = user.getEmail().concat(String.valueOf(System.currentTimeMillis()));
		final byte[] authTokenBytes = Base64.encodeBase64(key.getBytes());
		final String authToken = new String(authTokenBytes);
		LoginData logindata = loginDataRepository.findByAuthToken(authToken);
		if (logindata != null) {
			getAuthToken(user);
		}
		return authToken;
	}

	@Override
	public UserInfoDTO registerUser(RegistrationDTO registrationDTO) {
		UserInfo info = new UserInfo();
		UserInfoDTO dto = new UserInfoDTO();
		if (registrationDTO != null) {
			mapper.map(registrationDTO, info);
		}
		setUserPwdAndSalt(info, registrationDTO.getPassword());
		userInfoRepository.save(info);
		
		mapper.map(info, dto);
		
		return dto;
	}
	
	public void setUserPwdAndSalt(UserInfo info, String userPassword) {
		final Random random = new SecureRandom();
		final byte[] saltArray = new byte[32];
		random.nextBytes(saltArray);
		final String randomSalt = Base64.encodeBase64String(saltArray);
		final String passwordWithSalt = userPassword + randomSalt;
		String hashedPasswordWithSalt = encryptPasswordSHA256(passwordWithSalt);
		info.setPassword(hashedPasswordWithSalt);
		info.setSalt(randomSalt);
	}

}
