package com.programmers.lime.global.config.chat.type;

public enum DestinationEnum {

	CHAT("/subscribe/rooms", DestinationType.PRIVATE_CHAT_ROOM),
	ROOM_ENTRY("/subscribe/rooms/join", DestinationType.PRIVATE_CHAT_ROOM),
	ROOM_EXIT("/subscribe/rooms/exit", DestinationType.PRIVATE_CHAT_ROOM);

	private final String path;
	private final DestinationType type;

	DestinationEnum(String path, DestinationType type) {
		this.path = path;
		this.type = type;
	}

	public String getPath() {
		return path;
	}

	public DestinationType getType() {
		return type;
	}

	public static DestinationEnum findByPath(String path) {
		// 슬래시와 숫자가 함께 붙어 있는 문자를 제외하기 위해 정규 표현식 사용
		// subscribe/rooms/1 -> subscribe/rooms
		String processedPath = path.replaceAll("/\\d+$", "");

		for (DestinationEnum destinationEnum : values()) {
			if (processedPath.equals(destinationEnum.getPath())) {
				return destinationEnum;
			}
		}
		return null;
	}

}
