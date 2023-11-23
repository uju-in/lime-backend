package com.programmers.bucketback.global.util;

import org.springframework.stereotype.Component;

import com.programmers.bucketback.domains.member.domain.Member;
import com.programmers.bucketback.domains.member.implementation.MemberReader;
import com.programmers.bucketback.error.BusinessException;
import com.programmers.bucketback.error.ErrorCode;
import com.programmers.bucketback.global.config.security.SecurityUtils;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public final class MemberUtils {

	private final MemberReader memberReader;

	public Long getCurrentMemberId() {
		final Long memberId = SecurityUtils.getCurrentMemberId();

		if (memberId == null) {
			return null;
		}

		final Member member = memberReader.read(memberId);

		validateMember(member);

		return memberId;
	}

	public Member getCurrentMember() {
		final Long memberId = SecurityUtils.getCurrentMemberId();
		final Member member = memberReader.read(memberId);

		validateMember(member);

		return member;
	}

	private void validateMember(final Member member) {
		if (member.isDeleted()) {
			throw new BusinessException(ErrorCode.MEMBER_DELETED);
		}
	}
}
