package com.exam.portal.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.portal.domain.Answer;
import com.exam.portal.domain.Question;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
	
	Answer findByAnswerText(String text);
	Answer findByAnswerTextAndQuestion(String text, Question question);

}
