package com.programmers.bucketback.domains.item.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.bucketback.domains.item.domain.Item;
import com.programmers.bucketback.domains.item.domain.MemberItem;
import com.programmers.bucketback.domains.item.repository.MemberItemRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddMemberItemService {

	private final MemberItemRepository memberItemRepository;

	private final ItemReader itemReader;

	@Transactional
	public void addMemberItems(
		final List<Long> itemIds,
		final Long memberId
	) {
		List<Item> items = getItems(itemIds);
		List<MemberItem> memberItems = getMemberItems(memberId, items);
		memberItemRepository.saveAll(memberItems);
	}

	private List<MemberItem> getMemberItems(
		final Long memberId,
		final List<Item> items
	) {
		return items.stream()
			.map(item ->
				new MemberItem(
					memberId, item
				)
			).toList();
	}

	private List<Item> getItems(final List<Long> itemIds) {
		return itemIds.stream()
			.distinct()
			.map(itemReader::read)
			.toList();
	}
}
