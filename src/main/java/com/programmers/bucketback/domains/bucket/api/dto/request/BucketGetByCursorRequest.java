package com.programmers.bucketback.domains.bucket.api.dto.request;

import com.programmers.bucketback.domains.bucket.application.vo.CursorPageParameters;

public record BucketGetByCursorRequest(
	//null 가능
	Long cursorId,

	int size

) {
	public CursorPageParameters toParameters(){
		return new CursorPageParameters(cursorId, size);
	}
}
