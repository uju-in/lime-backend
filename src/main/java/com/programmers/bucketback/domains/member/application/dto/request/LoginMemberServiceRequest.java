package com.programmers.bucketback.domains.member.application.dto.request;

import jakarta.validation.constraints.NotNull;

public record LoginMemberServiceRequest(
        @NotNull
        String email,

        @NotNull
        String password
) {
}
