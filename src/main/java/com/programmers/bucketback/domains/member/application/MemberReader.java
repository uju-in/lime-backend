package com.programmers.bucketback.domains.member.application;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.bucketback.domains.member.domain.Member;
import com.programmers.bucketback.domains.member.repository.MemberRepository;
import com.programmers.bucketback.global.error.exception.EntityNotFoundException;
import com.programmers.bucketback.global.error.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberReader {

	private final MemberRepository memberRepository;

	public Member read(final Long memberId) {
		return memberRepository.findById(memberId)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
	}

	public Member readByEmail(final String email) {
		return memberRepository.findByLoginInfoEmail(email)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_LOGIN_FAIL));
	}

	public Member readByNickname(final String nickname) {
		return memberRepository.findByNicknameNickname(nickname)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
	}
}
