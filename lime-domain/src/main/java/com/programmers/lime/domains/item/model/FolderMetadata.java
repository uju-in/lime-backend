package com.programmers.lime.domains.item.model;

import java.util.List;

import lombok.Builder;

@Builder
public record FolderMetadata(
	List<String> imageUrls,
	int itemCount
){
}
