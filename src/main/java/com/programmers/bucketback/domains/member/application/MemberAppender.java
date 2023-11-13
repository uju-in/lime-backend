package com.programmers.bucketback.domains.member.application;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.programmers.bucketback.domains.member.domain.Member;
import com.programmers.bucketback.domains.member.domain.vo.LoginInfo;
import com.programmers.bucketback.domains.member.domain.vo.Role;
import com.programmers.bucketback.domains.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberAppender {

	private final PasswordEncoder passwordEncoder;
	private final MemberRepository memberRepository;

	public void append(
		final LoginInfo loginInfo,
		final String nickname
	) {
		final String rawPassword = loginInfo.getPassword();
		Member.validatePassword(rawPassword);
		final String encodedPassword = passwordEncoder.encode(rawPassword);

		final Member member = Member.builder()
			.email(loginInfo.getEmail())
			.password(encodedPassword)
			.nickname(nickname)
			.role(Role.USER)
			.build();

		memberRepository.save(member);
	}
}
