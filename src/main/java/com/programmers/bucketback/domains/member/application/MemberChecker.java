package com.programmers.bucketback.domains.member.application;

import org.springframework.stereotype.Component;

import com.programmers.bucketback.domains.member.repository.MemberRepository;
import com.programmers.bucketback.global.error.exception.BusinessException;
import com.programmers.bucketback.global.error.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberChecker {

	private final MemberRepository memberRepository;

	public boolean checkNicknameDuplication(final String nickname) {
		return memberRepository.existsByNickname(nickname);
	}

	public void checkEmailDuplication(final String email) {
		if (memberRepository.existsByLoginInfoEmail(email)) {
			throw new BusinessException(ErrorCode.MEMBER_EMAIL_EXIST);
		}
	}
}
