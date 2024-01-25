package com.programmers.lime.domains.item.model;

import java.util.List;

import com.programmers.lime.common.cursor.CursorIdBuilder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemCursorIdInfoBuilder {

	public static List<ItemCursorIdInfo> buildMany() {
		return List.of(
			build(1L),
			build(2L),
			build(3L)
		);
	}

	public static ItemCursorIdInfo build(Long id) {
		return new ItemCursorIdInfo(id, CursorIdBuilder.build(id));
	}
}
