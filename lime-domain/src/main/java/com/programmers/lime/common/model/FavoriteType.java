package com.programmers.lime.common.model;

import lombok.Getter;

@Getter
public enum FavoriteType {

	ITEM("item"),
	FOLDER("folder");

	private final String type;

	FavoriteType(String type) {
		this.type = type;
	}
}
