package com.programmers.bucketback.domains.member.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum Level {
    LEVEL1(0),
    LEVEL2(20),
    LEVEL3(50),
    LEVEL4(90),
    LEVEL5(140),
    LEVEL6(200),
    LEVEL7(270),
    LEVEL8( 350),
    LEVEL9(440),
    LEVEL10(540),
    ;

    private static final int[] LEVEL_LIST;

    static {
        LEVEL_LIST = Stream.of(values())
                .mapToInt(Level::getPoint).toArray();
    }

    private final int point;

    public static int from(final int memberPoint) {
        final int level = Arrays.binarySearch(LEVEL_LIST, memberPoint);
        if (level < 0) {
            return -level - 1;
        }

        return level + 1;
    }
}
