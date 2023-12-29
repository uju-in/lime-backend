package com.programmers.bucketback.global.config.security;

import org.springframework.cache.CacheManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import com.programmers.bucketback.global.config.security.jwt.JwtService;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SecurityManager {

	public static final String REFRESH_TOKEN_CACHE = "refreshToken";

	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	private final CacheManager cacheManager;

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
		final String refreshToken = jwtService.generateRefreshToken();
		cacheManager.getCache(REFRESH_TOKEN_CACHE).put(refreshToken, memberId);

		return refreshToken;
	}

	public void removeRefreshToken(final String refreshToken) {
		cacheManager.getCache(REFRESH_TOKEN_CACHE).evict(refreshToken);
	}

	public String reissueAccessToken(final String refreshToken, final String authorizationHeader) {
		final String accessToken = authorizationHeader.substring(7);

		if (jwtService.isRefreshValidAndAccessInValid(refreshToken, accessToken)) {
			final Long memberId = cacheManager.getCache(REFRESH_TOKEN_CACHE).get(refreshToken, Long.class);

			if (memberId == null) {
				throw new RuntimeException("Refresh Token이 유효하지 않습니다.");
			}

			return generateAccessToken(Long.valueOf(memberId));
		}

		if (jwtService.isRefreshAndAccessValid(refreshToken, accessToken)) {
			return accessToken;
		}

		throw new JwtException("Access Token을 재발급 할 수 없습니다.");
	}
}
