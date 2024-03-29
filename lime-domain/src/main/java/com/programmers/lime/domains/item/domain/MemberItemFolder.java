package com.programmers.lime.domains.item.domain;

import java.util.Objects;

import com.programmers.lime.domains.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "member_item_folders")
public class MemberItemFolder extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "member_id", nullable = false)
	private Long memberId;

	public MemberItemFolder(final String name, final Long memberId) {
		this.name = Objects.requireNonNull(name);
		this.memberId = Objects.requireNonNull(memberId);
	}

	public void modifyFolderName(final String folderName) {
		this.name = folderName;
	}
}
