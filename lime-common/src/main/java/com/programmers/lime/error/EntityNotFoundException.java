package com.programmers.lime.error;

public class EntityNotFoundException extends BusinessException {
	public EntityNotFoundException(final ErrorCode errorCode) {
		super(errorCode);
	}
}
