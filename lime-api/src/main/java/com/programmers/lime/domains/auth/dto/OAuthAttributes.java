package com.programmers.lime.domains.auth.dto;

import java.util.Map;
import java.util.UUID;

import com.programmers.lime.domains.auth.vo.GoogleOAuth2UserInfo;
import com.programmers.lime.domains.auth.vo.KakaoOAuth2UserInfo;
import com.programmers.lime.domains.auth.vo.OAuth2UserInfo;
import com.programmers.lime.domains.member.domain.Member;
import com.programmers.lime.domains.member.domain.vo.Role;
import com.programmers.lime.domains.member.domain.vo.SocialType;
import com.programmers.lime.domains.member.domain.vo.SocialInfo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuthAttributes {

	private String nameAttributeKey; // 키가 되는 필드값(pk)
	private OAuth2UserInfo oauth2UserInfo; // 소셜 유저 정보

	@Builder
	OAuthAttributes(
		final String nameAttributeKey,
		final OAuth2UserInfo oauth2UserInfo
	){
		this.nameAttributeKey = nameAttributeKey;
		this.oauth2UserInfo = oauth2UserInfo;
	}

	public static OAuthAttributes of(
		final SocialType socialType,
		final String userNameAttributeName,
		final Map<String, Object> attributes
	) {
		if(socialType == SocialType.KAKAO){
			return ofKakao(userNameAttributeName,attributes);
		}
		return ofGoogle(userNameAttributeName, attributes);
	}

	public Member toEntity(
		final SocialType socialType,
		final OAuth2UserInfo oauth2UserInfo
	){
		SocialInfo socialInfo = SocialInfo.builder()
			.socialId(oauth2UserInfo.getSocialId())
			.email(oauth2UserInfo.getEmail())
			.profileImage(oauth2UserInfo.getProfileImage())
			.role(Role.GUEST)
			.socialType(socialType)
			.build();

		long timestamp = System.currentTimeMillis();
		String randomString = String.valueOf(UUID.randomUUID()).replace("-", "").substring(0, 8);
		String randomNickname = timestamp + randomString;

		return new Member(socialInfo, randomNickname);
	}

	private static OAuthAttributes ofKakao(
		final String userNameAttributeName,
		final Map<String, Object> attributes
	) {
		return OAuthAttributes.builder()
			.nameAttributeKey(userNameAttributeName)
			.oauth2UserInfo(new KakaoOAuth2UserInfo(attributes))
			.build();
	}

	private static OAuthAttributes ofGoogle(
		final String userNameAttributeName,
		final Map<String, Object> attributes
	) {
		return OAuthAttributes.builder()
			.nameAttributeKey(userNameAttributeName)
			.oauth2UserInfo(new GoogleOAuth2UserInfo(attributes))
			.build();
	}
}
