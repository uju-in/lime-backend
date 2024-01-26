package com.programmers.lime.global.event.point;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.programmers.lime.domains.member.domain.Member;
import com.programmers.lime.domains.member.implementation.MemberReader;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PointEventListener {

	private final MemberReader memberReader;

	@EventListener
	public void earnPoint(final PointEvent pointEvent) {
		final Member member = memberReader.read(pointEvent.getMemberId());
		member.earnPoint(pointEvent.getPoint());
	}
}
