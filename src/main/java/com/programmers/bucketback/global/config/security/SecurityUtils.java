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
		Authentication authentication = getAuthentication();

		if (!authentication.isAuthenticated() || isAnonymousMember(authentication)) {
			return null;
		}

		return Long.valueOf(authentication.getName());
	}

	public static boolean isLoggedIn() {
		Authentication authentication = getAuthentication();

		return !isAnonymousMember(authentication);
	}

	private static Authentication getAuthentication() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			throw new BusinessException(ErrorCode.SECURITY_CONTEXT_NOT_FOUND);
		}

		return authentication;
	}

	private static boolean isAnonymousMember(final Authentication authentication) {
		return authentication.getAuthorities().contains(anonymousMember);
	}
}
