package com.programmers.bucketback.domains.inventory.repository;

import java.util.List;

import com.programmers.bucketback.domains.inventory.model.InventoryInfoSummary;

public interface InventoryRepositoryForSummary {
	List<InventoryInfoSummary> findInfoSummaries(final Long memberId);
}
