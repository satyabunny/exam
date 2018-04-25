package com.exam.portal.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.exam.portal.domain.ExamConstants;
import com.exam.portal.domain.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
	
	List<Question> findByQuestionTypeIn(List<ExamConstants> constants);
	
	Question findByQuestionId(Long questionId);
	
	@Query(value = "select CAST ( question_id as INTEGER) ,name from question where has_image = 'true'", nativeQuery=true)
	List<Object[]> getImageQuestions();
	
}
