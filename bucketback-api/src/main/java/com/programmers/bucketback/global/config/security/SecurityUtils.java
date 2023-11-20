package com.programmers.bucketback.global.config.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

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

	private static Authentication getAuthentication() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			throw new RuntimeException("Security context를 찾을 수 없습니다.");
		}

		return authentication;
	}

	private static boolean isAnonymousMember(final Authentication authentication) {
		return authentication.getAuthorities().contains(anonymousMember);
	}
}
