package com.programmers.bucketback.domains.member.application;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.programmers.bucketback.domains.member.application.dto.response.MemberCheckJwtServiceResponse;
import com.programmers.bucketback.domains.member.application.dto.response.MemberLoginServiceResponse;
import com.programmers.bucketback.domains.member.domain.Member;
import com.programmers.bucketback.domains.member.implementation.MemberReader;
import com.programmers.bucketback.global.config.security.SecurityManager;
import com.programmers.bucketback.global.util.MemberUtils;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberSecurityManager {

	private final SecurityManager securityManager;
	private final PasswordEncoder passwordEncoder;
	private final MemberReader memberReader;
	private final MemberUtils memberUtils;

	public MemberCheckJwtServiceResponse checkJwtToken() {
		final Long memberId = memberUtils.getCurrentMemberId();
		final Member member = memberReader.read(memberId);

		return new MemberCheckJwtServiceResponse(memberId, member.getNickname());
	}

	public MemberLoginServiceResponse login(
		final String rawPassword,
		final Member member
	) {
		final Long memberId = member.getId();
		final String nickname = member.getNickname();

		securityManager.authenticate(memberId, rawPassword);
		final String accessToken = securityManager.generateAccessToken(memberId);
		final String refreshToken = securityManager.generateRefreshToken(memberId);

		return new MemberLoginServiceResponse(memberId, nickname, accessToken, refreshToken);
	}

	public String encodePassword(final String password) {
		Member.validatePassword(password);
		return passwordEncoder.encode(password);
	}

	public void removeRefreshToken(final String refreshToken) {
		securityManager.removeRefreshToken(refreshToken);
	}
}
