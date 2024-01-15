package com.programmers.lime.domains.auth;

import java.util.Collections;
import java.util.Map;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.programmers.lime.domains.member.domain.Member;
import com.programmers.lime.domains.member.implementation.MemberAppender;
import com.programmers.lime.domains.member.implementation.MemberReader;
import com.programmers.lime.domains.member.domain.vo.SocialType;
import com.programmers.lime.global.config.security.auth.CustomOauth2User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService {

	private final MemberAppender memberAppender;
	private final MemberReader memberReader;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		log.info("OAuth2UserService 실행");
		OAuth2User oAuth2User = super.loadUser(userRequest);
		Map<String, Object> attributes = oAuth2User.getAttributes();

		/**
		 * userRequest에서 registrationId 추출 후 registrationId으로 SocialType 저장
		 * http://localhost:8080/oauth2/authorization/kakao에서 kakao가 registrationId
		 * userNameAttributeName은 이후에 nameAttributeKey로 설정된다.
		 */
		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		SocialType socialType = getSocialType(registrationId);
		String userNameAttributeName = userRequest.getClientRegistration()
			.getProviderDetails()
			.getUserInfoEndpoint()
			.getUserNameAttributeName();

		// socialType에 따라 유저 정보를 통해 OAuthAttributes 객체 생성
		OAuthAttributes extractAttributes = OAuthAttributes.of(socialType, userNameAttributeName, attributes);

		Member member = getMember(extractAttributes, socialType);

		return new CustomOauth2User(
			Collections.singleton(new SimpleGrantedAuthority(member.getRole().getKey())),
			attributes,
			extractAttributes.getNameAttributeKey(),
			member.getId(),
			member.getRole()
		);
	}

	private Member getMember(
		final OAuthAttributes attributes,
		final SocialType socialType
	){
		Member foundMember = memberReader.readBySocialIdAndSocialType(
			attributes.getOauth2UserInfo().getSocialId(),
			socialType
		).orElse(null);

		if(foundMember == null){

			return saveMember(attributes, socialType);
		}

		return foundMember;
	}

	private Member saveMember(
		final OAuthAttributes attributes,
		final SocialType socialType
	) {
		Member member = attributes.toEntity(socialType, attributes.getOauth2UserInfo());

		return memberAppender.append(member);
	}

	private SocialType getSocialType(String registrationId) {
		String socialRegistrationId = registrationId.toUpperCase();

		if(SocialType.KAKAO.equals(SocialType.valueOf(socialRegistrationId))) {
			return SocialType.KAKAO;
		}
		return SocialType.GOOGLE;
	}
}
