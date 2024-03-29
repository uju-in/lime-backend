package com.programmers.lime.domains.item.implementation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.programmers.lime.common.model.FavoriteType;
import com.programmers.lime.domains.item.domain.Item;
import com.programmers.lime.domains.item.domain.MemberItem;
import com.programmers.lime.domains.item.domain.MemberItemFolder;
import com.programmers.lime.domains.item.model.FolderMetadata;
import com.programmers.lime.domains.item.model.MemberItemFavoriteInfo;
import com.programmers.lime.domains.item.model.MemberItemFavoriteMetadata;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberItemFolderFavoriteInfoReader implements IFavoriteReader {

	private final MemberItemFolderReader memberItemFolderReader;
	private static final int DEFAULT_IMAGE_SIZE = 3;

	private final MemberItemReader memberItemReader;
	private static final String DEFAULT_FOLDER_NAME = "default";

	/*
	 *
	 * folder id 가 null 이면 root folder 를 의미한다.
	 * 제약조건으로 root folder일 때 폴더를 가진다.
	 * 따라서 readObject는 root folder 일 때(folderId == null)만 memberItemFolder 를 읽어온다.
	 * */
	@Override
	public List<MemberItemFavoriteInfo> readFavorites(final Long folderId, final Long memberId) {
		if (folderId != null) {
			return Collections.emptyList();
		}

		List<MemberItemFolder> memberItemFolders = memberItemFolderReader.readMemberItemFoldersByMemberId(memberId);
		moveDefaultFolderToEnd(memberItemFolders);

		return memberItemFolders.stream()
			.map(this::mapToMemberItemObjectInfo)
			.toList();
	}

	private void moveDefaultFolderToEnd(final List<MemberItemFolder> memberItemFolders) {
		List<MemberItemFolder> defaultFolders = new ArrayList<>();

		memberItemFolders.removeIf(folder -> {
			boolean isDefault = DEFAULT_FOLDER_NAME.equals(folder.getName());
			if (isDefault) {
				defaultFolders.add(folder);
			}
			return isDefault;
		});

		memberItemFolders.addAll(defaultFolders);
	}

	@Override
	public FavoriteType getFavoriteType() {
		return FavoriteType.FOLDER;
	}

	private MemberItemFavoriteInfo mapToMemberItemObjectInfo(final MemberItemFolder memberItemFolder) {
		return MemberItemFavoriteInfo.builder()
			.favoriteId(memberItemFolder.getId())
			.originalName(memberItemFolder.getName())
			.type(FavoriteType.FOLDER)
			.createdAt(memberItemFolder.getCreatedAt())
			.metadata(toMetadata(memberItemFolder))
			.modifiedAt(memberItemFolder.getModifiedAt())
			.build();
	}

	private MemberItemFavoriteMetadata toMetadata(final MemberItemFolder memberItemFolder) {

		List<MemberItem> memberItems = memberItemReader.readByFolderId(memberItemFolder.getId());
		ArrayList<String> imageUrls = memberItems.stream()
			.limit(DEFAULT_IMAGE_SIZE)
			.map(MemberItem::getItem)
			.map(Item::getImage)
			.collect(Collectors.toCollection(ArrayList::new));

		return MemberItemFavoriteMetadata.builder()
			.folderMetadata(
				FolderMetadata.builder()
					.imageUrls(imageUrls)
					.itemCount(memberItems.size())
					.build()
			)
			.build();
	}
}
