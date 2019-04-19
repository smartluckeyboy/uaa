package com.hcl.cloud.uaa.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.hcl.cloud.uaa.bean.JwtToken;

@Repository
public interface JwtTokenRepository extends MongoRepository<JwtToken,String> {
	 @Query(value="{'email' : ?0}")
	 JwtToken findByEmail(String email);
	 
	 @Query(value="{'token' : ?0}")
	 JwtToken findBytoken(String token);
}
