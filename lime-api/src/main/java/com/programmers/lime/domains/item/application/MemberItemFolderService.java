package com.programmers.lime.domains.item.application;

import org.springframework.stereotype.Component;

import com.programmers.lime.common.cursor.CursorPageParameters;
import com.programmers.lime.common.cursor.CursorSummary;
import com.programmers.lime.common.model.Hobby;
import com.programmers.lime.domains.item.application.dto.MemberItemFolderGetServiceResponse;
import com.programmers.lime.domains.item.implementation.MemberItemFolderAppender;
import com.programmers.lime.domains.item.implementation.MemberItemFolderModifier;
import com.programmers.lime.domains.item.implementation.MemberItemFolderReader;
import com.programmers.lime.domains.item.implementation.MemberItemFolderRemover;
import com.programmers.lime.domains.item.implementation.MemberItemFolderValidator;
import com.programmers.lime.domains.item.model.MemberItemFolderCursorSummary;
import com.programmers.lime.global.util.MemberUtils;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberItemFolderService {

	private final MemberItemFolderAppender memberItemFolderAppender;

	private final MemberItemFolderReader memberItemFolderReader;

	private final MemberItemFolderModifier memberItemFolderModifier;

	private final MemberItemFolderValidator memberItemFolderValidator;

	private final MemberItemFolderRemover memberItemFolderRemover;

	private final MemberUtils memberUtils;

	public MemberItemFolderGetServiceResponse getMemberItemFolderByCursor(
		final Hobby hobby,
		final CursorPageParameters parameters
	) {
		Long memberId = memberUtils.getCurrentMemberId();
		int totalMemberItemFolderCount = memberItemFolderReader.countByMemberIdAndHobby(memberId, hobby);

		CursorSummary<MemberItemFolderCursorSummary> cursorSummary = memberItemFolderReader.readMemberItemFolderByCursor(
			hobby,
			memberId,
			parameters
		);

		return new MemberItemFolderGetServiceResponse(cursorSummary, totalMemberItemFolderCount);
	}

	public void createMemberItemFolder(
		final String folderName,
		final Hobby hobby
	) {
		Long memberId = memberUtils.getCurrentMemberId();
		memberItemFolderAppender.append(folderName, memberId, hobby);
	}

	public void modifyMemberItemFolder(final Long folderId, final String folderName) {
		Long memberId = memberUtils.getCurrentMemberId();

		memberItemFolderValidator.validateExsitMemberItemFolder(folderId, memberId);

		memberItemFolderModifier.modify(folderId, folderName);
	}

	public void removeMemberItemFolder(final Long folderId) {
		Long memberId = memberUtils.getCurrentMemberId();

		memberItemFolderValidator.validateExsitMemberItemFolder(folderId, memberId);

		memberItemFolderRemover.remove(memberId, folderId);
	}
}
