package com.programmers.lime.domains.feed.implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.lime.domains.feed.domain.Feed;
import com.programmers.lime.domains.feed.domain.FeedItem;
import com.programmers.lime.domains.feed.domain.FeedLike;
import com.programmers.lime.domains.feed.model.FeedCreateServiceRequest;
import com.programmers.lime.domains.feed.repository.FeedLikeRepository;
import com.programmers.lime.domains.feed.repository.FeedRepository;
import com.programmers.lime.domains.item.domain.Item;
import com.programmers.lime.domains.item.implementation.ItemReader;
import com.programmers.lime.error.BusinessException;
import com.programmers.lime.error.ErrorCode;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FeedAppender {

	private final FeedRepository feedRepository;
	private final FeedLikeRepository feedLikeRepository;
	private final ItemReader itemReader;
	private final FeedReader feedReader;

	private static final int MAX_FEED_ITEMS = 3;

	/** 피드 생성 */
	@Transactional
	public Long append(
		final Long memberId,
		final FeedCreateServiceRequest request
	) {

		List<FeedItem> feedItems = createFeedItems(request.itemIdRegistry().itemIds());
		validateFeedItems(feedItems);

		Feed feed = Feed.builder()
			.memberId(memberId)
			.content(request.content())
			.hobby(request.hobby())
			.budget(request.budget())
			.build();
		feedItems.forEach(feed::addFeedItem);

		return feedRepository.save(feed).getId();
	}

	/** 피드 아이템 생성 */
	public List<FeedItem> createFeedItems(final List<Long> itemIds) {
		return itemIds.stream()
			.distinct()
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

	private void validateFeedItems(final List<FeedItem> feedItems) {
		if (feedItems.size() > MAX_FEED_ITEMS){
			throw new BusinessException(ErrorCode.FEED_ITEMS_EXCEED);
		}
	}
}
