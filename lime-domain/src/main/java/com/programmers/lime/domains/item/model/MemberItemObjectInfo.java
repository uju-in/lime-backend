package com.programmers.lime.domains.item.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

import com.programmers.lime.common.model.ObjectType;

import lombok.Builder;

@Builder
public record MemberItemObjectInfo (
	Long objectId,
	String originalName,
	ObjectType type,
	LocalDateTime createdAt,
	LocalDateTime modifiedAt,
	Map<String, Serializable> metadata
) {
}
