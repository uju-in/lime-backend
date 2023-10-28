package com.programmers.bucketback.global.config.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.programmers.bucketback.global.error.exception.BusinessException;
import com.programmers.bucketback.global.error.exception.ErrorCode;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SecurityUtils {

	private static final SimpleGrantedAuthority anonymousMember = new SimpleGrantedAuthority("ROLE_ANONYMOUS");

	public static Long getCurrentMemberId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			throw new BusinessException(ErrorCode.SECURITY_CONTEXT_NOT_FOUND);
		}

		if (!authentication.isAuthenticated() || authentication.getAuthorities().contains(anonymousMember)) {
			throw new BusinessException(ErrorCode.MEMBER_ANONYMOUS);
		}

		return Long.valueOf(authentication.getName());
	}
}
