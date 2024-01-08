package com.programmers.lime.domains.member.api.dto.response;

import com.programmers.lime.domains.member.application.dto.response.MemberCheckJwtServiceResponse;

public record MemberCheckJwtResponse(
	Long memberId,
	String nickname
) {
	public static MemberCheckJwtResponse from(final MemberCheckJwtServiceResponse serviceResponse) {
		return new MemberCheckJwtResponse(serviceResponse.memberId(), serviceResponse.nickname());
	}
}
