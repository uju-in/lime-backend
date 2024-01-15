package com.programmers.lime.domains.member.application;

import org.springframework.stereotype.Component;

import com.programmers.lime.domains.member.application.dto.response.MemberCheckJwtServiceResponse;
import com.programmers.lime.domains.member.domain.Member;
import com.programmers.lime.domains.member.implementation.MemberReader;
import com.programmers.lime.global.config.security.SecurityManager;
import com.programmers.lime.global.util.MemberUtils;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberSecurityManager {

	private final SecurityManager securityManager;
	private final MemberReader memberReader;
	private final MemberUtils memberUtils;

	public MemberCheckJwtServiceResponse checkJwtToken() {
		final Long memberId = memberUtils.getCurrentMemberId();
		final Member member = memberReader.read(memberId);

		return new MemberCheckJwtServiceResponse(memberId, member.getNickname());
	}

	public void removeRefreshToken(final String refreshToken) {
		securityManager.removeRefreshToken(refreshToken);
	}

	public String reissueAccessToken(
		final String refreshToken,
		final String authorizationHeader
	) {
		return securityManager.reissueAccessToken(refreshToken, authorizationHeader);
	}
}
