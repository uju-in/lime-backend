package com.programmers.lime.domains.feed.implementation;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.lime.domains.feed.domain.Feed;
import com.programmers.lime.domains.feed.domain.FeedItem;
import com.programmers.lime.domains.feed.model.FeedDetail;
import com.programmers.lime.domains.feed.model.FeedInfo;
import com.programmers.lime.domains.feed.model.FeedItemInfo;
import com.programmers.lime.domains.feed.repository.FeedLikeRepository;
import com.programmers.lime.domains.feed.repository.FeedRepository;
import com.programmers.lime.domains.member.domain.Member;
import com.programmers.lime.domains.member.implementation.MemberReader;
import com.programmers.lime.domains.member.model.MemberInfo;
import com.programmers.lime.error.EntityNotFoundException;
import com.programmers.lime.error.ErrorCode;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FeedReader {

	private final FeedRepository feedRepository;
	private final FeedLikeRepository feedLikeRepository;
	private final MemberReader memberReader;

	public Feed read(final Long feedId) {
		return feedRepository.findById(feedId)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.FEED_NOT_FOUND));
	}

	public Feed read(
		final Long feedId,
		final Long memberId
	) {
		return feedRepository.findByIdAndMemberId(feedId, memberId)
			.orElseThrow(() -> {
				throw new EntityNotFoundException(ErrorCode.FEED_NOT_FOUND);
			});
	}

	public boolean alreadyLiked(
		final Long memberId,
		final Feed feed
	) {
		return feedLikeRepository.existsByMemberIdAndFeed(memberId, feed);
	}

	public FeedDetail readDetail(
		final Long feedId,
		final Long memberId
	) {
		final Feed feed = read(feedId);
		final Long feedMemberId = feed.getMemberId();
		final Member feedOwner = memberReader.read(feedMemberId);
		final MemberInfo memberInfo = MemberInfo.from(feedOwner);

		final Boolean isLiked = isLiked(feed, memberId);
		final FeedInfo feedInfo = FeedInfo.of(feed, feed.getTotalPrice(), isLiked);

		final List<FeedItem> feedItems = feed.getFeedItems();
		final List<FeedItemInfo> feedItemInfos = getFeedItemInfos(feedItems);

		return new FeedDetail(memberInfo, feedInfo, feedItemInfos);
	}

	private Boolean isLiked(
		final Feed feed,
		final Long memberId
	) {
		if (memberId == null) {
			return null;
		}

		return feedLikeRepository.existsByMemberIdAndFeed(memberId, feed);
	}

	private List<FeedItemInfo> getFeedItemInfos(final List<FeedItem> feedItems) {
		return feedItems.stream()
			.map(FeedItemInfo::from)
			.toList();
	}
}
