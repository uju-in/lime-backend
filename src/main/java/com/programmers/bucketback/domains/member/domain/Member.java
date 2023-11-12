package com.programmers.bucketback.domains.member.domain;

import com.programmers.bucketback.domains.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "members")
public class Member extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Embedded
	private LoginInfo loginInfo;

	@Embedded
	private Nickname nickname;

	@Column(name = "introduction")
	private String introduction;

	@NotNull
	@Column(name = "level_point")
	private Integer levelPoint;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "role")
	private Role role;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private MemberStatus status;

	@Builder
	private Member(
		@NotNull final String email,
		@NotNull final String password,
		@NotNull final String nickname,
		@NotNull final Role role
	) {
		this.loginInfo = new LoginInfo(email, password);
		this.nickname = new Nickname(nickname);
		this.levelPoint = 0;
		this.role = role;
		this.status = MemberStatus.ACTIVE;
	}

	public static void validatePassword(final String password) {
		LoginInfo.validatePassword(password);
	}

	public String getPassword() {
		return loginInfo.getPassword();
	}

	public String getNickname() {
		return nickname.getNickname();
	}

	public void delete() {
		this.status = MemberStatus.DELETED;
	}

	public boolean isDeleted() {
		return this.getStatus() == MemberStatus.DELETED;
	}

	public void updateProfile(
		final String nickname,
		final String introduction
	) {
		this.nickname = new Nickname(nickname);
		this.introduction = introduction;
	}

	public void updatePassword(final String password) {
		this.loginInfo.updatePassword(password);
	}
}
