package com.programmers.bucketback.domains.member.domain;

import org.springframework.test.util.ReflectionTestUtils;

import com.programmers.bucketback.domains.member.domain.vo.Role;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberBuilder {

	public static Member build(final Long memberId) {
		Member member = Member.builder()
			.nickname("nickname")
			.email("email@naver.com")
			.password("password")
			.role(Role.USER)
			.build();

		setMemberId(member, memberId);

		return member;
	}

	public static Member build() {
		Member member = Member.builder()
			.nickname("nickname")
			.email("email@naver.com")
			.password("password")
			.role(Role.USER)
			.build();

		setMemberId(member);

		return member;
	}

	private static void setMemberId(
		final Member member
	) {
		ReflectionTestUtils.setField(
			member,
			"id",
			1L
		);
	}

	private static void setMemberId(
		final Member member,
		final Long id
	) {
		ReflectionTestUtils.setField(
			member,
			"id",
			id
		);
	}
}
