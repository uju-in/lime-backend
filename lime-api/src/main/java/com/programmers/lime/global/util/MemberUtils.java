package com.programmers.lime.global.util;

import org.springframework.stereotype.Component;

import com.programmers.lime.domains.member.domain.Member;
import com.programmers.lime.domains.member.implementation.MemberReader;
import com.programmers.lime.global.config.security.SecurityUtils;

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
