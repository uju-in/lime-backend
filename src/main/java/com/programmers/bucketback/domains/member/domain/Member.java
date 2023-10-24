package com.programmers.bucketback.domains.member.domain;

import com.programmers.bucketback.domains.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
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

	@NotNull
	@Column(name = "email")
	private String email;

	@NotNull
	@Column(name = "password")
	private String password;

	@NotNull
	@Column(name = "nickname")
	private String nickname;

	@NotNull
	@Column(name = "introduction")
	private String introduction;

	@NotNull
	@Column(name = "level_point")
	private Integer levelPoint;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "role")
	private Role role;
}
