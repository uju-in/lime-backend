package com.programmers.lime.common.model;

import lombok.Getter;

@Getter
public enum ObjectType {

	ITEM("item"),
	FOLDER("folder");

	private final String type;

	ObjectType(String type) {
		this.type = type;
	}
}
