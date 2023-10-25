package com.programmers.bucketback.domains.member.api.dto.request;

import com.programmers.bucketback.domains.member.application.dto.request.LoginMemberServiceRequest;
import jakarta.validation.constraints.NotNull;

public record MemberLoginRequest(
        @NotNull
        String email,

        @NotNull
        String password
) {
    public LoginMemberServiceRequest toLoginMemberServiceRequest() {
        return new LoginMemberServiceRequest(email, password);
    }
}
