package com.programmers.lime.domains.member.api.dto.request;

import java.time.YearMonth;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.programmers.lime.domains.member.domain.vo.Introduction;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record MemberUpdateProfileRequest(
	@Schema(description = "닉네임", example = "킹왕짱오리")
	@NotNull(message = "닉네임은 필수 값입니다.")
	String nickname,

	@Schema(description = "대표 취미", example = "농구")
	@NotNull(message = "대표 취미는 필수 값입니다.")
	String hobby,

	@Schema(description = "취미 시작일", example = "2023.02")
	@NotNull(message = "취미 시작일은 필수 값입니다.")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM", timezone = "Asia/Seoul")
	YearMonth startDate,

	@Schema(description = "자기소개", example = "안녕하세요!")
	@NotNull(message = "자기소개는 필수 값입니다.")
	String content,

	@Schema(description = "MBTI", example = "ISFJ")
	@NotNull(message = "MBTI는 필수 값입니다.")
	String mbti
) {
	public Introduction toIntroduction() {
		return Introduction.builder()
			.hobby(hobby)
			.startDate(startDate)
			.content(content)
			.mbti(mbti)
			.build();
	}
}
