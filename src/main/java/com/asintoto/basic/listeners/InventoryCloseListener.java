package com.asintoto.basic.listeners;

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

        /*if(p.getPersistentDataContainer().has(BasicKeys.BASIC_MENU_OPEN, PersistentDataType.BOOLEAN)) {
            p.getPersistentDataContainer().remove(BasicKeys.BASIC_MENU_OPEN);
        }*/

        MenuManager.removePlayer(p);
    }
}
