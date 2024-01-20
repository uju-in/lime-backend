package com.programmers.lime.domains.item.implementation;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.lime.domains.item.domain.MemberItemFolder;
import com.programmers.lime.domains.item.repository.MemberItemFolderRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberItemFolderRemover {

	private final MemberItemFolderRepository memberItemFolderRepository;

	private final MemberItemFolderReader memberItemFolderReader;

	private final MemberItemRemover memberItemRemover;

	@Transactional
	public void remove(
		final Long memberId,
		final Long folderId
	) {
		memberItemRemover.removeByMemberId(memberId);

		MemberItemFolder memberItemFolder = memberItemFolderReader.read(folderId);
		memberItemFolderRepository.delete(memberItemFolder);
	}
}
