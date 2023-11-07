package com.programmers.bucketback.domains.inventory.repository;

import java.util.List;

import com.programmers.bucketback.domains.inventory.api.dto.response.InventoryInfoSummary;

public interface InventoryRepositoryForSummary {
	List<InventoryInfoSummary> findInfoSummaries(final Long memberId);
}
