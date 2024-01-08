package com.programmers.lime.domains.inventory.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programmers.lime.domains.inventory.domain.InventoryItem;

public interface InventoryItemRepository extends JpaRepository<InventoryItem,Long> {
	List<InventoryItem> findByInventoryId(final Long inventoryId);
}
