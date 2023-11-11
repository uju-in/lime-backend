package com.programmers.bucketback.domains.member.application;

import org.springframework.stereotype.Component;

import com.programmers.bucketback.domains.common.MemberUtils;
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
		final Long memberId = MemberUtils.getCurrentMemberId();
		final Member member = memberReader.read(memberId);
		member.updateProfile(nickname, introduction);
	}

	public void update(final String password) {
		final Long memberId = MemberUtils.getCurrentMemberId();
		final Member member = memberReader.read(memberId);
		member.updatePassword(password);
	}
}
