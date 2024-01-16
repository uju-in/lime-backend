package com.programmers.lime.domains.item.model;

import java.util.List;

public record MemberItemIdRegistry(
	List<Long> itemIds,
	Long folderId
) {
}
