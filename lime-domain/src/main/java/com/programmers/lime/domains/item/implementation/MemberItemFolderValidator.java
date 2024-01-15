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
		final Long id,
		final Long memberId
	) {
		boolean isExsistMemberItemFolder = memberItemFolderRepository.
			existsMemberItemFolderByIdAndMemberId(
				id,
				memberId
			);

		if(!isExsistMemberItemFolder) {
			throw new BusinessException(ErrorCode.MEMBER_ITEM_FOLDER_NOT_FOUND);
		}
	}
}
