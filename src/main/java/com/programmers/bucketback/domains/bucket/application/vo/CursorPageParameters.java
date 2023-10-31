package com.programmers.bucketback.domains.bucket.application.vo;

public record CursorPageParameters(
	String cursorId,

	int size
) {
}
