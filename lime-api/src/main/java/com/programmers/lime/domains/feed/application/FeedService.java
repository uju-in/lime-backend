package com.programmers.lime.domains.feed.application;

import java.util.List;

import org.springframework.stereotype.Service;

import com.programmers.lime.common.cursor.CursorPageParameters;
import com.programmers.lime.common.cursor.CursorSummary;
import com.programmers.lime.common.model.Hobby;
import com.programmers.lime.domains.feed.application.dto.response.FeedGetRankingServiceResponse;
import com.programmers.lime.domains.feed.application.dto.response.FeedGetServiceResponse;
import com.programmers.lime.domains.feed.implementation.FeedAppender;
import com.programmers.lime.domains.feed.implementation.FeedCursorReader;
import com.programmers.lime.domains.feed.implementation.FeedModifier;
import com.programmers.lime.domains.feed.implementation.FeedReader;
import com.programmers.lime.domains.feed.implementation.FeedRemover;
import com.programmers.lime.domains.feed.model.FeedCreateServiceRequest;
import com.programmers.lime.domains.feed.model.FeedCursorSummaryLike;
import com.programmers.lime.domains.feed.model.FeedDetail;
import com.programmers.lime.domains.feed.model.FeedInfo;
import com.programmers.lime.domains.feed.model.FeedSortCondition;
import com.programmers.lime.domains.feed.model.FeedUpdateServiceRequest;
import com.programmers.lime.error.BusinessException;
import com.programmers.lime.error.ErrorCode;
import com.programmers.lime.global.event.ranking.feed.FeedRankingUpdateEvent;
import com.programmers.lime.global.event.ranking.feed.FeedRankingUpdateEventListener;
import com.programmers.lime.global.util.MemberUtils;
import com.programmers.lime.redis.feed.FeedRedisManager;
import com.programmers.lime.redis.feed.model.FeedRankingInfo;

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
	private final FeedRedisManager feedRedisManager;
	private final FeedRankingUpdateEventListener feedRankingUpdateEventListener;

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
		changePopularity(feedId, 1);
	}

	/** 피드 좋아요 취소 */
	public void unLikeFeed(final Long feedId) {
		Long memberId = memberUtils.getCurrentMemberId();
		feedRemover.unlike(memberId, feedId);
		changePopularity(feedId, -1);
	}

	/** 피드 상세 조회 **/
	public FeedGetServiceResponse getFeed(final Long feedId) {
		final Long memberId = memberUtils.getCurrentMemberId();
		final FeedDetail detail = feedReader.readDetail(feedId, memberId);
		changePopularity(detail, 1);

		return FeedGetServiceResponse.from(detail);
	}

	private void changePopularity(
		final Long feedId,
		final int value
	) {
		Long memberId = memberUtils.getCurrentMemberId();
		changePopularity(feedReader.readDetail(feedId, memberId), value);
	}

	private void changePopularity(
		final FeedDetail feedDetail,
		final int value
	) {
		FeedInfo feedInfo = feedDetail.feedInfo();

		FeedRankingInfo feedRankingInfo = FeedRankingInfo.builder()
			.feedId(feedInfo.id())
			.feedContent(feedInfo.content())
			.hobbyName(feedInfo.hobby())
			.likeCount(feedInfo.likeCount())
			.build();

		FeedRankingUpdateEvent event = new FeedRankingUpdateEvent(feedRankingInfo, value);
		feedRankingUpdateEventListener.updateFeedRanking(event);
	}

	public FeedGetRankingServiceResponse getFeedRanking() {
		List<FeedRankingInfo> feedRankingInfos = feedRedisManager.getFeedRanking();

		return new FeedGetRankingServiceResponse(feedRankingInfos);
	}
}
