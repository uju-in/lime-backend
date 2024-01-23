package com.programmers.lime.domains.item.implementation;

import org.springframework.stereotype.Component;

import com.programmers.lime.domains.item.domain.MemberItemFolder;
import com.programmers.lime.domains.item.repository.MemberItemFolderRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberItemFolderAppender {

	private final MemberItemFolderRepository memberItemFolderRepository;

	public Long append(
		final String folderName,
		final Long memberId
	) {
		MemberItemFolder memberItemFolder = new MemberItemFolder(folderName, memberId);
		MemberItemFolder savedMemberItemFolder = memberItemFolderRepository.save(memberItemFolder);

		return savedMemberItemFolder.getId();
	}
}
