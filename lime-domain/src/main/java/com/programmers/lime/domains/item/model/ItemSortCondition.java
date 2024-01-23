package com.programmers.lime.domains.item.model;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.programmers.lime.error.BusinessException;
import com.programmers.lime.error.ErrorCode;

public enum ItemSortCondition {
	REVIEW_COUNT_DESC,
	REVIEW_RATING_DESC,
	NEWEST,
	PRICE_HIGH_TO_LOW,
	PRICE_LOW_TO_HIGH;

	private static final Map<String, ItemSortCondition> SORT_CONDITION_MAP;

	static {
		SORT_CONDITION_MAP = Collections.unmodifiableMap(
			Stream.of(ItemSortCondition.values())
				.collect(Collectors.toMap(ItemSortCondition::name, Function.identity()))
		);
	}

	public static ItemSortCondition from(String sortCondition) {
		if (sortCondition == null) {
			throw new BusinessException(ErrorCode.ITEM_BAD_SORT_CONDITION);
		}

		if (SORT_CONDITION_MAP.containsKey(sortCondition)) {
			return SORT_CONDITION_MAP.get(sortCondition);
		}

		throw new BusinessException(ErrorCode.ITEM_BAD_SORT_CONDITION);
	}
}
