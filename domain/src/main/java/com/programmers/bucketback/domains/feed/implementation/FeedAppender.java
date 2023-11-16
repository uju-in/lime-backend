package com.programmers.bucketback.domains.feed.implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.bucketback.domains.bucket.domain.Bucket;
import com.programmers.bucketback.domains.bucket.implementation.BucketReader;
import com.programmers.bucketback.domains.feed.domain.Feed;
import com.programmers.bucketback.domains.feed.domain.FeedItem;
import com.programmers.bucketback.domains.feed.domain.FeedLike;
import com.programmers.bucketback.domains.feed.model.FeedCreateServiceRequest;
import com.programmers.bucketback.domains.feed.repository.FeedLikeRepository;
import com.programmers.bucketback.domains.feed.repository.FeedRepository;
import com.programmers.bucketback.domains.item.domain.Item;
import com.programmers.bucketback.domains.item.implementation.ItemReader;
import com.programmers.bucketback.error.exception.BusinessException;
import com.programmers.bucketback.error.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FeedAppender {

	private final FeedRepository feedRepository;
	private final FeedLikeRepository feedLikeRepository;
	private final BucketReader bucketReader;
	private final ItemReader itemReader;
	private final FeedReader feedReader;

	/** 피드 생성 */
	@Transactional
	public Long append(
		final Long memberId,
		final FeedCreateServiceRequest request
	) {
		Bucket bucket = bucketReader.read(request.bucketId(), memberId);
		List<Long> itemIds = bucket.getBucketItems().stream()
			.map(bucketItem -> bucketItem.getItem().getId())
			.toList();
		List<FeedItem> feedItems = createFeedItems(itemIds);

		Feed feed = Feed.builder()
			.memberId(memberId)
			.content(request.content())
			.hobby(bucket.getHobby())
			.bucketName(bucket.getName())
			.bucketBudget(bucket.getBudget())
			.build();
		feedItems.forEach(feed::addFeedItem);

		return feedRepository.save(feed).getId();
	}

	/** 피드 아이템 생성 */
	public List<FeedItem> createFeedItems(final List<Long> itemIds) {
		return itemIds.stream()
			.map(itemId -> {
				Item item = itemReader.read(itemId);
				FeedItem feedItem = new FeedItem(item);

				return feedItem;
			})
			.distinct()
			.collect(Collectors.toList());
	}

	/** 피드 좋아요 */
	@Transactional
	public void like(
		final Long memberId,
		final Long feedId
	) {
		Feed feed = feedReader.read(feedId);

		if (feedReader.alreadyLiked(memberId, feed)) {
			throw new BusinessException(ErrorCode.FEED_ALREADY_LIKED);
		}

		FeedLike feedLike = new FeedLike(memberId, feed);

		feedLikeRepository.save(feedLike);
	}
}
