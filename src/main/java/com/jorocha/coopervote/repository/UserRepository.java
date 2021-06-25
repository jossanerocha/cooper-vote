package com.jorocha.coopervote.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.jorocha.coopervote.domain.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
	
	User findByUsername(String username);

	User findByUsernameAndPassword(String username, String password);

}
