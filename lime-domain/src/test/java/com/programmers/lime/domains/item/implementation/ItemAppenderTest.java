package com.programmers.lime.domains.item.implementation;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.programmers.lime.common.model.Hobby;
import com.programmers.lime.domains.item.domain.Item;
import com.programmers.lime.domains.item.domain.ItemBuilder;
import com.programmers.lime.domains.item.model.ItemCrawlerInfo;
import com.programmers.lime.domains.item.model.ItemCrawlerInfoBuilder;
import com.programmers.lime.domains.item.repository.ItemRepository;

@ExtendWith(MockitoExtension.class)
class ItemAppenderTest {

	@Mock
	private ItemRepository itemRepository;

	@InjectMocks
	private ItemAppender itemAppender;

	@Test
	@DisplayName("아이템 추가 테스트에 저장한다.")
	void appendTest() {
		// given
		Hobby hobby = Hobby.BASEBALL;
		ItemCrawlerInfo itemCrawlerInfo = ItemCrawlerInfoBuilder.build();
		Item item = ItemBuilder.fromItemCrawlerInfoBuild(hobby, itemCrawlerInfo);

		given(itemRepository.save(any())).willReturn(item);
		Long expectedItemId = item.getId();

		// when
		Long actualItemId = itemAppender.append(hobby, itemCrawlerInfo);

		// then
		then(itemRepository).should(times(1)).save(any());
		assertThat(actualItemId).isEqualTo(expectedItemId);
	}
}
