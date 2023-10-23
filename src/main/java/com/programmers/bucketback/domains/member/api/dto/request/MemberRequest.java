package com.programmers.bucketback.domains.member.api.dto.request;

public record MemberRequest(
	String email,
	String password
) {
}
