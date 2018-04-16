package com.exam.portal.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.portal.domain.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {

}
