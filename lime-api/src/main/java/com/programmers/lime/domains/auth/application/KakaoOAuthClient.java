package com.programmers.lime.domains.auth.application;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.programmers.lime.domains.auth.api.dto.KakaoMemberResponse;
import com.programmers.lime.domains.auth.api.dto.KakaoOAuthLoginInfo;
import com.programmers.lime.domains.auth.api.dto.OAuthAccessTokenResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class KakaoOAuthClient {

	private static final RestTemplate restTemplate = new RestTemplate();
	private final KakaoOAuthLoginInfo kakaoOAuthLoginInfo;

	public String getAccessToken(final String code) {
		MultiValueMap<String, String> loginInfoRequest = makeKakaoLoginInfo(code);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(loginInfoRequest, headers);

		OAuthAccessTokenResponse response = restTemplate.postForEntity(
			"https://kauth.kakao.com/oauth/token",
			httpEntity,
			OAuthAccessTokenResponse.class
		).getBody();

		return response.accessToken();
	}

	private MultiValueMap<String, String> makeKakaoLoginInfo(final String code) {
		MultiValueMap<String, String> loginInfoRequest = new LinkedMultiValueMap<>();

		loginInfoRequest.add("grant_type", kakaoOAuthLoginInfo.grantType());
		loginInfoRequest.add("client_id", kakaoOAuthLoginInfo.clientId());
		loginInfoRequest.add("client_secret", kakaoOAuthLoginInfo.clientSecret());
		loginInfoRequest.add("redirect_uri", kakaoOAuthLoginInfo.redirectUri());
		loginInfoRequest.add("code", code);

		return loginInfoRequest;
	}

	public KakaoMemberResponse getMemberInfo(final String accessToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(accessToken);
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		HttpEntity<Void> request = new HttpEntity<>(headers);

		KakaoMemberResponse response = restTemplate.exchange(
			"https://kapi.kakao.com/v2/user/me",
			HttpMethod.GET,
			request,
			KakaoMemberResponse.class
		).getBody();

		return response;
	}


}
