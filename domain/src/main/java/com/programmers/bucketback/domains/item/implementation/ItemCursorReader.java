package com.programmers.bucketback.domains.item.implementation;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.bucketback.common.cursor.CursorPageParameters;
import com.programmers.bucketback.common.cursor.CursorSummary;
import com.programmers.bucketback.common.cursor.CursorUtils;
import com.programmers.bucketback.domains.item.model.ItemCursorSummary;
import com.programmers.bucketback.domains.item.repository.ItemRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemCursorReader {

	private static final int DEFAULT_SIZE = 20;
	private final ItemRepository itemRepository;

	public CursorSummary<ItemCursorSummary> readByCursor(
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

		return CursorUtils.getCursorSummaries(itemCursorSummaries);
	}

	private int getPageSize(final CursorPageParameters parameters) {
		Integer parameterSize = parameters.size();

		if (parameterSize == 0) {
			return DEFAULT_SIZE;
		}

		return parameterSize;
	}
}
