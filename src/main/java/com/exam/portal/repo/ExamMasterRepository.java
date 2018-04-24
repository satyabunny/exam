package com.exam.portal.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.exam.portal.domain.ExamMaster;

public interface ExamMasterRepository extends JpaRepository<ExamMaster , Long> {
	
	@Query(value="select * from exam_master e order by created_date DESC limit 1", nativeQuery = true)
	ExamMaster findLatest(); 
}
