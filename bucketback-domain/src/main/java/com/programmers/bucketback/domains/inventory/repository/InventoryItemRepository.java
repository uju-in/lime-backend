package com.programmers.bucketback.domains.inventory.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programmers.bucketback.domains.inventory.domain.InventoryItem;

public interface InventoryItemRepository extends JpaRepository<InventoryItem,Long> {
	List<InventoryItem> findByInventoryId(final Long inventoryId);
}
