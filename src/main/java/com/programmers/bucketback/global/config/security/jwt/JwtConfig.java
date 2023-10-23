package com.programmers.bucketback.global.config.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public record JwtConfig(
	String secretKey,
	long expirationSeconds
) {
}
