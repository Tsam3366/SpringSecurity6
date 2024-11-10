package com.sam.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	
	private final UserDetailsService userDetailsService;
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	
	
	public WebSecurityConfig(UserDetailsService userDetailsService,JwtAuthenticationFilter jwtAuthenticationFilter) {
		this.userDetailsService = userDetailsService;
		this.jwtAuthenticationFilter=jwtAuthenticationFilter;
	}


	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
			.csrf(csrf->csrf.disable())
			.authorizeHttpRequests(
					request -> request
					.requestMatchers("/auth/api/register","/auth/api/login").permitAll()
					.anyRequest().authenticated())
//			.formLogin(Customizer.withDefaults())
			.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
			.httpBasic(Customizer.withDefaults());
	
		return httpSecurity.build();
	}
	
//	
//	@Bean
//	public UserDetailsService userDetailsService() {
//		UserDetails sam=User.withUsername("sam")
//				.password("{noop}sam")
//				.roles("Admin").build();
//		
//		UserDetails queen=User.withUsername("queen")
//				.password("{noop}queen")
//				.roles("User").build();
//		
//		return new InMemoryUserDetailsManager(sam,queen);
//		
//	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder(14);
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetailsService);
//		daoAuthenticationProvider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
		daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
		return daoAuthenticationProvider;
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

}
