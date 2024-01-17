package com.programmers.lime.domains.item.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MemberItemFolderUpdateRequest(

	@Schema(description = "폴더 이름", example = "농구 취미 폴더")
	@NotNull(message = "폴더 이름을 입력하지 않았습니다.")
	@Size(min = 1, max = 20, message = "폴더 이름은 1자 이상 20자 이하로 입력해주세요.")
	String folderName
) {
}
