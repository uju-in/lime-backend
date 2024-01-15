package com.programmers.lime.domains.auth.vo;

import java.util.Map;

public class GoogleOAuth2UserInfo extends Oauth2UserInfo {
	public GoogleOAuth2UserInfo(final Map<String, Object> attributes) {
		super(attributes);
	}

	@Override
	public String getSocialId() {
		return (String) attributes.get("sub");
	}

	@Override
	public String getEmail() {
		return null;
	}

	@Override
	public String getProfileImage() {
		return (String) attributes.get("picture");
	}
}
