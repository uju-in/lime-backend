package com.programmers.bucketback.domains.feed.implementation;

import java.util.List;

import org.springframework.stereotype.Component;

import com.programmers.bucketback.Hobby;
import com.programmers.bucketback.common.cursor.CursorPageParameters;
import com.programmers.bucketback.common.cursor.CursorSummary;
import com.programmers.bucketback.common.cursor.CursorUtils;
import com.programmers.bucketback.domains.feed.model.FeedCursorSummary;
import com.programmers.bucketback.domains.feed.model.FeedCursorSummaryLike;
import com.programmers.bucketback.domains.feed.model.FeedSortCondition;
import com.programmers.bucketback.domains.feed.repository.FeedLikeRepository;
import com.programmers.bucketback.domains.feed.repository.FeedRepository;
import com.programmers.bucketback.domains.member.domain.Member;
import com.programmers.bucketback.domains.member.implementation.MemberReader;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FeedCursorReader {

	private final FeedRepository feedRepository;
	private final MemberReader memberReader;
	private final FeedLikeRepository feedLikeRepository;
	private final FeedReader feedReader;

	public CursorSummary<FeedCursorSummaryLike> getFeedByCursor(
		final String hobbyName,
		final String nickname,
		final Long loginMemberId,
		final FeedSortCondition sortCondition,
		final CursorPageParameters parameters
	) {
		int pageSize = getPageSizeByParameter(parameters);
		Long myPageMemberId = getMemberIdByNickname(nickname);
		Hobby hobby = getHobbyByName(hobbyName);

		List<FeedCursorSummary> feedCursorSummaries = feedRepository.findAllByCursor(
			myPageMemberId,
			hobby,
			sortCondition,
			parameters.cursorId(),
			pageSize
		);

		List<FeedCursorSummaryLike> feedCursorSummaryLikes = feedCursorSummaries.stream().map(
			item -> {
				boolean isLike = feedLikeRepository.existsByMemberIdAndFeed(
					loginMemberId,
					feedReader.read(item.feedId()
					)
				);

				return item.of(isLike);
			}
		).toList();

		return CursorUtils.getCursorSummaries(feedCursorSummaryLikes);
	}

	private Hobby getHobbyByName(final String hobbyName) {
		if (hobbyName == null) {
			return null;
		}

		String trimHobbyName = hobbyName.trim();
		if (trimHobbyName.isEmpty()) {
			return null;
		}

		return Hobby.valueOf(trimHobbyName);
	}

	private int getPageSizeByParameter(final CursorPageParameters parameters) {
		Integer pageSize = parameters.size();
		if (pageSize == null) {
			return 20;
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
