package com.programmers.lime.domains.inventory.repository;

import java.util.List;

import com.programmers.lime.domains.inventory.model.InventoryInfoSummary;

public interface InventoryRepositoryForSummary {
	List<InventoryInfoSummary> findInfoSummaries(final Long memberId);
}
