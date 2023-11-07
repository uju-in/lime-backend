package com.programmers.bucketback.domains.vote.application;

public enum VoteStatusCondition {
	INPROGRESS,
	COMPLETED,
	POSTED,
	PARTICIPATED;

	public boolean isRequiredLogin() {
		return this == POSTED || this == PARTICIPATED;
	}
}