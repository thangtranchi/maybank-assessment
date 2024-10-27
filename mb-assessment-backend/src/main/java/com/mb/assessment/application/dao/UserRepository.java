package com.mb.assessment.application.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mb.assessment.application.entity.Userlogin;


@Repository
public interface UserRepository extends JpaRepository<Userlogin, Long> {
	
	Userlogin findByUsername(String username);
}
