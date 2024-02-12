package com.programmers.lime.domains.item.model;

import java.util.List;

import lombok.Builder;

@Builder
public record MemberItemFolderMetadata (
	List<String> imageUrls,
	int itemCount
){
}
