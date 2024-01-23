package com.programmers.lime.domains.item.model;

import java.time.LocalDateTime;

import com.programmers.lime.common.model.ObjectType;

import lombok.Builder;

@Builder
public record MemberItemObjectInfo (
	Long objectId,
	String originalName,
	ObjectType type,
	LocalDateTime createdAt,
	LocalDateTime modifiedAt,
	MemberItemObjectMetadata metadata
) {
}
