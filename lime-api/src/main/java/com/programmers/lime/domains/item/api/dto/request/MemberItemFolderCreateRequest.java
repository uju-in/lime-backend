package com.programmers.lime.domains.item.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record MemberItemFolderCreateRequest(
	@Schema(description = "취미", example = "농구")
	@NotNull(message = "취미 이름을 입력하지 않았습니다.")
	String hobbyName,

	@Schema(description = "폴더 이름", example = "농구 취미 폴더")
	@NotNull(message = "폴더 이름을 입력하지 않았습니다.")
	String folderName
){
}
