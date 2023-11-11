package com.programmers.bucketback.domains.member.application;

import org.springframework.stereotype.Component;

import com.programmers.bucketback.domains.member.domain.Member;
import com.programmers.bucketback.domains.member.repository.MemberRepository;
import com.programmers.bucketback.global.error.exception.EntityNotFoundException;
import com.programmers.bucketback.global.error.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberReader {

	private final MemberRepository memberRepository;

	public Member read(final Long memberId) {
		return memberRepository.findById(memberId)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
	}

	public Member read(final String email) {
		return memberRepository.findByLoginInfoEmail(email)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_LOGIN_FAIL));
	}

	public Member readByNickname(final String nickname) {
		return memberRepository.findByNickname(nickname)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
	}
}
