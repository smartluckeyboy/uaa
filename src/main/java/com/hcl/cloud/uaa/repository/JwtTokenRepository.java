package com.hcl.cloud.uaa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.cloud.uaa.bean.JwtToken;

@Repository
public interface JwtTokenRepository extends JpaRepository<JwtToken,String> {
	 
	 JwtToken findByEmail(String email);
	 
	 JwtToken findBytoken(String token);
}
