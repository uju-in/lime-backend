package com.programmers.lime.domains.inventory.implementation;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.programmers.lime.common.model.Hobby;
import com.programmers.lime.common.model.ItemIdRegistry;
import com.programmers.lime.common.model.ItemIdRegistryBuilder;
import com.programmers.lime.domains.inventory.domain.Inventory;
import com.programmers.lime.domains.inventory.domain.InventoryBuilder;
import com.programmers.lime.domains.inventory.domain.InventoryItem;
import com.programmers.lime.domains.inventory.repository.InventoryRepository;
import com.programmers.lime.domains.item.domain.ItemBuilder;
import com.programmers.lime.domains.item.implementation.ItemReader;

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

		Inventory inventory = InventoryBuilder.build();

		ItemIdRegistry itemIdRegistry = ItemIdRegistryBuilder.build();
		List<InventoryItem> inventoryItems = InventoryBuilder.buildInventoryItems(itemIdRegistry);

		given(inventoryRepository.save(any(Inventory.class)))
			.willReturn(inventory);
		given(itemReader.read(anyLong()))
			.willReturn(ItemBuilder.build());

		//when
		Long actualInventoryId = inventoryAppender.append(memberId, hobby, itemIdRegistry);

		//then
		assertThat(actualInventoryId).isEqualTo(inventory.getId());
		assertThat(inventory.getInventoryItems())
			.usingRecursiveComparison()
			.ignoringFields("inventory")
			.isEqualTo(inventoryItems);
	}
}
