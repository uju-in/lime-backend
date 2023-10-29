package com.programmers.bucketback.domains.member.application;

import org.springframework.stereotype.Component;

import com.programmers.bucketback.domains.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberChecker {

	private final MemberRepository memberRepository;

	public boolean checkNickname(final String nickname) {
		return !memberRepository.existsByNickname(nickname);
	}
}
