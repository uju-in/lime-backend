package com.programmers.bucketback.global.config.security.jwt;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
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
		return extractClaim(token, Claims::getSubject, jwtConfig.accessSecretKey());
	}

	public <T> T extractClaim(
		final String token,
		final Function<Claims, T> claimsTResolver,
		final String secretKey
	) {
		final Claims claims = extractAllClaims(token, secretKey);
		return claimsTResolver.apply(claims);
	}

	public String generateAccessToken(final String subject) {
		return Jwts.builder()
			.setSubject(subject)
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + jwtConfig.accessExpirationSeconds() * 1000L))
			.signWith(getSignInKey(jwtConfig.accessSecretKey()), SignatureAlgorithm.HS256)
			.compact();
	}

	public String generateRefreshToken() {
		return Jwts.builder()
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + jwtConfig.refreshExpirationSeconds() * 1000L))
			.signWith(getSignInKey(jwtConfig.refreshSecretKey()), SignatureAlgorithm.HS256)
			.compact();
	}

	public boolean isRefreshValidAndAccessInValid(
		final String refreshToken,
		final String accessToken
	) {
		isRefreshTokenValid(refreshToken);
		try {
			isAccessTokenValid(accessToken);
		} catch (JwtException e) {
			return true;
		}

		return false;
	}

	public boolean isAccessTokenValid(final String token) {
		try {
			return !isTokenExpired(token, jwtConfig.accessSecretKey());
		} catch (final ExpiredJwtException e) {
			throw new JwtException("Access Token이 만료되었습니다.");
		} catch (final JwtException e) {
			throw new JwtException("Access Token이 유효하지 않습니다.");
		}
	}

	public boolean isRefreshTokenValid(final String token) {
		try {
			return !isTokenExpired(token, jwtConfig.refreshSecretKey());
		} catch (final ExpiredJwtException e) {
			throw new JwtException("Refresh Token이 만료되었습니다.");
		} catch (final JwtException e) {
			throw new JwtException("Refresh Token이 유효하지 않습니다.");
		}
	}

	private boolean isTokenExpired(
		final String token,
		final String secretKey
	) {
		return extractExpiration(token, secretKey).before(new Date());
	}

	private Date extractExpiration(
		final String token,
		final String secretKey
	) {
		return extractClaim(token, Claims::getExpiration, secretKey);
	}

	private Claims extractAllClaims(
		final String token,
		final String secretKey
	) {
		return Jwts
			.parserBuilder()
			.setSigningKey(getSignInKey(secretKey))
			.build()
			.parseClaimsJws(token)
			.getBody();
	}

	private Key getSignInKey(final String secretKey) {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
