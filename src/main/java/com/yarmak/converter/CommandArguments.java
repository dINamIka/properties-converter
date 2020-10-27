package com.yarmak.converter;

import java.util.Arrays;

public enum CommandArguments {
    YAML("-y"),
    PROPERTIES("-p");

    private String arg;

    CommandArguments(final String arg) {
        this.arg = arg;
    }

    public String arg() {
        return arg;
    }

    public static CommandArguments getCommand(final String val) {
        return Arrays.stream(CommandArguments.values())
                .filter(e -> e.arg().equals(val))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Not valid command argument!"));
    }
}
