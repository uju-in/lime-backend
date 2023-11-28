package com.programmers.bucketback.domains.feed.implementation;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;
import java.util.stream.LongStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
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
import com.programmers.bucketback.domains.feed.FeedBuilder;
import com.programmers.bucketback.domains.feed.domain.Feed;
import com.programmers.bucketback.domains.feed.domain.FeedItem;
import com.programmers.bucketback.domains.feed.domain.FeedLike;
import com.programmers.bucketback.domains.feed.model.FeedCreateServiceRequest;
import com.programmers.bucketback.domains.feed.model.FeedCreateServiceRequestBuilder;
import com.programmers.bucketback.domains.feed.repository.FeedLikeRepository;
import com.programmers.bucketback.domains.feed.repository.FeedRepository;
import com.programmers.bucketback.domains.item.domain.Item;
import com.programmers.bucketback.domains.item.domain.ItemBuilder;
import com.programmers.bucketback.domains.item.implementation.ItemReader;
import com.programmers.bucketback.error.BusinessException;

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

	@Mock
	private FeedReader feedReader;

	@Mock
	private FeedLikeRepository feedLikeRepository;

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

	@Nested
	@DisplayName("피드 좋아요 테스트")
	class FeedLikeTest {

		@Test
		@DisplayName("피드 좋아요를 한다.")
		void likeTest() {
			// given
			ArgumentCaptor<FeedLike> feedLikeArgumentCaptor = ArgumentCaptor.forClass(FeedLike.class);

			final Long memberId = 1L;
			final Long feedId = 1L;

			final Feed feed = FeedBuilder.build();

			FeedLike expectedFeedLike = new FeedLike(memberId, feed);

			given(feedReader.read(feedId))
				.willReturn(feed);

			given(feedReader.alreadyLiked(memberId, feed))
				.willReturn(false);

			// when
			feedAppender.like(memberId, feedId);

			// then
			then(feedLikeRepository).should().save(feedLikeArgumentCaptor.capture());
			FeedLike actualFeedLike = feedLikeArgumentCaptor.getValue();
			assertThat(actualFeedLike)
				.usingRecursiveComparison()
				.isEqualTo(expectedFeedLike);

		}

		@Test
		@DisplayName("이미 좋아요를 한 피드에 대해 좋아요를 한다.")
		void alreadyLikedTest() {
			// given
			final Long memberId = 1L;
			final Long feedId = 1L;

			final Feed feed = FeedBuilder.build();

			given(feedReader.read(feedId))
				.willReturn(feed);

			given(feedReader.alreadyLiked(memberId, feed))
				.willReturn(true);

			// when & then
			assertThatThrownBy(() -> feedAppender.like(memberId, feedId))
				.isInstanceOf(BusinessException.class);
		}
	}
}