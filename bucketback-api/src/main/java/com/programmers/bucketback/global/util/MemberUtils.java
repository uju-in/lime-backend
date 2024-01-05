package com.programmers.bucketback.global.util;

import org.springframework.stereotype.Component;

import com.programmers.bucketback.domains.member.domain.Member;
import com.programmers.bucketback.domains.member.implementation.MemberReader;
import com.programmers.bucketback.global.config.security.SecurityUtils;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public final class MemberUtils {

	private final MemberReader memberReader;

	public Long getCurrentMemberId() {
		return SecurityUtils.getCurrentMemberId();
	}

	public Member getCurrentMember() {
		final Long memberId = SecurityUtils.getCurrentMemberId();

		return memberReader.read(memberId);
	}
}
