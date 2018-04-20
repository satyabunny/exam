package com.exam.portal.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.portal.domain.Test;
import com.exam.portal.domain.UserInfo;

public interface TestRepository extends JpaRepository<Test, Long> {
	
	Test findByGivenBy(UserInfo user);
	
}
