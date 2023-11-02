package com.programmers.bucketback.domains.item.application;

import org.springframework.stereotype.Component;

import com.programmers.bucketback.domains.item.domain.Item;
import com.programmers.bucketback.domains.item.domain.MemberItem;
import com.programmers.bucketback.domains.item.repository.MemberItemRepository;
import com.programmers.bucketback.global.error.exception.EntityNotFoundException;
import com.programmers.bucketback.global.error.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberItemReader {

	private final MemberItemRepository memberItemRepository;
	private final ItemReader itemReader;

	public MemberItem read(final Long memberItemId) {
		return memberItemRepository.findById(memberItemId)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_ITEM_NOT_FOUND));
	}

	public MemberItem read(
		final Long itemId,
		final Long memberIteId
	) {
		Item item = itemReader.read(itemId);
		return memberItemRepository.findMemberItemByMembersIdAndItem(memberIteId, item)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_ITEM_NOT_FOUND));
	}
}
