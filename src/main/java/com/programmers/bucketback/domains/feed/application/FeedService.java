package com.programmers.bucketback.domains.feed.application;

import org.springframework.stereotype.Service;

import com.programmers.bucketback.domains.common.MemberUtils;
import com.programmers.bucketback.domains.common.vo.CursorPageParameters;
import com.programmers.bucketback.domains.feed.application.dto.response.FeedGetByCursorServiceResponse;
import com.programmers.bucketback.domains.feed.application.dto.response.GetFeedServiceResponse;
import com.programmers.bucketback.domains.feed.application.vo.FeedCreateContent;
import com.programmers.bucketback.domains.feed.application.vo.FeedUpdateContent;

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

	public FeedGetByCursorServiceResponse getFeedByCursor(
		final String hobbyName,
		final String nickName,
		final String sortCondition,
		final CursorPageParameters parameters
	) {
		FeedSortCondition feedSortCondition =
			sortCondition != null ?
				FeedSortCondition.valueOf(sortCondition) : null;

		return feedCursorReader.getFeedByCursor(
			hobbyName,
			nickName,
			feedSortCondition,
			parameters
		);
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
		Long memberId = null;
		if (MemberUtils.isLoggedIn()) {
			memberId = MemberUtils.getCurrentMemberId();
		}

		return feedReader.readFeed(feedId, memberId);
	}
}
