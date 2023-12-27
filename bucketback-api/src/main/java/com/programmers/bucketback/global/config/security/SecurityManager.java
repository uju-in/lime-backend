package com.programmers.bucketback.global.config.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import com.programmers.bucketback.global.config.security.jwt.JwtService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SecurityManager {

	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;

	public void authenticate(
		final Long memberId,
		final String password
	) {
		final UsernamePasswordAuthenticationToken authenticationToken =
			new UsernamePasswordAuthenticationToken(memberId, password);

		authenticationManager.authenticate(authenticationToken);
	}

	public String generateAccessToken(final Long memberId) {
		return jwtService.generateAccessToken(memberId.toString());
	}

	public String generateRefreshToken(final Long memberId) {
		return jwtService.generateRefreshToken(memberId.toString());
	}
}
