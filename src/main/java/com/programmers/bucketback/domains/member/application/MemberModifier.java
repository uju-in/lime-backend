package com.programmers.bucketback.domains.member.application;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.bucketback.domains.common.MemberUtils;
import com.programmers.bucketback.domains.member.domain.Member;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberModifier {

	private final MemberReader memberReader;

	@Transactional
	public void modify(
		final String nickname,
		final String introduction
	) {
		final Long memberId = MemberUtils.getCurrentMemberId();
		final Member member = memberReader.read(memberId);
		member.updateProfile(nickname, introduction);
	}

	@Transactional
	public void modify(final String password) {
		final Long memberId = MemberUtils.getCurrentMemberId();
		final Member member = memberReader.read(memberId);
		member.updatePassword(password);
	}
}
