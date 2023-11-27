package com.programmers.bucketback.domains.inventory.implementation;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.programmers.bucketback.common.model.Hobby;
import com.programmers.bucketback.common.model.ItemIdRegistry;
import com.programmers.bucketback.common.model.ItemIdRegistryBuilder;
import com.programmers.bucketback.domains.inventory.domain.Inventory;
import com.programmers.bucketback.domains.inventory.domain.InventoryBuilder;
import com.programmers.bucketback.domains.inventory.domain.InventoryItem;
import com.programmers.bucketback.domains.inventory.repository.InventoryRepository;
import com.programmers.bucketback.domains.item.domain.ItemBuilder;
import com.programmers.bucketback.domains.item.implementation.ItemReader;

@ExtendWith(MockitoExtension.class)
public class InventoryAppenderTest {

	@Mock
	private InventoryRepository inventoryRepository;

	@Mock
	private ItemReader itemReader;

	@InjectMocks
	private InventoryAppender inventoryAppender;

	@Test
	@DisplayName("인벤토리를 생성한다.")
	void createInventory() {
		//given
		Long memberId = 1L;
		Hobby hobby = Hobby.BASKETBALL;
		ItemIdRegistry itemIdRegistry = ItemIdRegistryBuilder.build();

		Inventory inventory = InventoryBuilder.build(itemIdRegistry);
		List<InventoryItem> inventoryItems = InventoryBuilder.buildInventoryItems(itemIdRegistry);

		given(inventoryRepository.save(any(Inventory.class)))
			.willReturn(inventory);
		given(itemReader.read(anyLong()))
			.willReturn(ItemBuilder.build());

		//when
		Long actualInventoryId = inventoryAppender.append(memberId, hobby, itemIdRegistry);

		//then
		assertThat(actualInventoryId).isEqualTo(inventory.getId());
		assertThat(inventory.getInventoryItems().size()).isEqualTo(inventoryItems.size());
	}
}
