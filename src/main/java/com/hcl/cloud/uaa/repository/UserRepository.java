package com.hcl.cloud.uaa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.cloud.uaa.bean.User;

@Repository("UserRepository")
public interface UserRepository extends JpaRepository<User,String> {
	
    User findByEmail(String email);
}
