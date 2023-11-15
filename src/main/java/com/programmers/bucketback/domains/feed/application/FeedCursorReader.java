package com.programmers.bucketback.domains.feed.application;

import java.util.List;

import org.springframework.stereotype.Component;

import com.programmers.bucketback.domains.common.Hobby;
import com.programmers.bucketback.domains.common.MemberUtils;
import com.programmers.bucketback.domains.common.vo.CursorPageParameters;
import com.programmers.bucketback.domains.feed.application.dto.response.FeedGetByCursorServiceResponse;
import com.programmers.bucketback.domains.feed.application.vo.FeedCursorSummary;
import com.programmers.bucketback.domains.feed.application.vo.FeedCursorSummaryLike;
import com.programmers.bucketback.domains.feed.repository.FeedLikeRepository;
import com.programmers.bucketback.domains.feed.repository.FeedRepository;
import com.programmers.bucketback.domains.member.application.MemberReader;
import com.programmers.bucketback.domains.member.domain.Member;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FeedCursorReader {

	private final FeedRepository feedRepository;
	private final MemberReader memberReader;
	private final FeedLikeRepository feedLikeRepository;
	private final FeedReader feedReader;

	public FeedGetByCursorServiceResponse getFeedByCursor(
		final String hobbyName,
		final String nickname,
		final FeedSortCondition sortCondition,
		final CursorPageParameters parameters
	) {
		int pageSize = getPageSizeByParameter(parameters);
		Long myPageMemberId = getMemberIdByNickname(nickname);
		Long loginMemberId = MemberUtils.getCurrentMemberId();
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

		String nextCursorId = getNextCursorId(feedCursorSummaryLikes);

		return new FeedGetByCursorServiceResponse(
			nextCursorId,
			feedCursorSummaryLikes
		);
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

	private String getNextCursorId(final List<FeedCursorSummaryLike> feedCursorSummaryLikes) {
		int feedCursorSummaryLikeSize = feedCursorSummaryLikes.size();

		if (feedCursorSummaryLikeSize == 0) {
			return null;
		}

		FeedCursorSummaryLike lastElement = feedCursorSummaryLikes.get(feedCursorSummaryLikeSize - 1);

		return lastElement.cursorId();
	}
}
