package com.programmers.bucketback.domains.item.implementation;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.programmers.bucketback.domains.item.domain.Item;
import com.programmers.bucketback.domains.item.domain.ItemBuilder;
import com.programmers.bucketback.domains.item.model.ItemInfo;
import com.programmers.bucketback.domains.item.repository.ItemRepository;

@ExtendWith(MockitoExtension.class)
class ItemFinderTest {

	@InjectMocks
	private ItemFinder itemFinder;

	@Mock
	private ItemRepository itemRepository;

	@Nested
	@DisplayName("키워드로 아이템을 조회 테스트")
	class ItemFindByKeywordTest {

		@Test
		@DisplayName("아이템 이름이 있는 키워드로 아이템을 조회한다.")
		void findByKeywordInItem() {
			// given
			String keyword = "농구";
			List<Item> items = ItemBuilder.buildMany();
			List<ItemInfo> expectedItemInfos = items.stream()
				.map(ItemInfo::from)
				.toList();

			given(itemRepository.findItemsByNameContains(keyword.trim()))
				.willReturn(items);

			// when
			List<ItemInfo> actualItemInfos = itemFinder.getItemNamesByKeyword(keyword);

			// then
			assertThat(actualItemInfos)
				.usingRecursiveComparison()
				.isEqualTo(expectedItemInfos);
		}
	}

}