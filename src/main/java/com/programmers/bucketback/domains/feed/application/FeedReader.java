package com.programmers.bucketback.domains.feed.application;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.bucketback.domains.feed.application.dto.response.GetFeedServiceResponse;
import com.programmers.bucketback.domains.feed.application.vo.FeedInfo;
import com.programmers.bucketback.domains.feed.application.vo.FeedItemInfo;
import com.programmers.bucketback.domains.feed.domain.Feed;
import com.programmers.bucketback.domains.feed.domain.FeedItem;
import com.programmers.bucketback.domains.feed.repository.FeedLikeRepository;
import com.programmers.bucketback.domains.feed.repository.FeedRepository;
import com.programmers.bucketback.domains.member.application.MemberReader;
import com.programmers.bucketback.domains.member.application.vo.MemberInfo;
import com.programmers.bucketback.domains.member.domain.Member;
import com.programmers.bucketback.global.error.exception.EntityNotFoundException;
import com.programmers.bucketback.global.error.exception.ErrorCode;

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

	public GetFeedServiceResponse readFeed(
		final Long feedId,
		final Long memberId
	) {
		final Feed feed = read(feedId);
		final Long feedMemberId = feed.getMemberId();
		final Member member = memberReader.read(feedMemberId);
		final MemberInfo memberInfo = MemberInfo.from(member);

		final Boolean isLiked = isLiked(feed, memberId);
		final FeedInfo feedInfo = FeedInfo.of(feed, isLiked);

		final List<FeedItem> feedItems = feed.getFeedItems();
		final List<FeedItemInfo> feedItemInfos = getFeedItemInfos(feedItems);

		return new GetFeedServiceResponse(memberInfo, feedInfo, feedItemInfos);
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
