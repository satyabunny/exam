package com.exam.portal.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.exam.portal.domain.UserInfo;

@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
	
	UserInfo findByEmail(String email);

}
