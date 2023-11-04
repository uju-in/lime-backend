package com.programmers.bucketback.domains.item.application;

import java.util.List;
import java.util.stream.IntStream;

import org.springframework.stereotype.Component;

import com.programmers.bucketback.domains.common.vo.CursorPageParameters;
import com.programmers.bucketback.domains.item.application.dto.GetItemByCursorServiceResponse;
import com.programmers.bucketback.domains.item.application.vo.ItemCursorSummary;
import com.programmers.bucketback.domains.item.application.vo.ItemSummary;
import com.programmers.bucketback.domains.item.repository.ItemRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ItemCursorReader {

	private final ItemRepository itemRepository;

	public GetItemByCursorServiceResponse readByCursor(
		final String keyword,
		final CursorPageParameters parameters
	) {
		int pageSize = parameters.size() == 0 ? 20 : parameters.size();

		List<ItemSummary> itemSummaries = itemRepository.findAllByCursor(
			keyword,
			parameters.cursorId(),
			pageSize
		);

		List<String> cursorIds = itemSummaries.stream()
			.map(this::generateCursorId)
			.toList();

		String nextCursorId = cursorIds.size() == 0 ? null : cursorIds.get(cursorIds.size() - 1);

		List<ItemCursorSummary> itemCursorSummaries = getItemCursorSummaries(itemSummaries, cursorIds);

		return new GetItemByCursorServiceResponse(
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

				return ItemCursorSummary.of(cursorId, itemSummary);
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
