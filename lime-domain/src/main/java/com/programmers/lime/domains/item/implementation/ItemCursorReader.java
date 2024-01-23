package com.programmers.lime.domains.item.implementation;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.lime.common.cursor.CursorPageParameters;
import com.programmers.lime.common.cursor.CursorSummary;
import com.programmers.lime.common.cursor.CursorUtils;
import com.programmers.lime.domains.item.model.ItemCursorSummary;
import com.programmers.lime.domains.item.model.ItemSortCondition;
import com.programmers.lime.domains.item.repository.ItemRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemCursorReader {

	private static final int DEFAULT_SIZE = 20;
	private final ItemRepository itemRepository;

	public CursorSummary<ItemCursorSummary> readByCursor(
		final String keyword,
		final CursorPageParameters parameters,
		final ItemSortCondition itemSortCondition
	) {
		String trimmedKeyword = keyword.trim();

		if (trimmedKeyword.isEmpty()) {
			return new CursorSummary<>(
				null,
				0,
				Collections.emptyList()
			);
		}

		int pageSize = getPageSize(parameters);

		List<ItemCursorSummary> itemCursorSummaries = itemRepository.findAllByCursor(
			trimmedKeyword,
			parameters.cursorId(),
			pageSize,
			itemSortCondition
		);

		return CursorUtils.getCursorSummaries(itemCursorSummaries);
	}

	private int getPageSize(final CursorPageParameters parameters) {
		Integer parameterSize = parameters.size();

		if (parameterSize == null) {
			return DEFAULT_SIZE;
		}

		return parameterSize;
	}
}
