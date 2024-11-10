package com.sam.security.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.sam.security.entity.MyUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	public String generateToken(MyUser user) {
		Map<String, Object> claims = new HashMap<>();
		return Jwts.builder().claims().add(claims).issuer("SSS").subject(user.getUsername())
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + 60 * 10 * 1000)).and().signWith(getSecretKey())
				.compact();
	}

	private SecretKey getSecretKey() {
		byte[] decode = Decoders.BASE64.decode(getKey());
		return Keys.hmacShaKeyFor(decode);
	}

	private String getKey() {
		return "c2VjdXJlY29kZWRpZ2l0a2V5Zm9yY29tcGxleGl0eW5vZmZsaW5lCg==";
	}

	public String extractUsername(String jwt) {
		return extractClaims(jwt, Claims::getSubject);
	}

	private <T> T extractClaims(String jwt, Function<Claims, T> claimResolver) {
		// TODO Auto-generated method stub
		Claims claims = extractClaims(jwt);
		return claimResolver.apply(claims);
	}

	private Claims extractClaims(String jwt) {
		// TODO Auto-generated method stub
		return Jwts.parser().verifyWith(getSecretKey()).build().parseSignedClaims(jwt).getPayload();
	}

	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String userName = extractUsername(token);
		return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	private boolean isTokenExpired(String jwt) {
		// TODO Auto-generated method stub
		return extractExpiration(jwt).before(new Date());
	}

	private Date extractExpiration(String jwt) {
		// TODO Auto-generated method stub
		return extractClaims(jwt, Claims::getExpiration);
	}

}
