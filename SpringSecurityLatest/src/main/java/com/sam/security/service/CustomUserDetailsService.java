package com.sam.security.service;

import java.util.Objects;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.sam.security.config.CustomUserDetails;
import com.sam.security.entity.MyUser;
import com.sam.security.repo.UserRepo;


@Component
public class CustomUserDetailsService implements UserDetailsService{

	private final UserRepo userRepo;
	
	
	public CustomUserDetailsService(UserRepo userRepo) {
		super();
		this.userRepo = userRepo;
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		MyUser user=userRepo.findByUsername(username);
		if(!Objects.nonNull(user)) {
			System.out.println("User Not Available");
			throw new UsernameNotFoundException("User Not Available");
		}
		return new CustomUserDetails(user);
	}

}
