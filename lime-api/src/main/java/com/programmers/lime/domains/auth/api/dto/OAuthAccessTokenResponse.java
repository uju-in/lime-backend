package com.programmers.lime.domains.auth.api.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public record OAuthAccessTokenResponse(
	String tokenType,
	String accessToken,
	Integer expiresIn,
	String refreshToken,
	Integer refreshTokenExpiresIn
) {
}