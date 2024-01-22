package com.programmers.lime.domains.member.implementation;

import org.springframework.stereotype.Component;

import com.programmers.lime.domains.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberChecker {

	private final MemberRepository memberRepository;

	public boolean checkNicknameDuplication(final String nickname) {
		return memberRepository.existsByNicknameNickname(nickname);
	}
}
