package com.programmers.bucketback.domains.feed.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.bucketback.domains.feed.application.vo.FeedCreateContent;
import com.programmers.bucketback.domains.feed.application.vo.FeedUpdateContent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FeedService {

	private final FeedAppender feedAppender;
	private final FeedModifier feedModifier;
	private final FeedRemover feedRemover;

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
}
