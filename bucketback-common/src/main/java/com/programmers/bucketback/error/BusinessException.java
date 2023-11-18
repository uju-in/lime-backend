package com.programmers.bucketback.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BusinessException extends RuntimeException {

	private final ErrorCode errorCode;
}
