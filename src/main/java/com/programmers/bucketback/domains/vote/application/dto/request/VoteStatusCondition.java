package com.programmers.bucketback.domains.vote.application.dto.request;

public enum VoteStatusCondition {
	INPROGRESS,
	COMPLETED,
	POSTED,
	PARTICIPATED;

	public boolean isRequiredLogin() {
		return this == POSTED || this == PARTICIPATED;
	}
}