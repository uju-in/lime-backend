package com.programmers.bucketback.domains.item.application;

import java.util.Collections;
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

	private final ItemRepository itemRepository;

	public ItemGetByCursorServiceResponse readByCursor(
		final String keyword,
		final CursorPageParameters parameters
	) {
		final String trimmedKeyword = keyword.trim();

		if (trimmedKeyword.isEmpty()) {
			return new ItemGetByCursorServiceResponse(null, Collections.emptyList());
		}

		int pageSize = parameters.size() == 0 ? 20 : parameters.size();

		List<ItemCursorSummary> itemCursorSummaries = itemRepository.findAllByCursor(
			trimmedKeyword,
			parameters.cursorId(),
			pageSize
		);

		String nextCursorId =
			itemCursorSummaries.size() == 0 ? null : itemCursorSummaries.get(itemCursorSummaries.size() - 1).cursorId();
		
		return new ItemGetByCursorServiceResponse(
			nextCursorId,
			itemCursorSummaries
		);
	}
}
