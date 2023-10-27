package com.programmers.bucketback.domains.common;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

	@CreatedDate
	@NotNull
	@Column(name = "create_at", columnDefinition = "datetime(6)", updatable = false)
	private LocalDateTime createdAt;

	@LastModifiedDate
	@NotNull
	@Column(name = "modify_at", columnDefinition = "datetime(6)")
	private LocalDateTime modifiedAt;

}
