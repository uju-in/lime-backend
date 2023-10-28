package com.programmers.bucketback.domains.member.api.dto.request;

import com.programmers.bucketback.domains.member.application.dto.request.UpdateProfileMemberServiceRequest;

import jakarta.validation.constraints.NotNull;

public record MemberUpdateProfileRequest(
	@NotNull
	String nickname,

	@NotNull
	String introduction
) {
	public UpdateProfileMemberServiceRequest toUpdateProfileMemberServiceRequest() {
		return new UpdateProfileMemberServiceRequest(nickname, introduction);
	}
}
