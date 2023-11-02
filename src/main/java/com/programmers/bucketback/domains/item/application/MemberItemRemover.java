package com.programmers.bucketback.domains.item.application;

import org.springframework.stereotype.Component;

import com.programmers.bucketback.domains.item.domain.MemberItem;
import com.programmers.bucketback.domains.item.repository.MemberItemRepository;

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
}
