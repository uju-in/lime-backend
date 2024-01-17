package com.programmers.lime.domains.item.application;

import java.util.List;

import org.springframework.stereotype.Component;

import com.programmers.lime.common.model.Hobby;
import com.programmers.lime.domains.item.domain.Item;
import com.programmers.lime.domains.item.domain.MemberItemFolder;
import com.programmers.lime.domains.item.implementation.ItemReader;
import com.programmers.lime.domains.item.implementation.MemberItemFolderReader;
import com.programmers.lime.error.BusinessException;
import com.programmers.lime.error.ErrorCode;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberItemFolderValidator {

	private final MemberItemFolderReader memberItemFolderReader;

	private final ItemReader itemReader;

	public void validateItemHobbyEqualsFolderHobby(
		final List<Long> itemIds,
		final Long folderId
	) {
		if(itemIds.size() == 0) {
			throw new BusinessException(ErrorCode.MEMBER_ITEM_ID_LIST_IS_EMPTY);
		}

		for(Long itemId : itemIds) {
			Item item = itemReader.read(itemId);
			Hobby itemHobby = item.getHobby();

			MemberItemFolder memberItemFolder = memberItemFolderReader.read(folderId);
			Hobby folderHobby = memberItemFolder.getHobby();

			if (!itemHobby.equals(folderHobby)) {
				throw new BusinessException(ErrorCode.MEMBER_ITEM_HOBBY_NOT_EQUAL_TO_FOLDER_HOBBY);
			}
		}
	}
}
