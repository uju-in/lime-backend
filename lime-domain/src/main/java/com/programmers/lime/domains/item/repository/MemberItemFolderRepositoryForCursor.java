package com.programmers.lime.domains.item.repository;

import java.util.List;

import com.programmers.lime.common.model.Hobby;
import com.programmers.lime.domains.item.model.MemberItemFolderCursorSummary;

public interface MemberItemFolderRepositoryForCursor {

	List<MemberItemFolderCursorSummary> findMemberItemFoldersByCursor(
		final Long memberId,
		final Hobby hobby,
		final String cursorId,
		final int pageSize
	);
}
