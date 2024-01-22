package com.programmers.lime.domains.item.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.programmers.lime.domains.item.model.MemberItemObjectInfo;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberItemObjectReader {

	private final List<IObjectReader> objectReaders;

	public List<MemberItemObjectInfo> readObjects(
		final Long folderId,
		final Long memberId
	) {

		List<MemberItemObjectInfo> memberItemObjectInfos = new ArrayList<>();

		for (IObjectReader objectReader : objectReaders) {
			List<MemberItemObjectInfo> memberItemObjectInfosPart = objectReader.readObjects(folderId, memberId);
			memberItemObjectInfos.addAll(memberItemObjectInfosPart);
		}

		return memberItemObjectInfos;
	}
}
