package com.programmers.lime.domains.auth.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.lime.domains.auth.api.dto.KakaoMemberResponse;
import com.programmers.lime.domains.member.api.dto.response.MemberLoginResponse;
import com.programmers.lime.domains.member.domain.Member;
import com.programmers.lime.domains.member.domain.vo.SocialType;
import com.programmers.lime.domains.member.implementation.MemberAppender;
import com.programmers.lime.domains.member.implementation.MemberReader;
import com.programmers.lime.global.config.security.jwt.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OAuthUserService {

	private final KakaoOAuthClient kakaoOAuthClient;
	private final MemberAppender memberAppender;
	private final MemberReader memberReader;
	private final JwtService jwtService;

	@Transactional
	public MemberLoginResponse login(String code) {
		String kakaoAccessToken = kakaoOAuthClient.getAccessToken(code);
		KakaoMemberResponse response = kakaoOAuthClient.getMemberInfo(kakaoAccessToken);

		Member foundMember = memberReader.readBySocialIdAndSocialType(
			response.id(),
			SocialType.KAKAO
		).orElseGet(() -> saveMember(response));

		String accessToken = jwtService.generateAccessToken(String.valueOf(foundMember.getId()));
		String refreshToken = jwtService.generateRefreshToken();

		return MemberLoginResponse.from(foundMember, accessToken, refreshToken);
	}

	private Member saveMember(final KakaoMemberResponse response) {
		return memberAppender.append(response.toEntity());
	}

}
