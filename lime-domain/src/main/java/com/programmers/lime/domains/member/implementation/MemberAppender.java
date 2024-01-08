package com.programmers.lime.domains.member.implementation;

import org.springframework.stereotype.Component;

import com.programmers.lime.domains.member.domain.Member;
import com.programmers.lime.domains.member.domain.vo.Role;
import com.programmers.lime.domains.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberAppender {

	private final MemberRepository memberRepository;

	public void append(
		final Member member
	) {
  		memberRepository.save(member);
	}

	public Member append(
		final String email,
		final String encodedPassword,
		final String nickname
	) {
		final Member member = Member.builder()
			.email(email)
			.password(encodedPassword)
			.nickname(nickname)
			.role(Role.USER)
			.build();

		return memberRepository.save(member);
	}
}
