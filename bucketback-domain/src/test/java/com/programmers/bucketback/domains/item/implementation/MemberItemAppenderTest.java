package com.programmers.bucketback.domains.item.implementation;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
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
import com.programmers.bucketback.domains.item.domain.MemberItem;
import com.programmers.bucketback.domains.item.repository.MemberItemRepository;
import com.programmers.bucketback.error.BusinessException;

@ExtendWith(MockitoExtension.class)
class MemberItemAppenderTest {

	@Mock
	private MemberItemRepository memberItemRepository;

	@Mock
	private ItemReader itemReader;

	@Mock
	private MemberItemValidator memberItemValidator;

	@InjectMocks
	private MemberItemAppender memberItemAppender;

	@Nested
	@DisplayName("나의 아이템 추가 테스트")
	class MemberItemAppendTest {

		@Test
		@DisplayName("나의 아이템을 추가한다.")
		void appendMyItem() {
			// given
			Long memberId = 1L;

			List<Item> items = ItemBuilder.buildMany();

			List<Long> itemIds = items.stream()
				.map(Item::getId)
				.toList();

			List<MemberItem> memberItems = items.stream()
				.map(item -> new MemberItem(memberId, item))
				.toList();

			doNothing().when(memberItemValidator)
				.validateExistMemberItem(anyLong(), anyList());

			given(itemReader.read(anyLong())).willAnswer(invocation -> {
				Long itemId = invocation.getArgument(0);
				return items.stream()
					.filter(item -> item.getId().equals(itemId))
					.findFirst()
					.orElseThrow();
			});

			given(memberItemRepository.saveAll(memberItems))
				.willReturn(memberItems);

			// when
			List<Long> actualItemIds = memberItemAppender.addMemberItems(
				itemIds,
				memberId
			);

			// then
			then(itemReader).should(times(itemIds.size())).read(anyLong());

			assertThat(actualItemIds).containsExactlyElementsOf(itemIds);

		}

		@Test
		@DisplayName("이미 존재하는 아이템을 추가하려고 하면 예외가 발생한다.")
		void appendExistItem() {
			// given
			Long memberId = 1L;

			List<Item> items = ItemBuilder.buildMany();

			List<Long> itemIds = items.stream()
				.map(Item::getId)
				.toList();

			given(itemReader.read(anyLong())).willAnswer(invocation -> {
				Long itemId = invocation.getArgument(0);
				return items.stream()
					.filter(item -> item.getId().equals(itemId))
					.findFirst()
					.orElseThrow();
			});

			doThrow(BusinessException.class).when(memberItemValidator)
				.validateExistMemberItem(memberId, items);

			// when && then
			assertThatThrownBy(() -> memberItemAppender.addMemberItems(
				itemIds,
				memberId
			)).isInstanceOf(BusinessException.class);

			then(itemReader).should(times(itemIds.size())).read(anyLong());
		}
	}
}
