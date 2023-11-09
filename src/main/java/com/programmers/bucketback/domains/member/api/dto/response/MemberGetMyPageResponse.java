package com.programmers.bucketback.domains.member.api.dto.response;

import com.programmers.bucketback.domains.member.application.vo.MyPage;

public record MemberGetMyPageResponse(
	MyPage myPage
) {
	public static MemberGetMyPageResponse from(final MyPage myPage) {
		return new MemberGetMyPageResponse(myPage);
	}
}
