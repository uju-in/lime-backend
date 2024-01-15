package com.programmers.lime.domains.member.domain;

import com.programmers.lime.domains.BaseEntity;
import com.programmers.lime.domains.member.domain.vo.Introduction;
import com.programmers.lime.domains.member.domain.vo.MemberStatus;
import com.programmers.lime.domains.member.domain.vo.Nickname;
import com.programmers.lime.domains.member.domain.vo.Role;
import com.programmers.lime.domains.member.domain.vo.SocialInfo;

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
	private SocialInfo socialInfo;

	@Embedded
	private Nickname nickname;

	@Embedded
	private Introduction introduction;

	@Column(name = "level_point", nullable = false)
	private Integer levelPoint;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private MemberStatus status;

	public Member(
		final SocialInfo socialInfo,
		final String nickname
	) {
		this.socialInfo = socialInfo;
		this.nickname = new Nickname(nickname);
		this.levelPoint = 0;
		this.status = MemberStatus.ACTIVE;
	}

	public String getEmail() {
		return socialInfo.getEmail();
	}

	public String getSocialId() {
		return socialInfo.getSocialId();
	}

	public Role getRole() {
		return socialInfo.getRole();
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

	public String getProfileImage() {
		return this.socialInfo.getProfileImage();
	}

	public void delete() {
		this.status = MemberStatus.DELETED;
		this.nickname.delete();
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

	public int getLevel() {
		return Level.from(this.levelPoint);
	}

	public void earnPoint(final int point) {
		this.levelPoint += point;
	}

	public void updateProfileImage(final String profileImage) {
		this.socialInfo.updateProfileImage(profileImage);

	}
}
