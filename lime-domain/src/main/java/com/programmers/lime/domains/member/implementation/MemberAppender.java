package com.programmers.lime.domains.member.implementation;

import org.springframework.stereotype.Component;

import com.programmers.lime.domains.member.domain.Member;
import com.programmers.lime.domains.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberAppender {

	private final MemberRepository memberRepository;

	public Member append(
		final Member member
	) {
  		return memberRepository.save(member);
	}

}
