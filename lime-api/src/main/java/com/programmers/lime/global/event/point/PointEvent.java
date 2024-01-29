package com.programmers.lime.global.event.point;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PointEvent {

	private final Long memberId;
	private final int point;
}
