package com.programmers.bucketback.global.util;

import com.programmers.bucketback.global.config.security.SecurityUtils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MemberUtils {

	public static Long getCurrentMemberId() {
		return SecurityUtils.getCurrentMemberId();
	}
}
