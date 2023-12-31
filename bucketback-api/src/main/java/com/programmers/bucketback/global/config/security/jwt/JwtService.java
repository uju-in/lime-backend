package com.programmers.bucketback.global.config.security.jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtService {

	private final JwtConfig jwtConfig;

	public String extractUsername(final String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public <T> T extractClaim(
		final String token,
		final Function<Claims, T> claimsTResolver
	) {
		final Claims claims = extractAllClaims(token);
		return claimsTResolver.apply(claims);
	}

	public String generateToken(final UserDetails userDetails) {
		return generateToken(new HashMap<>(), userDetails);
	}

	public String generateToken(
		final Map<String, Object> extraClaims,
		final UserDetails userDetails
	) {
		return Jwts.builder()
			.setClaims(extraClaims)
			.setSubject(userDetails.getUsername())
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + 1000 * jwtConfig.expirationSeconds()))
			.signWith(getSignInKey(), SignatureAlgorithm.HS256)
			.compact();
	}

	public boolean isTokenValid(
		final String token,
		final UserDetails userDetails
	) {
		try {
			final String username = extractUsername(token);
			return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
		} catch (ExpiredJwtException e) {
			return false;
		}
	}

	private boolean isTokenExpired(final String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(final String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	private Claims extractAllClaims(final String token) {
		return Jwts
			.parserBuilder()
			.setSigningKey(getSignInKey())
			.build()
			.parseClaimsJws(token)
			.getBody();
	}

	private Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(jwtConfig.secretKey());
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
