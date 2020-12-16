package com.defencefirst.pki.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.defencefirst.pki.model.User;



public interface UserRepository extends JpaRepository<User,Long> {

	User findByUsername(String username);


}
