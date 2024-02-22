package com.programmers.lime.domains.item.implementation;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.lime.domains.item.domain.MemberItemFolder;
import com.programmers.lime.domains.item.repository.MemberItemFolderRepository;
import com.programmers.lime.error.BusinessException;
import com.programmers.lime.error.EntityNotFoundException;
import com.programmers.lime.error.ErrorCode;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberItemFolderReader {

	private final MemberItemFolderRepository memberItemFolderRepository;

	public final MemberItemFolderAppender memberItemFolderAppender;

	public MemberItemFolder read(
		final Long folderId
	) {
		return memberItemFolderRepository.findById(folderId)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_ITEM_FOLDER_NOT_FOUND));
	}

	public List<MemberItemFolder> readMemberItemFoldersByMemberId(
		final Long memberId
	) {
		return memberItemFolderRepository.findMemberItemFoldersByMemberId(memberId);
	}

	public List<Long> getIdByMemberIdAndName(
		final Long memberId,
		final String folderName
	) {
		return memberItemFolderRepository.getIdByMemberIdAndName(memberId, folderName);
	}

	public Long getDefaultFolderId(final Long memberId, final String defaultFolderName) {
		List<Long> folderIds = getIdByMemberIdAndName(memberId, defaultFolderName);

		if(folderIds.isEmpty()) {
			return memberItemFolderAppender.append(defaultFolderName, memberId);
		}

		if(folderIds.size() > 1) {
			throw new BusinessException(ErrorCode.FAVORITE_DEFAULT_FOLDER_DUPLICATED);
		}

		return folderIds.get(0);
	}
}
