package com.sam.security.service;

import java.util.Objects;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sam.security.entity.MyUser;
import com.sam.security.repo.UserRepo;

@Service
public class UserService {
	
	private final UserRepo userRepo;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	
	public UserService(UserRepo userRepo, BCryptPasswordEncoder bCryptPasswordEncoder,AuthenticationManager authenticationManager,JwtService jwtService) {
		super();
		this.userRepo = userRepo;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.authenticationManager=authenticationManager;
		this.jwtService=jwtService;
	}
	
	
	public MyUser registerUser(MyUser user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		return userRepo.save(user);
	}


	public String verify(MyUser user) {
//		String username=user.getUsername();
//		var u=userRepo.findByUsername(username);
		Authentication auth=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));		
		if(auth.isAuthenticated())
			return jwtService.generateToken(user);
		return "failure";
	}
	
	
}
