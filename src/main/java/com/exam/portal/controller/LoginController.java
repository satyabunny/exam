package com.exam.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.exam.portal.dto.ErrorObject;
import com.exam.portal.dto.LoginDTO;
import com.exam.portal.dto.RegistrationDTO;
import com.exam.portal.dto.ReturnHolder;
import com.exam.portal.dto.UserInfoDTO;
import com.exam.portal.service.LoginService;

@RestController
@RequestMapping(value="/user")
public class LoginController {
	
	@Autowired
	private LoginService loginService;
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public ReturnHolder registerUser(@RequestBody RegistrationDTO registrationDTO) {
		ReturnHolder holder = new ReturnHolder();
		try {
			if (registrationDTO != null) {
				if (registrationDTO.getEmail() != null) {
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

}
