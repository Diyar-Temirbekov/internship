package mn.partners.runtime.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;

public enum RoomType {

    STANDARD,
    SUPERIOR,
    STUDIO,
    FAMILY,
    DELUXE,
    SUITE;

    @JsonCreator
    public static RoomType fromString(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("RoomType не может быть пустым или null");
        }

        try {
            return RoomType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Некорректный RoomType: " + value +
                    ". Допустимые значения: " + Arrays.toString(RoomType.values()));
        }
    }
}