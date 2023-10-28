package com.programmers.bucketback.domains.member.application;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.programmers.bucketback.domains.member.domain.Member;
import com.programmers.bucketback.domains.member.domain.Role;
import com.programmers.bucketback.domains.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberAppender {

	private final PasswordEncoder passwordEncoder;
	private final MemberRepository memberRepository;

	public void append(
		final String email,
		final String password,
		final String nickname
	) {
		final Member member = Member.builder()
			.email(email)
			.password(passwordEncoder.encode(password))
			.nickname(nickname)
			.role(Role.USER)
			.build();

		memberRepository.save(member);
	}
}
