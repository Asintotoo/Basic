package com.asintoto.basic.enums;

import java.util.ArrayList;
import java.util.List;

public class BasicChar {
    public static final String CROSS = "❌";
    public static final String CHECK = "✔";
    public static final String WARNING = "⚠";
    public static final String SKULL = "☠";
    public static final String CROSSED_SWORD = "⚔";

    private static final List<String> charList = new ArrayList<>();

    static {
        charList.add(CROSS);
        charList.add(CHECK);
        charList.add(WARNING);
        charList.add(SKULL);
        charList.add(CROSSED_SWORD);
    }

    public static List<String> getCharList() {
        return charList;
    }
}
