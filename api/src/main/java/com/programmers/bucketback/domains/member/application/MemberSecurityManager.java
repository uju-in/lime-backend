package com.programmers.bucketback.domains.member.application;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.programmers.bucketback.domains.member.application.dto.response.MemberLoginServiceResponse;
import com.programmers.bucketback.domains.member.domain.Member;
import com.programmers.bucketback.global.config.security.SecurityManager;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberSecurityManager {

	private final SecurityManager securityManager;
	private final PasswordEncoder passwordEncoder;

	public MemberLoginServiceResponse login(
		final String rawPassword,
		final Member member
	) {
		final Long memberId = member.getId();
		final String nickname = member.getNickname();

		securityManager.authenticate(memberId, rawPassword);
		final String jwtToken = securityManager.generateToken(member);

		return new MemberLoginServiceResponse(memberId, nickname, jwtToken);
	}

	public String encodePassword(final String password) {
		Member.validatePassword(password);
		return passwordEncoder.encode(password);
	}
}
