package com.programmers.bucketback.domains.inventory.implementation;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.util.Arrays;
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


@ExtendWith(MockitoExtension.class)
public class InventoryModifierTest {

	@Mock
	private InventoryAppender inventoryAppender;

	@Mock
	private InventoryRemover inventoryRemover;

	@Mock
	private InventoryReader inventoryReader;

	@InjectMocks
	private InventoryModifier inventoryModifier;

	@Test
	@DisplayName("인벤토리를 수정한다.")
	void modifyInventory() {
		//given
		Long memberId = 1L;
		Long bucketId = 1L;
		Hobby hobby = Hobby.SWIMMING;

		//기존 inventoryItem 제거
		Inventory existInventory = InventoryBuilder.build();
    
		given(inventoryReader.read(anyLong(), anyLong()))
			.willReturn(existInventory);
		doNothing().when(inventoryRemover)
			.removeInventoryItems(anyLong());

		//수정된 새로운 inventory 생성
		ItemIdRegistry updateItemRegistry = ItemIdRegistryBuilder.build(Arrays.asList(4L, 5L));
		List<InventoryItem> updateInventoryItems = InventoryBuilder.buildInventoryItems(updateItemRegistry);
		Inventory updateInventory = InventoryBuilder.build(hobby, updateItemRegistry);

		given(inventoryAppender.createInventoryItem(updateItemRegistry))
			.willReturn(updateInventoryItems);

		//when
		inventoryModifier.modify(memberId, bucketId, updateItemRegistry);

		//then
		assertThat(updateInventory.getInventoryItems().size()).usingRecursiveComparison().
			isEqualTo(updateInventoryItems.size());
		assertThat(updateInventory.getHobby()).isEqualTo(hobby);
	}
}
