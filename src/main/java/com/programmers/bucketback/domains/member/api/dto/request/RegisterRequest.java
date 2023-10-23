package com.programmers.bucketback.domains.member.api.dto.request;

public record RegisterRequest(
	String firstname,
	String lastname,
	String email,
	String password
) {
}
