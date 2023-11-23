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

		readMember(memberId);

		return memberId;
	}

	public Member getCurrentMember() {
		final Long memberId = SecurityUtils.getCurrentMemberId();

		return readMember(memberId);
	}

	private Member readMember(final Long memberId) {
		final Member member = memberReader.read(memberId);

		if (member.isDeleted()) {
			throw new BusinessException(ErrorCode.MEMBER_DELETED);
		}

		return member;
	}
}
