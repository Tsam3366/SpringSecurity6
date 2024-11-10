package com.sam.security.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sam.security.entity.MyUser;
import com.sam.security.repo.UserRepo;
import com.sam.security.service.UserService;

@RestController
@RequestMapping("/auth/api")
public class UserController {
		
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/register")
	public MyUser registerUser(@RequestBody MyUser user) {
		return userService.registerUser(user);
	}
	
	@PostMapping("/login")
	public String login(@RequestBody MyUser user) {
		
		return userService.verify(user);
		
	}

}
