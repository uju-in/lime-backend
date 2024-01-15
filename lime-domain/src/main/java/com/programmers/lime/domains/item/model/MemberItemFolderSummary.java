package com.programmers.lime.domains.item.model;

import lombok.Builder;

@Builder
public record MemberItemFolderSummary(
	Long id,

	String name

) {
}
