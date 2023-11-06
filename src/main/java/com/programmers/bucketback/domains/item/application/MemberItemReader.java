package com.programmers.bucketback.domains.item.application;

import java.util.List;

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
		final Long memberItemId
	) {
		Item item = itemReader.read(itemId);
		return memberItemRepository.findMemberItemByMemberIdAndItem(memberItemId, item)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_ITEM_NOT_FOUND));
	}

	public List<MemberItem> readByMemberId(final Long memberId) {
		return memberItemRepository.findByMemberId(memberId);
	}
}
