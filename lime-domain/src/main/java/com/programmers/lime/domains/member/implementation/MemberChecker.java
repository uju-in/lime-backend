package com.programmers.lime.domains.member.implementation;

import org.springframework.stereotype.Component;

import com.programmers.lime.domains.member.repository.MemberRepository;
import com.programmers.lime.error.BusinessException;
import com.programmers.lime.error.ErrorCode;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberChecker {

	private final MemberRepository memberRepository;

	public void checkNicknameDuplication(final String nickname) {
		if (memberRepository.existsByNicknameNickname(nickname)) {
			throw new BusinessException(ErrorCode.MEMBER_NICKNAME_DUPLICATE);
		}
	}

	public void checkEmailDuplication(final String email) {
		if (memberRepository.existsByLoginInfoEmail(email)) {
			throw new BusinessException(ErrorCode.MEMBER_EMAIL_EXIST);
		}
	}
}
