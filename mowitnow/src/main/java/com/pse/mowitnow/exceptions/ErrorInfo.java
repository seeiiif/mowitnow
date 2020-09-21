package com.pse.mowitnow.exceptions;

public enum ErrorInfo {
    INSTRUCTIONS_NOT_FOUND_EXCEPTION("The instructions file is missing or inaccessible"),
    LAWN_COORDINATES_EXCEPTION_FORMAT("Lawn's coordinates are wrong or unreadable"),
    LAWN_COORDINATES_EXCEPTION_VALUES("Lawn's coordinates must be positive and > 0"),
    MOWER_COORDINATES_EXCEPTION_FORMAT("Mower's coordinates are wrong or unreadable"),
    MOWER_COORDINATES_EXCEPTION_OUT_OF_LAWN("Mower is out of lawn"),
    MOVE_EXCEPTION_WRONG_MOVE("Wrong move. Expected instruction : G|D|A n times");

    private final String message;

    ErrorInfo(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
