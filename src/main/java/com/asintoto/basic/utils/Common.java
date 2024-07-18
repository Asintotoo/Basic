package com.asintoto.basic.utils;

import com.asintoto.basic.Basic;

public class Common {

    private final static String errorPrefix = "&7[&r&4&l" + "X" + "&r&7]&r&c ";
    private final static String successPrefix = "&7[&r&a&l" + "OK" + "&r&7]&r<SOLID:42f554> ";
    private final static String warningPrefix = "&7[&r&e&l" + "!" + "&r&7]&r&e ";

    public static void error(String msg) {
        Basic.sendConsoleMessage(errorPrefix + msg);
    }

    public static void warning(String msg) {
        Basic.sendConsoleMessage(warningPrefix + msg);
    }

    public static void success(String msg) {
        Basic.sendConsoleMessage(successPrefix + msg);
    }
}
