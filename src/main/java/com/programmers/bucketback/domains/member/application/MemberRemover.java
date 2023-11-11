package com.programmers.bucketback.domains.member.application;

import org.springframework.stereotype.Component;

import com.programmers.bucketback.domains.common.MemberUtils;
import com.programmers.bucketback.domains.member.domain.Member;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberRemover {

	private final MemberReader memberReader;

	public void remove() {
		final Long memberId = MemberUtils.getCurrentMemberId();
		final Member member = memberReader.read(memberId);
		member.delete();
	}
}
