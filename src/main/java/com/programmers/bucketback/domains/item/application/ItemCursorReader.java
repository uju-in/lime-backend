package com.programmers.bucketback.domains.item.application;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.stereotype.Component;

import com.programmers.bucketback.domains.common.vo.CursorPageParameters;
import com.programmers.bucketback.domains.item.application.dto.ItemGetByCursorServiceResponse;
import com.programmers.bucketback.domains.item.application.vo.ItemCursorSummary;
import com.programmers.bucketback.domains.item.application.vo.ItemSummary;
import com.programmers.bucketback.domains.item.repository.ItemRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
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

		List<ItemSummary> itemSummaries = itemRepository.findAllByCursor(
			trimmedKeyword,
			parameters.cursorId(),
			pageSize
		);

		List<String> cursorIds = itemSummaries.stream()
			.map(this::generateCursorId)
			.toList();

		String nextCursorId = cursorIds.size() == 0 ? null : cursorIds.get(cursorIds.size() - 1);

		List<ItemCursorSummary> itemCursorSummaries = getItemCursorSummaries(itemSummaries, cursorIds);

		return new ItemGetByCursorServiceResponse(
			nextCursorId,
			itemCursorSummaries
		);
	}

	private List<ItemCursorSummary> getItemCursorSummaries(
		final List<ItemSummary> itemSummaries,
		final List<String> cursorIds
	) {
		return IntStream.range(0, itemSummaries.size())
			.mapToObj(idx -> {
				String cursorId = cursorIds.get(idx);
				ItemSummary itemSummary = itemSummaries.get(idx);

				return new ItemCursorSummary(cursorId, itemSummary);
			}).toList();
	}

	private String generateCursorId(final ItemSummary itemSummary) {
		return itemSummary.createdAt().toString()
			.replace("T", "")
			.replace("-", "")
			.replace(":", "")
			.replace(".", "")
			+ String.format("%08d", itemSummary.id());
	}
}
