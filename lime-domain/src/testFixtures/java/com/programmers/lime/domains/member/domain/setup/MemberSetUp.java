package com.programmers.lime.domains.member.domain.setup;

import org.springframework.stereotype.Component;

import com.programmers.lime.domains.member.domain.Member;
import com.programmers.lime.domains.member.domain.MemberBuilder;
import com.programmers.lime.domains.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberSetUp {

	private final MemberRepository memberRepository;

	public Member saveOne(final Long memberId) {
		final Member member = MemberBuilder.build(memberId);

		return memberRepository.save(member);
	}
}
