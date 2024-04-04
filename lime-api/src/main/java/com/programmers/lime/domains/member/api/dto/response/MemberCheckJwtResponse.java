package com.programmers.lime.domains.member.api.dto.response;

import com.programmers.lime.domains.member.application.dto.response.MemberCheckJwtServiceResponse;
import com.programmers.lime.domains.member.domain.vo.Role;

public record MemberCheckJwtResponse(
	Long memberId,
	String nickname,
	Role role
) {
	public static MemberCheckJwtResponse from(final MemberCheckJwtServiceResponse serviceResponse) {
		return new MemberCheckJwtResponse(serviceResponse.memberId(), serviceResponse.nickname(), serviceResponse.role());
	}
}
