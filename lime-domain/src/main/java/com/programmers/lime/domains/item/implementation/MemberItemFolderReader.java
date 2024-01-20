package com.programmers.lime.domains.item.implementation;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.lime.common.cursor.CursorPageParameters;
import com.programmers.lime.common.cursor.CursorSummary;
import com.programmers.lime.common.cursor.CursorUtils;
import com.programmers.lime.common.model.Hobby;
import com.programmers.lime.domains.item.model.MemberItemFolderCursorSummary;
import com.programmers.lime.domains.item.repository.MemberItemFolderRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberItemFolderReader {

	private final MemberItemFolderRepository memberItemFolderRepository;
	private static final int DEFAULT_PAGE_SIZE = 20;

	public int countByMemberIdAndHobby(
		final Long memberId,
		final Hobby hobby
	) {
		return memberItemFolderRepository.countByHobbyAndMemberId(hobby, memberId);
	}

	public CursorSummary<MemberItemFolderCursorSummary> readMemberItemFolderByCursor(
		final Hobby hobby,
		final Long memberId,
		final CursorPageParameters parameters
	) {
		int size = getPageSize(parameters);
		List<MemberItemFolderCursorSummary> memberItemFoldersByCursor =
			memberItemFolderRepository.findMemberItemFoldersByCursor(
				memberId,
				hobby,
				parameters.cursorId(),
				size
			);

		return CursorUtils.getCursorSummaries(memberItemFoldersByCursor);
	}

	private int getPageSize(final CursorPageParameters parameters) {
		Integer parameterSize = parameters.size();

		if (parameterSize == null) {
			return DEFAULT_PAGE_SIZE;
		}

		return parameterSize;
	}
}
