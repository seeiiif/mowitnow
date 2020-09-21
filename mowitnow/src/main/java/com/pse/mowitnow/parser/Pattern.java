package com.pse.mowitnow.parser;

public enum Pattern {

    SEPARATOR  (" "),
    GRID_PATTERN ("^\\d+ \\d+$"),
    MOWER_PATTERN ("^\\d+ \\d+ [N|E|W|S]$");


    private final String pattern;

    Pattern(String pattern) {
        this.pattern = pattern;
    }

    public String getPattern() {
        return pattern;
    }
}
