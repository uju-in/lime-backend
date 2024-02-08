package com.programmers.lime.domains.item.implementation;

import java.util.List;

import org.springframework.stereotype.Component;

import com.programmers.lime.domains.item.domain.MemberItem;
import com.programmers.lime.domains.item.repository.MemberItemRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberItemRemover {

	private final MemberItemReader memberItemReader;

	private final MemberItemRepository memberItemRepository;

	public void remove(final Long memberItemId) {
		MemberItem memberItem = memberItemReader.read(memberItemId);
		memberItemRepository.delete(memberItem);
	}

	public void removeByFolderId(final Long folderId) {
		List<MemberItem> memberItems = memberItemReader.readByFolderId(folderId);
		memberItemRepository.deleteAll(memberItems);
	}
}
