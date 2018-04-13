package com.exam.portal.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.portal.domain.LoginData;

public interface LoginDataRepository extends JpaRepository<LoginData, Long> {
	LoginData findByAuthToken(String authTocken);
}
