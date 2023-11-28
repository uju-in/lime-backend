package com.programmers.bucketback.domains.feed.implementation;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;
import java.util.stream.LongStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.programmers.bucketback.domains.bucket.domain.Bucket;
import com.programmers.bucketback.domains.bucket.domain.BucketBuilder;
import com.programmers.bucketback.domains.bucket.domain.BucketItem;
import com.programmers.bucketback.domains.bucket.implementation.BucketReader;
import com.programmers.bucketback.domains.feed.domain.Feed;
import com.programmers.bucketback.domains.feed.domain.FeedItem;
import com.programmers.bucketback.domains.feed.model.FeedCreateServiceRequest;
import com.programmers.bucketback.domains.feed.model.FeedCreateServiceRequestBuilder;
import com.programmers.bucketback.domains.feed.repository.FeedRepository;
import com.programmers.bucketback.domains.item.domain.Item;
import com.programmers.bucketback.domains.item.domain.ItemBuilder;
import com.programmers.bucketback.domains.item.implementation.ItemReader;

@ExtendWith(MockitoExtension.class)
class FeedAppenderTest {

	@InjectMocks
	private FeedAppender feedAppender;

	@Mock
	private FeedRepository feedRepository;

	@Mock
	private BucketReader bucketReader;

	@Mock
	private ItemReader itemReader;

	@Test
	@DisplayName("피드 생성 테스트를 한다.")
	void feedAppendTest() {
		// given
		ArgumentCaptor<Feed> feedArgumentCaptor = ArgumentCaptor.forClass(Feed.class);

		Long memberId = 1L;
		Bucket bucket = BucketBuilder.build();
		FeedCreateServiceRequest request = FeedCreateServiceRequestBuilder.build(bucket.getId());

		given(bucketReader.read(request.bucketId(), memberId))
			.willReturn(bucket);

		given(itemReader.read(anyLong())).willAnswer(
			invocation -> bucket.getBucketItems().stream()
				.filter(bucketItem -> bucketItem.getItem().getId() == invocation.getArgument(0))
				.findFirst()
				.map(BucketItem::getItem)
				.orElseThrow(() -> new AssertionError("아이템이 없습니다."))
		);

		Feed expectedFeed = Feed.builder()
			.memberId(memberId)
			.hobby(bucket.getHobby())
			.content(request.content())
			.bucketName(bucket.getName())
			.bucketBudget(bucket.getBudget())
			.build();

		List<FeedItem> feedItems = bucket.getBucketItems().stream()
			.map(bucketItem -> new FeedItem(bucketItem.getItem()))
			.toList();

		feedItems.forEach(expectedFeed::addFeedItem);

		given(feedRepository.save(any(Feed.class)))
			.willReturn(expectedFeed);

		// when
		feedAppender.append(memberId, request);

		// then
		then(feedRepository).should().save(feedArgumentCaptor.capture());

		Feed actualFeed = feedArgumentCaptor.getValue();
		assertThat(actualFeed)
			.usingRecursiveComparison()
			.isEqualTo(expectedFeed);
	}

	@Test
	@DisplayName("중복된 아이템 id에 대한 피드 아이템 생성 테스트를 한다.")
	void createFeedItemsTest() {
		// given
		List<Long> itemIds = List.of(1L, 1L, 2L, 3L, 3L, 3L);

		List<Item> items = LongStream.range(1L, 3L)
			.mapToObj(ItemBuilder::build)
			.toList();

		List<FeedItem> expectedFeedItems = items.stream()
			.map(FeedItem::new)
			.toList();

		given(itemReader.read(anyLong())).willAnswer(
			invocation -> items.stream()
				.filter(item -> item.getId() == invocation.getArgument(0))
				.findFirst()
				.orElseThrow(() -> new AssertionError("아이템이 없습니다."))
		);

		// when
		List<FeedItem> actualFeedItems = feedAppender.createFeedItems(itemIds);

		// then
		assertThat(actualFeedItems)
			.usingRecursiveComparison()
			.isEqualTo(expectedFeedItems);
	}

}