package com.programmers.bucketback.domains.member.application;

import org.springframework.stereotype.Component;

import com.programmers.bucketback.domains.member.domain.Member;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberUpdater {

	private final MemberReader memberReader;

	public void update(
		final String nickname,
		final String introduction
	) {
		final Member member = memberReader.read();
		member.updateProfile(nickname, introduction);
	}

	public void update(final String password) {
		final Member member = memberReader.read();
		member.updatePassword(password);
	}
}
