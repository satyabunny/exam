package com.exam.portal.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.exam.portal.domain.ExamConstants;
import com.exam.portal.domain.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
	
	List<Question> findByQuestionTypeIn(List<ExamConstants> constants);
	
	Question findByQuestionId(Long questionId);
	
	@Query(value = "select count(*), user_id from user_test_status where isCorrectAnswered = 'true' group by user_id limit 25",nativeQuery=true)
	List<Object[]> getResults();

}
