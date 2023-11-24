package com.programmers.bucketback.common.model.builder;

import java.util.Arrays;

import com.programmers.bucketback.common.model.ItemIdRegistry;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemIdRegistryBuilder {

	public static ItemIdRegistry createItemIdRegistry() {
		return new ItemIdRegistry(Arrays.asList(1L, 2L, 3L));
	}

}
