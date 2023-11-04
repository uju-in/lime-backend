package com.programmers.bucketback.domains.inventory.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programmers.bucketback.domains.common.Hobby;
import com.programmers.bucketback.domains.inventory.domain.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
	boolean existsByHobbyAndMemberId(final Hobby hobby, final Long memberId);

	Optional<Inventory> findByIdAndMemberId(final Long inventoryId, final Long memberId);
}
