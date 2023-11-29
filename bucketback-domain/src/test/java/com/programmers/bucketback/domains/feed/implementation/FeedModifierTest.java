package com.programmers.bucketback.domains.feed.implementation;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.programmers.bucketback.domains.feed.FeedBuilder;
import com.programmers.bucketback.domains.feed.domain.Feed;
import com.programmers.bucketback.domains.feed.domain.FeedContent;
import com.programmers.bucketback.domains.feed.model.FeedUpdateServiceRequest;

@ExtendWith(MockitoExtension.class)
class FeedModifierTest {

	@Mock
	private FeedReader feedReader;

	@InjectMocks
	private FeedModifier feedModifier;

	@Test
	@DisplayName("피드를 수정한다.")
	void modify() {
		// given
		Long memberId = 1L;
		Feed feed = FeedBuilder.build();
		String expectedContent = "수정된 피드";

		given(feedReader.read(feed.getId(), memberId))
			.willReturn(feed);

		FeedUpdateServiceRequest updateServiceRequest = new FeedUpdateServiceRequest(expectedContent);

		// when
		feedModifier.modify(memberId, feed.getId(), updateServiceRequest);

		// then
		FeedContent content = feed.getContent();
		String actualFeedContent = content.getContent();

		assertThat(actualFeedContent).isEqualTo(expectedContent);
	}
}
