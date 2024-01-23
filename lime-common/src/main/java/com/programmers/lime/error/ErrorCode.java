package com.programmers.lime.error;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
	FORBIDDEN("COMMON_006", "권한이 없습니다."),
	MISSING_PARAMETER("COMMON_007", "필수 파라미터가 있습니다."),
	EXPIRED_ACCESS_TOKEN("COMMON_008", "Access Token이 만료되었습니다."),
	INVALID_ACCESS_TOKEN("COMMON_009", "Access Token이 유효하지 않습니다."),
	IMAGE_MAXIMUM_SIZE_EXCEEDED("COMMON_010", "업로드할 수 있는 파일의 최대 크기를 초과했습니다."),
	INVALID_REQUEST_FILED_TYPE("COMMON_011", "잘못된 요청 필드 타입입니다."),
	EXPIRED_REFRESH_TOKEN("COMMON_012", "Refresh Token이 만료되었습니다."),
	INVALID_REFRESH_TOKEN("COMMON_013", "Refresh Token이 유효하지 않습니다."),

	// Member
	MEMBER_LOGIN_FAIL("MEMBER_001", "로그인 정보가 잘못 되었습니다."),
	MEMBER_NOT_FOUND("MEMBER_002", "회원을 찾을 수 없습니다."),
	MEMBER_ANONYMOUS("MEMBER_003", "익명의 사용자 입니다."),
	MEMBER_DELETED("MEMBER_004", "탈퇴한 회원 입니다."),
	MEMBER_NOT_LOGIN("MEMBER_006", "로그인 하지 않은 유저 입니다."),
	MEMBER_NICKNAME_DUPLICATE("MEMBER_007", "닉네임이 중복되었습니다."),
	MEMBER_NICKNAME_BAD_LENGTH("MEMBER_008", "닉네임은 3글자에서 25글자 사이여야 합니다."),
	MEMBER_NICKNAME_BAD_PATTERN("MEMBER_009", "닉네임은 영어 대소문자, 한글, 숫자 그리고 언더스코어만 허용합니다."),
	MEMBER_INTRODUCTION_CONTENT_BAD_LENGTH("MEMBER_012", "자기소개는 최대 300자 입니다."),
	MEMBER_INTRODUCTION_MBTI_BAD_REQUEST("MEMBER_014", "잘못된 MBTI 값입니다."),
	MEMBER_INTRODUCTION_FAVORABILITY_BAD_REQUEST("MEMBER_015", "잘못된 선호도 값입니다."),
	MEMBER_INTRODUCTION_CAREER_BAD_VALUE("MEMBER_016", "경력은 최소 0개월 이상이어야 합니다."),

	// Item
	ITEM_MARKET_NOT_FOUND("ITEM_001", "지원하지 않는 아이템 URL 입니다."),
	ITEM_NOT_FOUND("ITEM_002", "요청한 아이템은 찾을 수 없습니다."),
	ITEM_URL_ALREADY_EXIST("ITEM_003", "이미 존재하는 아이템 URL 입니다."),

	// REVIEW
	REVIEW_NOT_FOUND("REVIEW_001", "해당하는 리뷰는 찾을 수 없습니다."),
	REVIEW_NOT_EQUAL_ITEM("REVIEW_002", "리뷰 아이디와 아이템 아이디가 일치하지 않습니다."),
	REVIEW_NOT_MINE("REVIEW_003", "리뷰 작성자와 로그인한 회원아이디가 일치하지 않습니다."),

	// Vote
	VOTE_NOT_FOUND("VOTE_001", "투표를 찾을 수 없습니다."),
	VOTE_NOT_CONTAIN_ITEM("VOTE_002", "투표에 포함된 아이템이 아닙니다."),
	VOTE_NOT_OWNER("VOTE_003", "투표의 작성자가 아닙니다."),
	VOTE_NOT_VOTER("VOTE_004", "투표의 투표자가 아닙니다."),
	VOTE_CANNOT_SORT("VOTE_005", "인기순 정렬을 할 수 없습니다."),
	VOTE_BAD_STATUS_CONDITION("VOTE_006", "잘못된 status 파라미터 값입니다."),
	VOTE_BAD_SORT_CONDITION("VOTE_007", "잘못된 sort 파라미터 값입니다."),
	VOTE_CANNOT_PARTICIPATE("VOTE_008", "종료된 투표에는 참여할 수 없습니다."),
	VOTE_CONTENT_BAD_LENGTH("VOTE_009", "투표 내용은 최대 1000자 입니다."),

	//Bucket
	BUCKET_NOT_FOUND("BUCKET_001", "버킷을 찾을 수 없습니다."),
	BUCKET_ITEM_NOT_FOUND("BUCKET_002", "버킷아이템을 찾을 수 없습니다."),
	BUCKET_EXCEED_BUDGET("BUCKET_003", "버킷 예산을 초과했습니다."),
	BUCKET_INVALID_NAME("BUCKET_004", "유효하지 않은 길이의 버킷 이름입니다."),
	BUCKET_INVALID_BUDGET("BUCKET_005", "유호하지 않은 버킷 예산입니다."),
	BUCKET_ITEM_NOT_REQUESTED("BUCKET_006", "버킷 아이템이 선택되지 않았습니다."),

	// MemberItem
	MEMBER_ITEM_NOT_FOUND("MEMBER_ITEM_001", "나의 아이템을 찾을 수 없습니다."),
	MEMBER_NOT_MINE("MEMBER_ITEM_002", "아이템 담기에 없는 아이템 입니다."),
	MEMBER_ITEM_ALREADY_EXIST("MEMBER_ITEM_003", "이미 존재하는 아이템 입니다."),
	MEMBER_ITEM_HOBBY_NOT_EQUAL_TO_FOLDER_HOBBY("MEMBER_ITEM_004", "나의 아이템 취미와 폴더의 취미가 다릅니다."),
	MEMBER_ITEM_ID_LIST_IS_EMPTY("MEMBER_ITEM_005", "나의 아이템 목록이 비어있습니다."),

	//Inventory
	INVENTORY_NOT_FOUND("INVENTORY_001", "인벤토리를 찾을 수 없습니다."),
	INVENTORY_ALREADY_EXIST("INVENTORY_002", "이미 생성된 인벤토리가 있습니다."),
	INVENTORY_ITEM_NOT_FOUND("INVENTORY_003", "인벤토리의 아이템을 찾을 수 없습니다."),
	INVENTORY_ITEM_NOT_REQUESTED("INVENTORY_004", "인벤토리의 아이템이 선택되지 않았습니다."),

	//Feed
	FEED_NOT_FOUND("FEED_001", "피드를 찾을 수 없습니다."),
	FEED_ALREADY_LIKED("FEED_002", "이미 좋아요룰 늘렀습니다."),
	FEED_LIKE_NOT_FOUND("FEED_003", "좋아요한 피드를 찾을 수 없습니다."),
	FEED_BAD_SORT_CONDITION("FEED_004", "잘못된 피드 정렬 조건 입니다."),
	FEED_BAD_LIKE_ONLY_REQUEST("FEED_005", "좋아요 피드만 보기 위해서는 닉네임은 필수 값 입니다."),

	//Comment
	COMMENT_NOT_FOUND("COMMENT_001", "피드 댓글을 찾을 수 없습니다."),
	COMMENT_NOT_IN_FEED("COMMENT_002", "피드에 존재하지 않은 댓글 입니다."),
	COMMENT_NOT_MINE("COMMENT_003", "댓글 작성자와 일치 하지 않은 사용자 입니다."),
	COMMENT_CANNOT_ADOPT("COMMENT_004", "본인의 댓글을 채택할 수 없습니다."),
	COMMENT_CONTENT_BAD_LENGTH("COMMENT_005", "댓글 내용은 최대 300자 입니다."),

	// Hobby
	HOBBY_BAD_PARAMETER("HOBBY_001", "잘못된 hobby 파라미터 값입니다."),

	// Crawler
	CRAWLER_NAVER_BAD_REQUEST("CRAWLER", "네이버 크롤러에서 파싱할 수 없는 URL 입니다."),
	CRAWLER_COUPANG_BAD_REQUEST("CRAWLER", "쿠팡 크롤러에서 파싱할 수 없는 URL 입니다."),
	CRAWLER_DANAWA_BAD_REQUEST("CRAWLER", "다나와 크롤러에서 파싱할 수 없는 URL 입니다."),

	// MemberItemFolder
	MEMBER_ITEM_FOLDER_NOT_FOUND("MEMBER_ITEM_FOLDER_001", "폴더를 찾을 수 없습니다."),
	MEMBER_ITEM_FOLDER_NOT_MINE("MEMBER_ITEM_FOLDER_002", "나의 아이템 폴더가 아닙니다."),

	// Friendship
	FRIENDSHIP_NOT_FOUND("FRIENDSHIP_001", "친구 관계를 찾을 수 없습니다."),
	FRIENDSHIP_ALREADY_EXISTS("FRIENDSHIP_002", "이미 친구 관계가 존재합니다."),
	;

	private static final Map<String, ErrorCode> ERROR_CODE_MAP;

	static {
		ERROR_CODE_MAP = Collections.unmodifiableMap(Stream.of(values())
			.collect(Collectors.toMap(ErrorCode::getMessage, Function.identity())));
	}

	private final String code;
	private final String message;

	public static ErrorCode from(final String message) {
		if (ERROR_CODE_MAP.containsKey(message)) {
			return ERROR_CODE_MAP.get(message);
		}

		return ErrorCode.INTERNAL_SERVER_ERROR;
	}
}
