package com.programmers.lime.domains.member.api.dto.request;

import com.programmers.lime.domains.member.domain.vo.Introduction;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record MemberUpdateProfileRequest(
	@Schema(description = "닉네임", example = "킹왕짱오리")
	@Size(min = 1, max = 25, message = "닉네임은 3글자에서 25글자 사이여야 합니다.")
	@Pattern(regexp = "^[A-Za-z0-9_가-힣]+$", message = "닉네임은 영어 대소문자, 한글, 숫자 그리고 언더스코어만 허용합니다.")
	@NotNull(message = "닉네임은 필수 값입니다.")
	String nickname,

	@Schema(description = "대표 취미", example = "농구")
	@NotNull(message = "대표 취미는 필수 값입니다.")
	String hobby,

	@Schema(description = "취미 경력", example = "1(년차)")
	@Min(value = 0, message = "취미 경력은 0보다 작을 수 없습니다.")
	@NotNull(message = "취미 경력은 필수 값입니다.")
	int career,

	@Schema(description = "자기소개", example = "안녕하세요!")
	@Size(max = 300, message = "자기소개는 최대 300자 입니다.")
	@NotNull(message = "자기소개는 필수 값입니다.")
	String content,

	@Schema(description = "MBTI", example = "ISFJ")
	@NotNull(message = "MBTI는 필수 값입니다.")
	String mbti
) {
	public Introduction toIntroduction() {
		return Introduction.builder()
			.hobby(hobby)
			.career(career)
			.content(content)
			.mbti(mbti)
			.build();
	}
}
