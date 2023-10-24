package com.programmers.bucketback.domains.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programmers.bucketback.domains.inventory.domain.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
}
