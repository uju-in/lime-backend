package com.programmers.bucketback.common.model;

import java.util.Arrays;
import java.util.List;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemIdRegistryBuilder {

	public static ItemIdRegistry build() {
		return new ItemIdRegistry(Arrays.asList(1L, 2L, 3L));
	}

	public static ItemIdRegistry build(List<Long> ids) {
		return new ItemIdRegistry(ids);
	}

}
