package com.programmers.bucketback.domains.inventory.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programmers.bucketback.domains.inventory.domain.InventoryItem;

public interface InventoryItemRepository extends JpaRepository<InventoryItem,Long> {
	Optional<List<InventoryItem>> findByInventoryId(Long inventoryId);
}
