package com.programmers.bucketback.domains.item.implementation;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.programmers.bucketback.common.cursor.CursorPageParameters;
import com.programmers.bucketback.common.cursor.CursorPageParametersBuilder;
import com.programmers.bucketback.common.cursor.CursorSummary;
import com.programmers.bucketback.common.cursor.CursorUtils;
import com.programmers.bucketback.common.model.Hobby;
import com.programmers.bucketback.domains.item.model.MemberItemSummary;
import com.programmers.bucketback.domains.item.model.MemberItemSummaryBuilder;
import com.programmers.bucketback.domains.item.repository.MemberItemRepository;

@ExtendWith(MockitoExtension.class)
class MemberItemReaderTest {

	@Mock
	private MemberItemRepository memberItemRepository;

	@InjectMocks
	private MemberItemReader memberItemReader;

	@Nested
	@DisplayName("나의 아이템 목록 조회 테스트")
	class MemberItemReadTest {
		@Test
		@DisplayName("커서를 이용해 나의 아이템 목록 조회한다.")
		void readByCursor() {
			// given
			Hobby hobby = Hobby.BASEBALL;
			Long memberId = 1L;
			CursorPageParameters parameters = CursorPageParametersBuilder.build();
			List<MemberItemSummary> memberItemSummaries = MemberItemSummaryBuilder.buildMany();
			CursorSummary<MemberItemSummary> cursorSummaries = CursorUtils.getCursorSummaries(memberItemSummaries);

			given(memberItemRepository.findMemberItemsByCursor(
					hobby,
					memberId,
					parameters.cursorId(),
					parameters.size()
				)
			).willReturn(memberItemSummaries);

			// when
			CursorSummary<MemberItemSummary> actualCursorSummary = memberItemReader.readMemberItem(
				hobby,
				memberId,
				parameters
			);

			// then
			assertThat(actualCursorSummary)
				.usingRecursiveComparison()
				.isEqualTo(cursorSummaries);
		}

		@Test
		@DisplayName("CursorId와 size가 null일 때 기본값으로 조회한다.")
		void readByCursorWithNullCursorIdAndSize() {
			// given
			Hobby hobby = Hobby.BASEBALL;
			Long memberId = 1L;
			CursorPageParameters parameters = CursorPageParametersBuilder.buildWithNull();
			List<MemberItemSummary> memberItemSummaries = MemberItemSummaryBuilder.buildMany();
			CursorSummary<MemberItemSummary> cursorSummaries = CursorUtils.getCursorSummaries(memberItemSummaries);

			given(memberItemRepository.findMemberItemsByCursor(
					hobby,
					memberId,
					null,
					20
				)
			).willReturn(memberItemSummaries);

			// when
			CursorSummary<MemberItemSummary> actualCursorSummary = memberItemReader.readMemberItem(
				hobby,
				memberId,
				parameters
			);

			// then
			assertThat(actualCursorSummary)
				.usingRecursiveComparison()
				.isEqualTo(cursorSummaries);
		}
	}

}