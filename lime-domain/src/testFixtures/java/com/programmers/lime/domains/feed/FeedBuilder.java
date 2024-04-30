package com.programmers.lime.domains.feed;

import org.springframework.test.util.ReflectionTestUtils;

import com.programmers.lime.domains.bucket.domain.BucketBuilder;
import com.programmers.lime.domains.bucket.domain.BucketInfo;
import com.programmers.lime.domains.feed.domain.Feed;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FeedBuilder {
	public static Feed build() {
		final BucketInfo bucketInfo = BucketBuilder.buildBucketInfo();
		final Feed feed = Feed.builder()
			.memberId(1L)
			.hobby(bucketInfo.getHobby())
			.content("농구 처음 시작하는데 아이템들 조합 어떙?")
			.build();

		setFeedId(feed);

		return feed;
	}

	private static void setFeedId(final Feed feed) {
		ReflectionTestUtils.setField(
			feed,
			"id",
			1L
		);
	}
}
