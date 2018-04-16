package com.exam.portal.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.portal.domain.Test;

public interface TestRepository extends JpaRepository<Test, Long> {

}
