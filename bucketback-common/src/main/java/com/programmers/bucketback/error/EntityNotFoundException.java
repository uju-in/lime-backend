package com.programmers.bucketback.error;

public class EntityNotFoundException extends BusinessException {
	public EntityNotFoundException(final ErrorCode errorCode) {
		super(errorCode);
	}
}
