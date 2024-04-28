package com.programmers.lime.domains.feed.model;

import com.programmers.lime.common.model.Hobby;
import com.programmers.lime.common.model.ItemIdRegistry;

public record FeedCreateServiceRequest(
	Hobby hobby,
	ItemIdRegistry itemIdRegistry,
	String content,
	Integer budget
) {
}
