package com.programmers.bucketback.domains.item.implementation;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.programmers.bucketback.common.model.Hobby;
import com.programmers.bucketback.domains.item.domain.Item;
import com.programmers.bucketback.domains.item.model.ItemCrawlerInfo;
import com.programmers.bucketback.domains.item.repository.ItemRepository;
import com.programmers.bucketback.item.builder.domain.ItemBuilder;
import com.programmers.bucketback.item.builder.model.ItemCrawlerInfoBuilder;

@ExtendWith(MockitoExtension.class)
class ItemAppenderTest {

	@Mock
	ItemRepository itemRepository;

	@Mock
	private ItemAppender itemAppender;

	@Test
	@DisplayName("아이템 추가 테스트")
	void appendTest() {
		// given
		Hobby hobby = Hobby.BASEBALL;
		ItemCrawlerInfo itemCrawlerInfo = ItemCrawlerInfoBuilder.successBuild();
		Item item = ItemBuilder.fromItemCrawlerInfoBuild(hobby, itemCrawlerInfo);
		Long expectedItemId = item.getId();

		// when
		when(itemRepository.save(any())).thenReturn(item);
		Long actualItemId = itemAppender.append(hobby, itemCrawlerInfo);

		// then
		assertThat(actualItemId).isEqualTo(expectedItemId);
	}
}
