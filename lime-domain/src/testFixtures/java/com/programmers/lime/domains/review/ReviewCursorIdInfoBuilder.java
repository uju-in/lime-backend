package com.programmers.lime.domains.review;

import java.util.List;

import com.programmers.lime.domains.review.model.ReviewCursorIdInfo;

public class ReviewCursorIdInfoBuilder {

	public static ReviewCursorIdInfo build(final Long id) {
		return new ReviewCursorIdInfo(
			id,
			"202301010000000000000" + id
		);
	}

	public static List<ReviewCursorIdInfo> buildMany() {
		return List.of(
			build(1L),
			build(2L),
			build(3L)
		);
	}
}
