package com.programmers.lime.domains.feed.implementation;

import java.util.List;

import org.springframework.stereotype.Component;

import com.programmers.lime.common.cursor.CursorPageParameters;
import com.programmers.lime.common.cursor.CursorSummary;
import com.programmers.lime.common.cursor.CursorUtils;
import com.programmers.lime.common.model.Hobby;
import com.programmers.lime.domains.feed.model.FeedCursorSummary;
import com.programmers.lime.domains.feed.model.FeedCursorSummaryLike;
import com.programmers.lime.domains.feed.model.FeedSortCondition;
import com.programmers.lime.domains.feed.repository.FeedLikeRepository;
import com.programmers.lime.domains.feed.repository.FeedRepository;
import com.programmers.lime.domains.member.domain.Member;
import com.programmers.lime.domains.member.implementation.MemberReader;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FeedCursorReader {

	private static final int DEFAULT_PAGE_SIZE = 20;

	private final FeedRepository feedRepository;
	private final MemberReader memberReader;
	private final FeedLikeRepository feedLikeRepository;
	private final FeedReader feedReader;

	public CursorSummary<FeedCursorSummaryLike> getFeedByCursor(
		final Hobby hobby,
		final String nickname,
		final Long loginMemberId,
		final FeedSortCondition sortCondition,
		final CursorPageParameters parameters
	) {
		int pageSize = getPageSizeByParameter(parameters);
		Long nicknameMemberId = getMemberIdByNickname(nickname);

		List<FeedCursorSummary> feedCursorSummaries = feedRepository.findAllByCursor(
			nicknameMemberId,
			hobby,
			sortCondition,
			parameters.cursorId(),
			pageSize
		);

		List<FeedCursorSummaryLike> feedCursorSummaryLikes = feedCursorSummaries.stream().map(
			feedCursorSummary -> {
				boolean isLike = feedLikeRepository.existsByMemberIdAndFeed(
					loginMemberId,
					feedReader.read(feedCursorSummary.feedId())
				);

				return feedCursorSummary.of(isLike);
			}
		).toList();

		return CursorUtils.getCursorSummaries(feedCursorSummaryLikes);
	}

	private int getPageSizeByParameter(final CursorPageParameters parameters) {
		Integer pageSize = parameters.size();
		if (pageSize == null) {
			return DEFAULT_PAGE_SIZE;
		}

		return pageSize;
	}

	private Long getMemberIdByNickname(final String nickname) {
		if (nickname == null) {
			return null;
		}

		String trimNickName = nickname.trim();
		if (trimNickName.isEmpty()) {
			return null;
		}

		Member foundMember = memberReader.readByNickname(trimNickName);

		return foundMember.getId();
	}
}
