package com.programmers.lime.domains.item.implementation;

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

import com.programmers.lime.common.cursor.CursorPageParameters;
import com.programmers.lime.common.cursor.CursorPageParametersBuilder;
import com.programmers.lime.common.cursor.CursorSummary;
import com.programmers.lime.common.cursor.CursorUtils;
import com.programmers.lime.common.model.Hobby;
import com.programmers.lime.domains.item.model.MemberItemSummary;
import com.programmers.lime.domains.item.model.MemberItemSummaryBuilder;
import com.programmers.lime.domains.item.repository.MemberItemRepository;

@ExtendWith(MockitoExtension.class)
class MemberItemReaderTest {

	private static final int DEFAULT_PAGE_SIZE = 20;
	@Mock
	private MemberItemRepository memberItemRepository;
	@InjectMocks
	private MemberItemReader memberItemReader;

	static Stream<Arguments> provideParameters() {
		return Stream.of(
			Arguments.of(CursorPageParametersBuilder.build()),
			Arguments.of(CursorPageParametersBuilder.buildWithNull())
		);
	}

	@ParameterizedTest
	@MethodSource("provideParameters")
	@DisplayName("커서를 이용해 나의 아이템 목록 조회한다.")
	void readByCursor(final CursorPageParameters parameters) {
		// given
		Hobby hobby = Hobby.BASEBALL;
		Long memberId = 1L;
		List<MemberItemSummary> memberItemSummaries = MemberItemSummaryBuilder.buildMany();
		CursorSummary<MemberItemSummary> cursorSummaries = CursorUtils.getCursorSummaries(memberItemSummaries);

		int pageSize = parameters.size() == null ? DEFAULT_PAGE_SIZE : parameters.size();
		given(memberItemRepository.findMemberItemsByCursor(
				hobby,
				1L,
				memberId,
				parameters.cursorId(),
				pageSize
			)
		).willReturn(memberItemSummaries);

		// when
		CursorSummary<MemberItemSummary> actualCursorSummary = memberItemReader.readMemberItem(
			hobby,
			1L,
			memberId,
			parameters
		);

		// then
		assertThat(actualCursorSummary)
			.usingRecursiveComparison()
			.isEqualTo(cursorSummaries);
	}

}
