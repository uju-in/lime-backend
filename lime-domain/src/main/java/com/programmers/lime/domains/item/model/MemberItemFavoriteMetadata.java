package com.programmers.lime.domains.item.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record MemberItemFavoriteMetadata(
	MemberItemMetadata memberItemMetadata,
	MemberItemFolderMetadata memberItemFolderMetadata
) {
}
