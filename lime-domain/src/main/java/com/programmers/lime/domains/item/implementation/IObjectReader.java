package com.programmers.lime.domains.item.implementation;

import java.util.List;

import com.programmers.lime.domains.item.model.MemberItemObjectInfo;

public interface IObjectReader {

	List<MemberItemObjectInfo> readObjects(final Long folderId, final Long memberId);
}
