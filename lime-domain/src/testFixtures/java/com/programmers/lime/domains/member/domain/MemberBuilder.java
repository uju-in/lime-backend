package com.programmers.lime.domains.member.domain;

import org.springframework.test.util.ReflectionTestUtils;

import com.programmers.lime.domains.member.domain.vo.Role;
import com.programmers.lime.domains.member.domain.vo.SocialInfo;
import com.programmers.lime.domains.member.domain.vo.SocialType;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberBuilder {

	public static Member build(final Long memberId) {
		final SocialInfo socialInfo = new SocialInfo(
			"357935205",
			"test@test.com",
			"1.png",
			SocialType.NAVER,
			Role.USER
		);
		final Member member = new Member(socialInfo, "nickname");

		setMemberId(member, memberId);

		return member;
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
