package com.exam.portal.security;

import org.springframework.stereotype.Component;

@Component
public class SecurityAuthenticationProvider /*implements AuthenticationProvider */{

	/*private final UserInfoRepository userInfoRepository;

	@Autowired
	public SecurityAuthenticationProvider(UserInfoRepository userInfoRepository) {
		this.userInfoRepository = userInfoRepository;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		DemoAuthenticationToken demoAuthentication = (DemoAuthenticationToken) authentication;
		Optional<UserInfo> optionalUser = userInfoRepository.findById(demoAuthentication.getId());
		UserInfo user = optionalUser != null ? optionalUser.get() : null;

		if (user == null) {
			try {
				throw new UnknownUserException();
			} catch (UnknownUserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return user;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return DemoAuthenticationToken.class.isAssignableFrom(authentication);
	}*/
}
