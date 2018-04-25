package com.exam.portal.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.exam.portal.domain.Answer;
import com.exam.portal.domain.Question;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
	
	Answer findByAnswerText(String text);
	Answer findByAnswerTextAndQuestion(String text, Question question);
	Answer findByAnswerId(Long answerId);
	@Query(value = "select * from answer where question_question_id =?", nativeQuery = true)
	List<Answer> findAnswersByQuestion(Long questionId);

}
