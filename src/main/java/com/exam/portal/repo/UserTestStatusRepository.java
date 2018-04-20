package com.exam.portal.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.exam.portal.domain.Question;
import com.exam.portal.domain.UserInfo;
import com.exam.portal.domain.UserTestStatus;

public interface UserTestStatusRepository extends JpaRepository<UserTestStatus, Long> {
	
	UserTestStatus findByQuestionAndInfo(Question question, UserInfo info);
	
	List<UserTestStatus> findByInfo(UserInfo info);

	@Query(value = "select CAST ( count(*) as INTEGER), CAST ( info_user_infoid as INTEGER) from user_test_status where is_correct_answered = 'true' group by info_user_infoid order by count(*) DESC limit 25",nativeQuery=true)
	List<Object[]> getResults();
}
