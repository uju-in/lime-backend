package com.programmers.bucketback.domains.member.api.dto.request;

import com.programmers.bucketback.domains.member.domain.LoginInfo;

import jakarta.validation.constraints.NotNull;

public record MemberLoginRequest(
        @NotNull
        String email,

        @NotNull
        String password
) {
    public LoginInfo toLoginInfo() {
        return new LoginInfo(email, password);
    }
}
