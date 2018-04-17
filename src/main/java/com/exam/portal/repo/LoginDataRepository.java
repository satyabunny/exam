package com.exam.portal.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.portal.domain.LoginData;
import com.exam.portal.domain.UserInfo;

public interface LoginDataRepository extends JpaRepository<LoginData, Long> {
	LoginData findByAuthToken(String authTocken);
	LoginData findByUserInfo(UserInfo userInfo);
}
