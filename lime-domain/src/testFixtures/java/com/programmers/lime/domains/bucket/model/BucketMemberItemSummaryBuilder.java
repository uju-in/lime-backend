package com.programmers.lime.domains.bucket.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.LongStream;

import com.programmers.lime.domains.item.domain.ItemBuilder;
import com.programmers.lime.domains.item.model.ItemInfo;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BucketMemberItemSummaryBuilder {

	public static List<BucketMemberItemSummary> buildMany(final int size) {
		return LongStream.range(0, size)
			.mapToObj(i -> build())
			.toList();
	}

	private static BucketMemberItemSummary build() {
		return new BucketMemberItemSummary(
			"20230124430000",
			false,
			LocalDateTime.of(2023, 1, 1, 0, 0, 0),
			ItemInfo.from(ItemBuilder.build())
		);
	}

}
