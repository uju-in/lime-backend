package com.programmers.lime.domains.member.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class SocialInfo {

	@Column(name = "social_id")
	private Long socialId;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "profile_image")
	private String profileImage;

	@Enumerated(EnumType.STRING)
	@Column(name = "social_type", nullable = false)
	private SocialType socialType;

	@Enumerated(EnumType.STRING)
	@Column(name = "role", nullable = false)
	private Role role;

	@Builder
	public SocialInfo(
		final Long socialId,
		final String email,
		final String profileImage,
		final SocialType socialType,
		final Role role
	){
		this.socialId = socialId;
		this.email = email;
		this.profileImage = profileImage;
		this.socialType = socialType;
		this.role = role;
	}

	public void updateProfileImage(final String profileImage) {
		this.profileImage = profileImage;
	}

	public void updateRole(final Role role) {
		this.role = role;
	}
}
