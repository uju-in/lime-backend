package com.programmers.bucketback.domains.item.repository;

import java.util.List;

import com.programmers.bucketback.domains.item.application.vo.ItemSummary;

public interface ItemRepositoryForCursor {
	List<ItemSummary> findAllByCursor(
		String keyword,
		String cursorId,
		int pageSize
	);
}