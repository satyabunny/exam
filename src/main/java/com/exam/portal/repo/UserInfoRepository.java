package com.exam.portal.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.portal.domain.UserInfo;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
	
	UserInfo findByEmail(String email);

}
