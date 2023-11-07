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
	MAIL_SEND_FAIL("COMMON_004", "이메일 인증코드 전송이 실패했습니다."),
	UNAUTHORIZED("COMMON_005", "로그인이 필요한 기능입니다."),

	// Member
	MEMBER_LOGIN_FAIL("MEMBER_001", "로그인 정보가 잘못 되었습니다."),
	MEMBER_NOT_FOUND("MEMBER_002", "회원을 찾을 수 없습니다."),
	MEMBER_ANONYMOUS("MEMBER_003", "익명의 사용자 입니다."),
	MEMBER_DELETED("MEMBER_004", "탈퇴한 회원 입니다."),
	MEMBER_EMAIL_EXIST("MEMBER_005", "이메일이 중복되었습니다."),
	MEMBER_NOT_LOGIN("MEMBER_006", "로그인 하지 않은 유저 입니다."),

	// Item
	ITEM_MARKET_NOT_FOUND("ITEM_001", "요청 url에 해당하는 상품을 찾을 수 없습니다."),
	ITEM_NOT_FOUND("ITEM_002", "해당하는 아이템은 찾을 수 없습니다."),
	ITEM_URL_DUPLICATED("ITEM_003", "중복되는 ITEM URL 입니다."),

	// REVIEW
	REVIEW_NOT_FOUND("REVIEW_001", "해당하는 리뷰는 찾을 수 없습니다."),
	REVIEW_NOT_EQUAL_ITEM("REVIEW_002", "리뷰 아이디와 아이템 아이디가 일치하지 않습니다."),
	REVIEW_NOT_MINE("REVIEW_003", "리뷰 작성자와 로그인한 회원아이디가 일치하지 않습니다."),

	// Vote
	VOTE_NOT_FOUND("VOTE_001", "투표를 찾을 수 없습니다."),
	VOTE_NOT_CONTAIN_ITEM("VOTE_002", "투표에 포함된 아이템이 아닙니다."),
	VOTE_NOT_OWNER("VOTE_003", "투표의 작성자가 아닙니다."),
	VOTE_NOT_VOTER("VOTE_004", "투표의 투표자가 아닙니다."),
	VOTE_BAD_POPULARITY("VOTE_005", "종료된 투표만 인기순 조회가 가능합니다."),

	//Bucket
	BUCKET_NOT_FOUND("BUCKET_001", "버킷을 찾을 수 없습니다."),
	BUCKET_ITEM_NOT_FOUND("BUCKET_002", "버킷아이템을 찾을 수 없습니다."),

	// MemberItem
	MEMBER_ITEM_NOT_FOUND("MEMBER_ITEM_001", "나의 아이템을 찾을 수 없습니다."),
	MEMBER_NOT_MINE("MEMBER_ITEM_002", "아이템 담기에 없는 아이템 입니다."),

	//Inventory
	INVENTORY_NOT_FOUND("INVENTORY_001", "인벤토리를 찾을 수 없습니다."),
	INVENTORY_ALREADY_EXIST("INVENTORY_002", "이미 생성된인벤토리가 잇습니다."),
	INVENTORY_ITEM_NOT_FOUND("INVENTORY_003", "인벤토리의 아이템을 찾을 수 없습니다."),

	//Feed
	FEED_NOT_FOUND("FEED_001", "피드를 찾을 수 없습니다."),
	FEED_ALREADY_LIKED("FEED_002", "이미 좋아요룰 늘렀습니다."),
	FEED_LIKE_NOT_FOUND("FEED_003", "좋아요한 피드를 찾을 수 없습니다.");

	private final String code;
	private final String message;
}
