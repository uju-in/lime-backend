package com.programmers.bucketback.domains.inventory.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programmers.bucketback.common.model.Hobby;
import com.programmers.bucketback.domains.inventory.domain.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long>, InventoryRepositoryForSummary {
	boolean existsByHobbyAndMemberId(
		final Hobby hobby,
		final Long memberId
	);

	Optional<Inventory> findByIdAndMemberId(
		final Long inventoryId,
		final Long memberId
	);

	List<Inventory> findByMemberId(final Long memberId);
}
