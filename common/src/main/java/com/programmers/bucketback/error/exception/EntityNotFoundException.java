package com.programmers.bucketback.error.exception;

public class EntityNotFoundException extends BusinessException {
	public EntityNotFoundException(final ErrorCode errorCode) {
		super(errorCode);
	}
}
