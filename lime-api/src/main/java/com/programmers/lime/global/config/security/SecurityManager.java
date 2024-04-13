package com.programmers.lime.global.config.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import com.programmers.lime.global.config.security.jwt.JwtService;
import com.programmers.lime.redis.token.RefreshTokenManager;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SecurityManager {

	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	private final RefreshTokenManager refreshTokenManager;

	public void authenticate(
		final Long memberId,
		final String password
	) {
		final UsernamePasswordAuthenticationToken authenticationToken =
			new UsernamePasswordAuthenticationToken(memberId, password);

		authenticationManager.authenticate(authenticationToken);
	}

	public String generateAccessToken(final Long memberId) {
		return jwtService.generateAccessToken(String.valueOf(memberId));
	}

	public String generateRefreshToken(final Long memberId) {
		final String refreshToken = jwtService.generateRefreshToken();
		refreshTokenManager.addRefreshToken(refreshToken, memberId);

		return refreshToken;
	}

	public void removeRefreshToken(final String refreshToken) {
		refreshTokenManager.deleteRefreshToken(refreshToken);
	}

	public String reissueAccessToken(final String refreshToken, final String authorizationHeader) {
		final String accessToken = authorizationHeader.substring(7);

		if (jwtService.isRefreshValidAndAccessInValid(refreshToken, accessToken)) {
			final Long memberId = refreshTokenManager.getMemberId(refreshToken);

			if (memberId == null) {
				throw new JwtException("Refresh Token이 유효하지 않습니다.");
			}

			return generateAccessToken(memberId);
		}

		if (jwtService.isAccessTokenValid(accessToken)) {
			return accessToken;
		}

		throw new JwtException("Refresh Token이 유효하지 않습니다.");
	}
}
