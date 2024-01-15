package com.programmers.lime.global.config.security.auth;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import com.programmers.lime.domains.member.domain.vo.Role;

import lombok.Getter;

@Getter
public class CustomOauth2User extends DefaultOAuth2User {

	private Long memberId;
	private Role role;

	public CustomOauth2User(
		final Collection<? extends GrantedAuthority> authorities,
		final Map<String, Object> attributes,
		final String nameAttributeKey,
		final Long memberId,
		final Role role
	) {
		super(authorities, attributes, nameAttributeKey);
		this.memberId = memberId;
		this.role = role;
	}
}
