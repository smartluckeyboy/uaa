package com.hcl.cloud.uaa.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hcl.cloud.uaa.bean.User;

@Repository
public interface UserRepository extends CrudRepository<User,String> {
    @Query(value="{'email' : ?0}")
    User findByEmail(String email);
}
