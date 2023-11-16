package com.programmers.bucketback.domains.feed.application;

import org.springframework.stereotype.Service;

import com.programmers.bucketback.domains.feed.implementation.FeedAppender;
import com.programmers.bucketback.domains.feed.implementation.FeedCursorReader;
import com.programmers.bucketback.domains.feed.implementation.FeedModifier;
import com.programmers.bucketback.domains.feed.implementation.FeedReader;
import com.programmers.bucketback.domains.feed.implementation.FeedRemover;
import com.programmers.bucketback.domains.feed.model.FeedCreateServiceRequest;
import com.programmers.bucketback.domains.feed.model.FeedUpdateServiceRequest;
import com.programmers.bucketback.global.util.MemberUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FeedService {

	private final FeedAppender feedAppender;
	private final FeedReader feedReader;
	private final FeedModifier feedModifier;
	private final FeedRemover feedRemover;
	private final FeedCursorReader feedCursorReader;

	/** 피드 생성 */
	public Long createFeed(final FeedCreateServiceRequest request) {
		Long memberId = MemberUtils.getCurrentMemberId();

		return feedAppender.append(memberId, request);
	}

	/** 피드 수정 */
	public void modifyFeed(
		final Long feedId,
		final FeedUpdateServiceRequest request
	) {
		Long memberId = MemberUtils.getCurrentMemberId();
		feedModifier.modify(memberId, feedId, request);
	}

	// public FeedGetByCursorServiceResponse getFeedByCursor(
	// 	final String hobbyName,
	// 	final String nickName,
	// 	final String sortCondition,
	// 	final CursorPageParameters parameters
	// ) {
	// 	FeedSortCondition feedSortCondition = FeedSortCondition.from(sortCondition);
	// 	Long loginMemberId = MemberUtils.getCurrentMemberId();
	//
	// 	return feedCursorReader.getFeedByCursor(
	// 		hobbyName,
	// 		nickName,
	// 		loginMemberId,
	// 		feedSortCondition,
	// 		parameters
	// 	);
	// }

	/** 피드 삭제 */
	public void deleteFeed(final Long feedId) {
		Long memberId = MemberUtils.getCurrentMemberId();
		feedRemover.remove(memberId, feedId);
	}

	/** 피드 좋아요 */
	public void likeFeed(final Long feedId) {
		Long memberId = MemberUtils.getCurrentMemberId();
		feedAppender.like(memberId, feedId);
	}

	/** 피드 좋아요 취소 */
	public void unLikeFeed(final Long feedId) {
		Long memberId = MemberUtils.getCurrentMemberId();
		feedRemover.unlike(memberId, feedId);
	}

	/** 피드 상세 조회 **/
	// public FeedGetServiceResponse getFeed(final Long feedId) {
	// 	Long memberId = MemberUtils.getCurrentMemberId();
	//
	// 	return feedReader.readFeed(feedId, memberId);
	// }
}
