package com.programmers.bucketback.domains.member.api.dto.response;

public record MemberLoginResponse(
        String nickname,
        String jwtToken
) {
}
