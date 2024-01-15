package com.programmers.lime.domains.item.implementation;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.lime.domains.item.domain.MemberItemFolder;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberItemFolderModifier {

	private final MemberItemFolderReader memberItemFolderReader;

	@Transactional
	public void modify(
		final Long folderId,
		final String folderName
	) {
		MemberItemFolder memberItemFolder = memberItemFolderReader.read(folderId);
		memberItemFolder.modifyFolderName(folderName);
	}
}
