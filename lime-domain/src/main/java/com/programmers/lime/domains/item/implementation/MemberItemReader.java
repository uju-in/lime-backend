package com.programmers.lime.domains.item.implementation;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.lime.common.cursor.CursorPageParameters;
import com.programmers.lime.common.cursor.CursorSummary;
import com.programmers.lime.common.cursor.CursorUtils;
import com.programmers.lime.common.model.Hobby;
import com.programmers.lime.domains.bucket.model.BucketMemberItemSummary;
import com.programmers.lime.domains.item.domain.Item;
import com.programmers.lime.domains.item.domain.MemberItem;
import com.programmers.lime.domains.item.model.MemberItemSummary;
import com.programmers.lime.domains.item.repository.MemberItemRepository;
import com.programmers.lime.error.EntityNotFoundException;
import com.programmers.lime.error.ErrorCode;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberItemReader {

	private static final int DEFAULT_PAGE_SIZE = 20;

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
		final Hobby hobby,
		final Long memberId,
		final String cursorId,
		final int pageSize
	) {
		return memberItemRepository.findBucketMemberItemsByCursor(
			itemIdsFromBucketItem,
			itemIdsFromMemberItem,
			hobby,
			memberId,
			cursorId,
			pageSize
		);
	}

	public CursorSummary<MemberItemSummary> readMemberItem(
		final Hobby hobby,
		final Long folderId,
		final Long memberId,
		final CursorPageParameters parameters
	) {
		int size = getPageSize(parameters);
		List<MemberItemSummary> memberItemsByCursor = memberItemRepository.findMemberItemsByCursor(
			hobby,
			folderId,
			memberId,
			parameters.cursorId(),
			size
		);

		return CursorUtils.getCursorSummaries(memberItemsByCursor);
	}

	private int getPageSize(final CursorPageParameters parameters) {
		Integer parameterSize = parameters.size();

		if (parameterSize == null) {
			return DEFAULT_PAGE_SIZE;
		}

		return parameterSize;
	}

	public int countByMemberIdAndHobby(
		final Long memberId,
		final Hobby hobby
	) {
		if (hobby == null) {
			return memberItemRepository.countByMemberId(memberId);
		}
		return memberItemRepository.countByHobbyAndMemberId(hobby, memberId);
	}
}
