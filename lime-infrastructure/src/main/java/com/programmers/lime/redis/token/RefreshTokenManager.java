package com.programmers.lime.redis.token;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RefreshTokenManager {

	public static final int EXPIRE_TIME = 1209600;
	public static final String key = "REFRESH_TOKEN_";
	private final RedisTemplate<String, Object> redisTemplate;

	public void addRefreshToken(
		final String refreshToken,
		final Long memberId
	) {
		redisTemplate.opsForValue().set(key + refreshToken, memberId);
		redisTemplate.expire(key + refreshToken, EXPIRE_TIME, TimeUnit.SECONDS);
	}

	public Long getMemberId(final String refreshToken) {
		Object memberId = redisTemplate.opsForValue().get(key + refreshToken);

		return memberId == null ? null : ((Integer) memberId).longValue();
	}

	public void deleteRefreshToken(final String refreshToken) {
		redisTemplate.delete(key + refreshToken);
	}
}
