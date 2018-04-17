package com.exam.portal.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.exam.portal.domain.UserInfo;
import com.exam.portal.dto.ErrorObject;
import com.exam.portal.dto.LoginDTO;
import com.exam.portal.dto.RegistrationDTO;
import com.exam.portal.dto.ReturnHolder;
import com.exam.portal.dto.UserInfoDTO;
import com.exam.portal.repo.UserInfoRepository;
import com.exam.portal.service.LoginService;

@RestController
@RequestMapping(value="/user")
public class LoginController {
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private UserInfoRepository userInfoRepository;
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public ReturnHolder registerUser(@RequestBody RegistrationDTO registrationDTO) {
		ReturnHolder holder = new ReturnHolder();
		try {
			if (registrationDTO != null) {
				if (registrationDTO.getEmail() != null) {
					System.out.println(" called..............>!" + registrationDTO.getEmail());
					UserInfo userInfo = userInfoRepository.findByEmail(registrationDTO.getEmail());
					System.out.println(" called..............>!");
					if (userInfo != null) {
						return new ReturnHolder(false, new ErrorObject("err04", "Email Already Exists.."));
					}
					UserInfoDTO userInfoDTO = loginService.registerUser(registrationDTO);
					holder.setResult(userInfoDTO);
				} else {
					holder = new ReturnHolder(false, new ErrorObject("err03", "Empty Email"));
				}
			} else {
				holder = new ReturnHolder(false, new ErrorObject("err01", "empty values"));
			}
		} catch (Exception e) {
			holder = new ReturnHolder(false, new ErrorObject("err02", "Unable to Register."));
		}

		return holder;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ExceptionHandler()
	public ReturnHolder login(@RequestBody LoginDTO loginBean) {
		ReturnHolder customResponse = new ReturnHolder();
		try {
			UserInfoDTO dataResponse = loginService.login(loginBean.getEmail(), loginBean.getPassword());
			if (dataResponse != null) {
				customResponse.setResult(dataResponse);
				customResponse.setMessage("User logged in succesfully");
			} else {
				customResponse = new ReturnHolder(false,
						new ErrorObject("err001", "User name and password don't match"));
			}
		} catch (Exception e) {
			customResponse = new ReturnHolder(false, new ErrorObject("err500", e.getMessage()));
		}
		return customResponse;
	}
	
	/**
	 * To logout
	 * 
	 * @param userId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public ReturnHolder logout(HttpServletRequest request) {
		ReturnHolder holder = new ReturnHolder();
		try {
			loginService.logout(request.getHeader("authToken"));
		} catch (Exception e) {
			e.printStackTrace();
			holder = new ReturnHolder(false, new ErrorObject("err500", e.getMessage()));
		}
		return holder;
	}

}
