package com.programmers.bucketback.domains.member.domain;

import com.programmers.bucketback.domains.BaseEntity;
import com.programmers.bucketback.domains.member.domain.vo.Introduction;
import com.programmers.bucketback.domains.member.domain.vo.LoginInfo;
import com.programmers.bucketback.domains.member.domain.vo.MemberStatus;
import com.programmers.bucketback.domains.member.domain.vo.Nickname;
import com.programmers.bucketback.domains.member.domain.vo.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

	@Embedded
	private Introduction introduction;

	@Column(name = "profile_image")
	private String profileImage;

	@Column(name = "level_point", nullable = false)
	private Integer levelPoint;

	@Enumerated(EnumType.STRING)
	@Column(name = "role", nullable = false)
	private Role role;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private MemberStatus status;

	@Builder
	private Member(
		final String email,
		final String password,
		final String nickname,
		final Role role
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

	public String getIntroduction() {
		if (introduction == null) {
			return null;
		}

		return introduction.getIntroduction();
	}

	public void delete() {
		this.status = MemberStatus.DELETED;
		this.loginInfo.delete();
	}

	public boolean isDeleted() {
		return this.getStatus() == MemberStatus.DELETED;
	}

	public void updateProfile(
		final String nickname,
		final String introduction
	) {
		this.nickname = new Nickname(nickname);
		this.introduction = new Introduction(introduction);
	}

	public void updatePassword(final String password) {
		this.loginInfo.updatePassword(password);
	}

	public int getLevel() {
		return Level.from(this.levelPoint);
	}

	public void earnPoint(final int point) {
		this.levelPoint += point;
	}

	public void updateProfileImage(final String profileImage) {
		this.profileImage = profileImage;
	}
}
