package com.sam.security.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sam.security.entity.MyUser;

@Repository
public interface UserRepo extends JpaRepository<MyUser, Integer> {

	MyUser findByUsername(String username);

}
