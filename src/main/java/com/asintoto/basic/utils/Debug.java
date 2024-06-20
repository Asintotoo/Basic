package com.asintoto.basic.utils;

import com.asintoto.basic.Basic;
import org.bukkit.Bukkit;

public class Debug {
    public static void log(String msg) {

        if(!Basic.getOptions().isDebugMode()) {
            return;
        }

        Bukkit.getServer().getConsoleSender().sendMessage("[Basic DEBUG] " + msg);
    }
}
