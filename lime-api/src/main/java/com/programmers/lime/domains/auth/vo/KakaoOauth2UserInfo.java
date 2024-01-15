package com.programmers.lime.domains.auth.vo;

import java.util.Map;

public class KakaoOauth2UserInfo extends Oauth2UserInfo {
	public KakaoOauth2UserInfo(final Map<String, Object> attributes) {
		super(attributes);
	}

	@Override
	public String getSocialId() {
		return String.valueOf(attributes.get("id"));
	}

	@Override
	public String getEmail() {
		Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");
		if (account == null) {
			return null;
		}

		return (String) account.get("email");
	}

	@Override
	public String getProfileImage() {
		Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");

		if (account == null) {
			return null;
		}

		Map<String, Object> profile = (Map<String, Object>) account.get("profile");

		if (profile == null) {
			return null;
		}

		return (String) profile.get("thumbnail_image_url");
	}
}
