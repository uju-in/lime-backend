package com.programmers.bucketback.domains.feed;

import org.springframework.test.util.ReflectionTestUtils;

import com.programmers.bucketback.common.model.Hobby;
import com.programmers.bucketback.domains.feed.domain.Feed;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FeedBuilder {
	public static Feed build() {
		final Feed feed = Feed.builder()
			.memberId(1L)
			.hobby(Hobby.BASKETBALL)
			.content("농구 처음 시작하는데 아이템들 조합 어떙?")
			.bucketName("농구 초보자 세트")
			.bucketBudget(100000)
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
