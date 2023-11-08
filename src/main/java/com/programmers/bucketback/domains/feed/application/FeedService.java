package com.programmers.bucketback.domains.feed.application;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.programmers.bucketback.domains.comment.domain.Comment;
import com.programmers.bucketback.domains.common.MemberUtils;
import com.programmers.bucketback.domains.feed.application.dto.response.GetFeedServiceResponse;
import com.programmers.bucketback.domains.feed.application.vo.FeedCreateContent;
import com.programmers.bucketback.domains.feed.application.vo.FeedInfo;
import com.programmers.bucketback.domains.feed.application.vo.FeedItemInfo;
import com.programmers.bucketback.domains.feed.application.vo.FeedUpdateContent;
import com.programmers.bucketback.domains.feed.domain.Feed;
import com.programmers.bucketback.domains.feed.domain.FeedItem;
import com.programmers.bucketback.domains.feed.repository.FeedLikeRepository;
import com.programmers.bucketback.domains.member.application.MemberReader;
import com.programmers.bucketback.domains.member.application.vo.MemberInfo;
import com.programmers.bucketback.domains.member.domain.Member;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FeedService {

	private final FeedAppender feedAppender;
	private final FeedReader feedReader;
	private final FeedModifier feedModifier;
	private final FeedRemover feedRemover;
	private final FeedLikeRepository feedLikeRepository;
	private final MemberReader memberReader;

	/** 피드 생성 */
	public void createFeed(final FeedCreateContent content) {
		feedAppender.append(content);
	}

	/** 피드 수정 */
	public void modifyFeed(
		final Long feedId,
		final FeedUpdateContent toContent
	) {
		feedModifier.modify(feedId, toContent);
	}

	/** 피드 삭제 */
	public void deleteFeed(final Long feedId) {
		feedRemover.remove(feedId);
	}

	/** 피드 좋아요 */
	public void likeFeed(final Long feedId) {
		feedAppender.like(feedId);
	}

	/** 피드 좋아요 취소 */
	public void unLikeFeed(final Long feedId) {
		feedRemover.unlike(feedId);
	}

	/** 피드 상세 조회 **/
	public GetFeedServiceResponse getFeed(final Long feedId) {
		final Feed feed = feedReader.read(feedId);
		final Long memberId = feed.getMemberId();
		final Member member = memberReader.read(memberId);
		final MemberInfo memberInfo = new MemberInfo(memberId, member.getNickname());

		final boolean hasAdoptedComment = hasAdoptedComment(feed.getComments());
		final Boolean isLiked = isLiked(feed);
		final FeedInfo feedInfo = FeedInfo.of(feed, hasAdoptedComment, isLiked);

		final List<FeedItemInfo> feedItemInfos = getFeedItemInfos(feed.getFeedItems());

		return new GetFeedServiceResponse(memberInfo, feedInfo, feedItemInfos);
	}

	private Boolean isLiked(final Feed feed) {
		if (MemberUtils.isLoggedIn()) {
			final Long currentMemberId = MemberUtils.getCurrentMemberId();
			return feedLikeRepository.existsByMemberIdAndFeed(currentMemberId, feed);
		}
		return null;
	}

	private boolean hasAdoptedComment(final List<Comment> comments) {
		return comments.stream()
			.anyMatch(Comment::isAdoption);
	}

	private List<FeedItemInfo> getFeedItemInfos(final List<FeedItem> feedItems) {
		final List<FeedItemInfo> feedItemInfos = new ArrayList<>();
		for (final FeedItem feedItem : feedItems) {
			final FeedItemInfo feedItemInfo = FeedItemInfo.from(feedItem);
			feedItemInfos.add(feedItemInfo);
		}

		return feedItemInfos;
	}
}
