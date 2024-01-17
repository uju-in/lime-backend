package com.programmers.lime.domains.friendships.model;

import com.programmers.lime.common.cursor.CursorIdParser;
import com.programmers.lime.domains.member.model.MemberInfo;

public record FollowerSummary(
	MemberInfo memberInfo,
	String cursorId
) implements CursorIdParser {
}
