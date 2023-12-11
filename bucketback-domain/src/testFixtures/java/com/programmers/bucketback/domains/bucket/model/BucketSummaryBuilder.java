package com.programmers.bucketback.domains.bucket.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.LongStream;

import com.programmers.bucketback.domains.item.domain.ItemBuilder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BucketSummaryBuilder {

	public static List<BucketSummary> buildMany(final int size) {
		return LongStream.range(0, size)
			.mapToObj(i -> build())
			.toList();
	}

	private static BucketSummary build() {
		return new BucketSummary(
			"20230124430000",
			1L,
			"유러피안 농구",
			100000,
			90000,
			LocalDateTime.of(2023, 1, 1, 0, 0, 0),
			ItemBuilder.buildItemImages()
		);
	}
}
