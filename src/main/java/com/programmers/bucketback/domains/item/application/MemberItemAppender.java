package com.programmers.bucketback.domains.item.application;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.bucketback.domains.item.domain.Item;
import com.programmers.bucketback.domains.item.domain.MemberItem;
import com.programmers.bucketback.domains.item.repository.MemberItemRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberItemAppender {

	private final MemberItemRepository memberItemRepository;

	private final ItemReader itemReader;

	private final MemberItemValidator memberItemValidator;

	/**
	 * @return
	 * 중복된 item id를 제거 후 데이터베이스에 저장된 item의 id의 리스트를 반환 합니다.
	 * 이 때 리스트를 새롭게 만들어 비효율적일 수 있지만 신뢰성을 높이기 위해 이렇게 하였습니다.
	 * 서버 최적화를 위해 이 부분은 입력받은 item 리스트를 그대로 반환할 수 있지만 앞의 내용을 고려하면 좋겠습니다.
	 */
	@Transactional
	public List<Long> addMemberItems(
		final List<Long> itemIds,
		final Long memberId
	) {
		List<Item> items = getItems(itemIds);
		List<MemberItem> memberItems = getMemberItems(memberId, items);
		List<MemberItem> savedMemberItems = memberItemRepository.saveAll(memberItems);

		return savedMemberItems.stream()
			.map(MemberItem::getItem)
			.map(Item::getId)
			.toList();
	}

	private List<MemberItem> getMemberItems(
		final Long memberId,
		final List<Item> items
	) {
		memberItemValidator.validateExistMemberItem(memberId, items);

		return items.stream()
			.map(
				item -> new MemberItem(memberId, item)
			).toList();
	}

	private List<Item> getItems(final List<Long> itemIds) {
		return itemIds.stream()
			.distinct()
			.map(itemReader::read)
			.toList();
	}
}
