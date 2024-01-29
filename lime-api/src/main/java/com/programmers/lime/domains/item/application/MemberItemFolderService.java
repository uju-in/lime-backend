package com.programmers.lime.domains.item.application;

import java.util.List;

import org.springframework.stereotype.Component;

import com.programmers.lime.domains.item.implementation.MemberItemFolderAppender;
import com.programmers.lime.domains.item.implementation.MemberItemFolderModifier;
import com.programmers.lime.domains.item.implementation.MemberItemFolderRemover;
import com.programmers.lime.domains.item.implementation.MemberItemFolderValidator;
import com.programmers.lime.global.util.MemberUtils;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberItemFolderService {

	private final MemberItemFolderAppender memberItemFolderAppender;

	private final MemberItemFolderModifier memberItemFolderModifier;

	private final MemberItemFolderValidator memberItemFolderValidator;

	private final MemberItemFolderRemover memberItemFolderRemover;

	private final MemberUtils memberUtils;

	public void createMemberItemFolder(
		final String folderName
	) {
		Long memberId = memberUtils.getCurrentMemberId();
		memberItemFolderAppender.append(folderName, memberId);
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

	public void removeMemberItemFolders(final List<Long> folderIds) {
		if (folderIds == null || folderIds.isEmpty()) {
			return;
		}

		for(Long folderId : folderIds) {
			removeMemberItemFolder(folderId);
		}
	}
}
