package com.programmers.bucketback.domains.item.domain;

import org.springframework.test.util.ReflectionTestUtils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberItemBuilder {

	public static MemberItem build() {
		Long memberId = 1L;
		Item item = ItemBuilder.build();
		MemberItem memberItem = new MemberItem(memberId, item);
		setItemId(memberItem, 1L);

		return memberItem;
	}

	private static void setItemId(
		final MemberItem memberItem,
		final Long id
	) {
		ReflectionTestUtils.setField(
			memberItem,
			"id",
			id
		);
	}
}
