package com.programmers.bucketback.domains.item.domain;

import java.util.List;

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

	public static List<MemberItem> buildMany() {
		MemberItem build1 = MemberItemBuilder.build();
		MemberItem build2 = MemberItemBuilder.build();
		return List.of(build1, build2);
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
