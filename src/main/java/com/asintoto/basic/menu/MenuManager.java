package com.asintoto.basic.menu;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class MenuManager {
    private static Map<Player, Menu> playerMenuMap = new HashMap<>();

    public static void init() {
        playerMenuMap = new HashMap<>();
    }

    public static void closeAllMenus() {
        for(Player p : playerMenuMap.keySet()) {
            p.closeInventory();
        }
    }

    public static void addPlayer(Player p, Menu m) {
        if(playerMenuMap.containsKey(p)) {
            playerMenuMap.remove(p);
        }

        playerMenuMap.put(p, m);
    }

    public static void removePlayer(Player p) {
        if(playerMenuMap.containsKey(p)) {
            playerMenuMap.remove(p);
        }
    }

    public static Menu getPlayerMenu(Player p) {
        if(playerMenuMap.containsKey(p)) {
            return playerMenuMap.get(p);
        }

        return null;
    }

    public static boolean contains(Player p) {
        return playerMenuMap.containsKey(p);
    }
}
