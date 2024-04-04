package com.programmers.lime.domains.member.application.dto.response;

import com.programmers.lime.domains.member.domain.vo.Role;

public record MemberCheckJwtServiceResponse(
	Long memberId,
	String nickname,
	Role role
) {
}
