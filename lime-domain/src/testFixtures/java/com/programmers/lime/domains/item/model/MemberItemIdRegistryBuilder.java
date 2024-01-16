package com.programmers.lime.domains.item.model;

import java.util.Arrays;
import java.util.List;

public class MemberItemIdRegistryBuilder {

	public static MemberItemIdRegistry build() {
		return new MemberItemIdRegistry(Arrays.asList(1L, 2L, 3L), 1L);
	}

	public static MemberItemIdRegistry build(List<Long> ids, Long folderId) {
		return new MemberItemIdRegistry(ids, folderId);
	}
}
