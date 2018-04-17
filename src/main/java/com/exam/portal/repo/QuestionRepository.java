package com.exam.portal.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.portal.domain.ExamConstants;
import com.exam.portal.domain.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
	
	List<Question> findByQuestionTypeIn(List<ExamConstants> constants);
	
	Question findByQuestionId(Long questionId);

}
