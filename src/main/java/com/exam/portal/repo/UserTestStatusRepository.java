package com.exam.portal.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.portal.domain.Question;
import com.exam.portal.domain.UserInfo;
import com.exam.portal.domain.UserTestStatus;

public interface UserTestStatusRepository extends JpaRepository<UserTestStatus, Long> {
	
	UserTestStatus findByQuestionAndInfo(Question question, UserInfo info);

}
