package com.programmers.bucketback.global.error.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

	// Common
	INTERNAL_SERVER_ERROR("COMMON_001", "Internal Server Error"),
	INVALID_REQUEST("COMMON_002", "유효하지 않은 요청입니다."),
	SECURITY_CONTEXT_NOT_FOUND("COMMON_003", "Security context를 찾을 수 없습니다."),

	// Member
	MEMBER_LOGIN_FAIL("MEMBER_001", "로그인 정보가 잘못 되었습니다."),
	MEMBER_NOT_FOUND("MEMBER_002", "회원을 찾을 수 없습니다."),
	MEMBER_ANONYMOUS("MEMBER_003", "익명의 사용자 입니다."),
	MEMBER_DELETED("MEMBER_003", "탈퇴한 회원 입니다."),

	// Item
	ITEM_MARKET_NOT_FOUND("ITEM_001", "요청 url에 해당하는 상품을 찾을 수 없습니다."),
	ITEM_NOT_FOUND("ITEM_002", "해당하는 아이템은 찾을 수 없습니다."),

	// REVIEW
	REVIEW_NOT_FOUND("REVIEW_001", "해당하는 리뷰는 찾을 수 없습니다."),
	REVIEW_NOT_EQUAL_ITEM("REVIEW_002", "리뷰 아이디와 아이템 아이디가 일치하지 않습니다."),
	REVIEW_NOT_MINE("REVIEW_003", "리뷰 작성자와 로그인한 회원아이디가 일치하지 않습니다."),
	//Bucket
	BUCKET_NOT_FOUND("BUCKET_001", "버킷을 찾을 수 없습니다.");

	private final String code;
	private final String message;
}
