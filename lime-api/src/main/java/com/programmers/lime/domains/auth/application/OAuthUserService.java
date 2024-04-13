package com.programmers.lime.domains.auth.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.lime.domains.auth.api.dto.KakaoMemberResponse;
import com.programmers.lime.domains.auth.application.dto.MemberLoginServiceResponse;
import com.programmers.lime.domains.member.domain.Member;
import com.programmers.lime.domains.member.domain.vo.SocialType;
import com.programmers.lime.domains.member.implementation.MemberAppender;
import com.programmers.lime.domains.member.implementation.MemberReader;
import com.programmers.lime.global.config.security.SecurityManager;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OAuthUserService {

	private final KakaoOAuthClient kakaoOAuthClient;
	private final MemberAppender memberAppender;
	private final MemberReader memberReader;
	private final SecurityManager securityManager;

	@Transactional
	public MemberLoginServiceResponse login(final String code) {
		String kakaoAccessToken = kakaoOAuthClient.getAccessToken(code);
		KakaoMemberResponse response = kakaoOAuthClient.getMemberInfo(kakaoAccessToken);

		Member foundMember = memberReader.readBySocialIdAndSocialType(
			response.id(),
			SocialType.KAKAO
		).orElseGet(() -> saveMember(response));

		String accessToken = securityManager.generateAccessToken(foundMember.getId());
		String refreshToken = securityManager.generateRefreshToken(foundMember.getId());

		return MemberLoginServiceResponse.from(foundMember, accessToken, refreshToken);
	}

	private Member saveMember(final KakaoMemberResponse response) {
		return memberAppender.append(response.toEntity());
	}

}
