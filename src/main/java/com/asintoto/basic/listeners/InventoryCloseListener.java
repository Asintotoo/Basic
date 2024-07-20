package com.asintoto.basic.listeners;

import com.asintoto.basic.menu.Menu;
import com.asintoto.basic.menu.MenuManager;
import com.asintoto.basic.utils.BasicKeys;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.persistence.PersistentDataType;

public class InventoryCloseListener implements Listener {
    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();

        if(!MenuManager.contains(p)) return;

        Menu menu = MenuManager.getPlayerMenu(p);

        MenuManager.removePlayer(p);

        menu.onClose(p);
    }
}
