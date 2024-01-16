package com.programmers.lime.domains.item.implementation;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.programmers.lime.domains.item.domain.Item;
import com.programmers.lime.domains.item.domain.ItemBuilder;
import com.programmers.lime.domains.item.domain.MemberItem;
import com.programmers.lime.domains.item.repository.MemberItemRepository;
import com.programmers.lime.error.BusinessException;

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
				.map(item -> new MemberItem(memberId, item, 1L))
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

			given(memberItemRepository.saveAll(anyList()))
				.willReturn(memberItems);

			// when
			List<Long> actualItemIds = memberItemAppender.addMemberItems(
				itemIds,
				1L,
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
				1L,
				memberId
			)).isInstanceOf(BusinessException.class);

			then(itemReader).should(times(itemIds.size())).read(anyLong());
		}

		@Test
		@DisplayName("입력으로 중복된 아이템 아이디가 들어오면 중복된 아이템 아이디를 제거하고 저장한다.")
		void appendDuplicateItem() {
			// given
			Long memberId = 1L;

			List<Item> items = ItemBuilder.buildMany();

			List<Long> originalItemIds = items.stream()
				.map(Item::getId)
				.toList();

			// 중복되는 아이템 아이디를 생성
			List<Long> duplicateItemIds = new ArrayList<>(originalItemIds);
			duplicateItemIds.add(originalItemIds.get(0));
			duplicateItemIds.add(originalItemIds.get(1));

			List<MemberItem> memberItems = items.stream()
				.map(item -> new MemberItem(memberId, item, 1L))
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

			given(memberItemRepository.saveAll(anyList()))
				.willReturn(memberItems);

			// when
			List<Long> actualItemIds = memberItemAppender.addMemberItems(
				duplicateItemIds,
				1L,
				memberId
			);

			// then
			then(itemReader).should(times(originalItemIds.size())).read(anyLong());

			assertThat(actualItemIds).containsExactlyElementsOf(originalItemIds);
		}
	}
}
