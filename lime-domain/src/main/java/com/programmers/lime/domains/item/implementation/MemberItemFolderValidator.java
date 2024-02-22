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
	private static final String DEFAULT_FOLDER_NAME = "default";

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

	public void validateFolderName(final String folderName) {

		if (folderName == null || folderName.isEmpty()) {
			throw new BusinessException(ErrorCode.MEMBER_ITEM_FOLDER_NAME_IS_EMPTY);
		}

		if (folderName.equals(DEFAULT_FOLDER_NAME)) {
			throw new BusinessException(ErrorCode.MEMBER_ITEM_FOLDER_NAME_IS_DEFAULT);
		}
	}
}
