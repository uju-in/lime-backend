package com.programmers.bucketback.domains.item.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.LongStream;

import com.programmers.bucketback.domains.item.domain.Item;
import com.programmers.bucketback.domains.item.domain.ItemBuilder;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class MemberItemSummaryBuilder {

	public static MemberItemSummary build(final Long itemId) {
		Item item = ItemBuilder.build(itemId);

		return new MemberItemSummary(
			"202301010000000000000" + itemId,
			LocalDateTime.of(2023, 1, 1, 0, 0, 0),
			ItemInfo.from(item)
		);
	}

	public static List<MemberItemSummary> buildMany() {
		return LongStream.range(1, 11)
			.mapToObj(MemberItemSummaryBuilder::build)
			.toList();
	}
}
