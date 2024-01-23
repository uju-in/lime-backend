package com.programmers.lime.domains.item.implementation;

import org.springframework.stereotype.Component;

import com.programmers.lime.domains.item.repository.MemberItemFolderRepository;
import com.programmers.lime.error.BusinessException;
import com.programmers.lime.error.ErrorCode;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberItemFolderValidator {

	private final MemberItemFolderRepository memberItemFolderRepository;

	public void validateExsitMemberItemFolder(
		final Long memberItemFolderId,
		final Long memberId
	) {
		if (memberItemFolderId == null) {
			return;
		}

		boolean isExsistMemberItemFolder = memberItemFolderRepository.
			existsMemberItemFolderByIdAndMemberId(
				memberItemFolderId,
				memberId
			);

		if (!isExsistMemberItemFolder) {
			throw new BusinessException(ErrorCode.MEMBER_ITEM_FOLDER_NOT_FOUND);
		}
	}
}
