package com.programmers.lime.domains.auth.api.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "oauth.kakao.login")
public record KakaoOAuthLoginInfo(
	String grantType,
	String clientId,
	String clientSecret,
	String redirectUri
) {
}