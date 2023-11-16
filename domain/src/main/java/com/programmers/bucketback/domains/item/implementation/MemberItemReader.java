package com.programmers.bucketback.domains.item.implementation;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.bucketback.domains.bucket.model.BucketMemberItemSummary;
import com.programmers.bucketback.domains.item.domain.Item;
import com.programmers.bucketback.domains.item.domain.MemberItem;
import com.programmers.bucketback.domains.item.repository.MemberItemRepository;
import com.programmers.bucketback.error.exception.EntityNotFoundException;
import com.programmers.bucketback.error.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
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

	public List<BucketMemberItemSummary> readBucketMemberItem(
		final List<Long> itemIdsFromBucketItem,
		final List<Long> itemIdsFromMemberItem,
		final Long memberId,
		final String cursorId,
		final int pageSize
	) {
		return memberItemRepository.findBucketMemberItemsByCursor(
			itemIdsFromBucketItem,
			itemIdsFromMemberItem,
			memberId,
			cursorId,
			pageSize
		);
	}
}
