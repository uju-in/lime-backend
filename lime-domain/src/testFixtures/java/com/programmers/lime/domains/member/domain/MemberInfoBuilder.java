package com.programmers.lime.domains.member.domain;

import com.programmers.lime.domains.member.model.MemberInfo;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberInfoBuilder {

	public static MemberInfo build(final Long id) {
		return MemberInfo.builder()
			.memberId(id)
			.nickname("nickname" + id)
			.profileImage("profileImage" + id)
			.level(id.intValue())
			.build();
	}
}
