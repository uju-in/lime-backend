package com.programmers.bucketback.domains.member.application;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.bucketback.domains.member.domain.Member;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberModifier {

	private final MemberReader memberReader;
	private final PasswordEncoder passwordEncoder;

	@Transactional
	public void modifyProfile(
		final Long memberId,
		final String nickname,
		final String introduction
	) {
		final Member member = memberReader.read(memberId);

		member.updateProfile(nickname, introduction);
	}

	@Transactional
	public void modifyPassword(
		final Long memberId,
		final String password
	) {
		final Member member = memberReader.read(memberId);
		Member.validatePassword(password);
		final String encodedPassword = passwordEncoder.encode(password);

		member.updatePassword(encodedPassword);
	}
}
