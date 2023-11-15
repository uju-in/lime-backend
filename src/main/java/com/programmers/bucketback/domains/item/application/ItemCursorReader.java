package com.programmers.bucketback.domains.item.application;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.bucketback.domains.common.vo.CursorPageParameters;
import com.programmers.bucketback.domains.item.application.dto.ItemGetByCursorServiceResponse;
import com.programmers.bucketback.domains.item.application.vo.ItemCursorSummary;
import com.programmers.bucketback.domains.item.repository.ItemRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemCursorReader {

	private static final int DEFAULT_SIZE = 20;
	private final ItemRepository itemRepository;

	public ItemGetByCursorServiceResponse readByCursor(
		final String keyword,
		final CursorPageParameters parameters
	) {

		int pageSize = getPageSize(parameters);

		String trimmedKeyword = keyword.trim();

		List<ItemCursorSummary> itemCursorSummaries = itemRepository.findAllByCursor(
			trimmedKeyword,
			parameters.cursorId(),
			pageSize
		);

		String nextCursorId = getNextCursorId(itemCursorSummaries);

		return new ItemGetByCursorServiceResponse(
			nextCursorId,
			itemCursorSummaries
		);
	}

	private int getPageSize(final CursorPageParameters parameters) {
		Integer parameterSize = parameters.size();

		if (parameterSize == 0) {
			return DEFAULT_SIZE;
		}

		return parameterSize;
	}

	private String getNextCursorId(final List<ItemCursorSummary> itemCursorSummaries) {
		int size = itemCursorSummaries.size();
		if (size == 0) {
			return null;
		}

		ItemCursorSummary lastElement = itemCursorSummaries.get(size - 1);

		return lastElement.cursorId();
	}
}
