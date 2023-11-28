package com.programmers.bucketback.domains.feed.implementation;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.programmers.bucketback.common.cursor.CursorPageParameters;
import com.programmers.bucketback.common.cursor.CursorPageParametersBuilder;
import com.programmers.bucketback.common.cursor.CursorSummary;
import com.programmers.bucketback.common.cursor.CursorUtils;
import com.programmers.bucketback.common.model.Hobby;
import com.programmers.bucketback.domains.feed.FeedBuilder;
import com.programmers.bucketback.domains.feed.domain.Feed;
import com.programmers.bucketback.domains.feed.model.FeedCursorSummary;
import com.programmers.bucketback.domains.feed.model.FeedCursorSummaryBuilder;
import com.programmers.bucketback.domains.feed.model.FeedCursorSummaryLike;
import com.programmers.bucketback.domains.feed.model.FeedCursorSummaryLikeBuilder;
import com.programmers.bucketback.domains.feed.model.FeedSortCondition;
import com.programmers.bucketback.domains.feed.repository.FeedLikeRepository;
import com.programmers.bucketback.domains.feed.repository.FeedRepository;
import com.programmers.bucketback.domains.member.domain.Member;
import com.programmers.bucketback.domains.member.domain.MemberBuilder;
import com.programmers.bucketback.domains.member.implementation.MemberReader;

@ExtendWith(MockitoExtension.class)
class FeedCursorReaderTest {

	private static final int DEFAULT_PAGE_SIZE = 20;

	@InjectMocks
	private FeedCursorReader feedCursorReader;

	@Mock
	private FeedRepository feedRepository;

	@Mock
	private FeedLikeRepository feedLikeRepository;

	@Mock
	private MemberReader memberReader;

	@Mock
	private FeedReader feedReader;

	static Stream<Arguments> provideParameters() {
		return Stream.of(
			Arguments.of(CursorPageParametersBuilder.build()),
			Arguments.of(CursorPageParametersBuilder.buildWithNull())
		);
	}

	@ParameterizedTest
	@DisplayName("피드 목록 조회 테스트를 한다.")
	@MethodSource(value = "provideParameters")
	void readFeedListTest(final CursorPageParameters parameters) {
		// given
		List<FeedCursorSummary> feedCursorSummaries = FeedCursorSummaryBuilder.buildMany();
		List<FeedCursorSummaryLike> feedCursorSummaryLikes = FeedCursorSummaryLikeBuilder
			.buildMany(feedCursorSummaries,
				true
			);

		CursorSummary<FeedCursorSummaryLike> expectedSummaries = CursorUtils.getCursorSummaries(feedCursorSummaryLikes);

		Hobby hobby = Hobby.BASEBALL;
		String nickname = " nickname ";
		Long memberId = 1L;
		Long myPageMemberId = 1L;
		FeedSortCondition sortCondition = FeedSortCondition.RECENT;
		Member member = MemberBuilder.build(memberId);
		int pageSize = parameters.size() == null ? DEFAULT_PAGE_SIZE : parameters.size();

		given(memberReader.readByNickname(nickname.trim())).willReturn(member);

		given(feedRepository.findAllByCursor(
				eq(myPageMemberId),
				anyBoolean(),
				eq(hobby),
				eq(sortCondition),
				eq(parameters.cursorId()),
				eq(pageSize)
			)
		).willReturn(feedCursorSummaries);

		given(feedLikeRepository.existsByMemberIdAndFeed(
				eq(memberId),
				any(Feed.class)
			)
		).willReturn(true);

		given(feedReader.read(anyLong())).willReturn(FeedBuilder.build());

		// when
		CursorSummary<FeedCursorSummaryLike> actualSummaries = feedCursorReader.getFeedByCursor(
			hobby,
			nickname,
			true,
			memberId,
			sortCondition,
			parameters
		);

		// then
		assertThat(actualSummaries)
			.usingRecursiveComparison()
			.isEqualTo(expectedSummaries);

	}

}