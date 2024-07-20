package com.asintoto.basic.listeners;

import com.asintoto.basic.menu.Menu;
import com.asintoto.basic.menu.MenuManager;
import com.asintoto.basic.utils.BasicKeys;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class InventoryCloseListener implements Listener {
    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();

        if(!MenuManager.contains(p)) return;

        Menu menu = MenuManager.getPlayerMenu(p);

        MenuManager.removePlayer(p);

        removePersistentDataButton(p);
        menu.onClose(p);
    }


    private void removePersistentDataButton(Player p) {
        for(ItemStack item : p.getInventory().getContents()) {
            removeButtonStatus(item);
        }

        for(ItemStack item : p.getInventory().getArmorContents()) {
            removeButtonStatus(item);
        }
    }

    private void removeButtonStatus(ItemStack item) {

        if(item == null) return;

        ItemMeta meta = item.getItemMeta();

        if(meta == null) return;

        if(meta.getPersistentDataContainer().has(BasicKeys.BUTTON_IS_BUTTON, PersistentDataType.BOOLEAN)) {
            meta.getPersistentDataContainer().remove(BasicKeys.BUTTON_IS_BUTTON);
            item.setItemMeta(meta);
        }
    }
}
