package com.programmers.bucketback.domains.feed.application;

import org.springframework.stereotype.Service;

import com.programmers.bucketback.common.cursor.CursorPageParameters;
import com.programmers.bucketback.common.cursor.CursorSummary;
import com.programmers.bucketback.common.model.Hobby;
import com.programmers.bucketback.domains.feed.application.dto.response.FeedGetServiceResponse;
import com.programmers.bucketback.domains.feed.implementation.FeedAppender;
import com.programmers.bucketback.domains.feed.implementation.FeedCursorReader;
import com.programmers.bucketback.domains.feed.implementation.FeedModifier;
import com.programmers.bucketback.domains.feed.implementation.FeedReader;
import com.programmers.bucketback.domains.feed.implementation.FeedRemover;
import com.programmers.bucketback.domains.feed.model.FeedCreateServiceRequest;
import com.programmers.bucketback.domains.feed.model.FeedCursorSummaryLike;
import com.programmers.bucketback.domains.feed.model.FeedDetail;
import com.programmers.bucketback.domains.feed.model.FeedSortCondition;
import com.programmers.bucketback.domains.feed.model.FeedUpdateServiceRequest;
import com.programmers.bucketback.error.BusinessException;
import com.programmers.bucketback.error.ErrorCode;
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
	private final MemberUtils memberUtils;

	/** 피드 생성 */
	public Long createFeed(final FeedCreateServiceRequest request) {
		Long memberId = memberUtils.getCurrentMemberId();

		return feedAppender.append(memberId, request);
	}

	/** 피드 수정 */
	public void modifyFeed(
		final Long feedId,
		final FeedUpdateServiceRequest request
	) {
		Long memberId = memberUtils.getCurrentMemberId();
		feedModifier.modify(memberId, feedId, request);
	}

	public CursorSummary<FeedCursorSummaryLike> getFeedByCursor(
		final Hobby hobby,
		final String nickName,
		final boolean onlyNicknameLikeFeeds,
		final String sortCondition,
		final CursorPageParameters parameters
	) {
		FeedSortCondition feedSortCondition = FeedSortCondition.from(sortCondition);
		Long loginMemberId = memberUtils.getCurrentMemberId();

		if (onlyNicknameLikeFeeds && nickName == null) {
			throw new BusinessException(ErrorCode.FEED_BAD_LIKE_ONLY_REQUEST);
		}

		return feedCursorReader.getFeedByCursor(
			hobby,
			nickName,
			onlyNicknameLikeFeeds,
			loginMemberId,
			feedSortCondition,
			parameters
		);
	}

	/** 피드 삭제 */
	public void deleteFeed(final Long feedId) {
		Long memberId = memberUtils.getCurrentMemberId();
		feedRemover.remove(memberId, feedId);
	}

	/** 피드 좋아요 */
	public void likeFeed(final Long feedId) {
		Long memberId = memberUtils.getCurrentMemberId();
		feedAppender.like(memberId, feedId);
	}

	/** 피드 좋아요 취소 */
	public void unLikeFeed(final Long feedId) {
		Long memberId = memberUtils.getCurrentMemberId();
		feedRemover.unlike(memberId, feedId);
	}

	/** 피드 상세 조회 **/
	public FeedGetServiceResponse getFeed(final Long feedId) {
		final Long memberId = memberUtils.getCurrentMemberId();
		final FeedDetail detail = feedReader.readDetail(feedId, memberId);

		return FeedGetServiceResponse.from(detail);
	}
}
