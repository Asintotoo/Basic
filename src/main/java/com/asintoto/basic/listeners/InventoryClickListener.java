package com.asintoto.basic.listeners;

import com.asintoto.basic.menu.Menu;
import com.asintoto.basic.menu.MenuManager;
import com.asintoto.basic.utils.BasicKeys;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        ItemStack item = e.getCurrentItem();

        if(item == null) return;

        if(!MenuManager.contains(p)) return;

        PersistentDataContainer itemData = item.getItemMeta().getPersistentDataContainer();

        Menu menu = MenuManager.getPlayerMenu(p);

        if(menu == null) return;

        int rawSlot = e.getRawSlot();
        int slot = e.getSlot();

        if(menu.hasPlayerInventoryProtection()) {
            if(isInventoryClick(slot, rawSlot, menu)) {
                return;
            }

            if(!itemData.has(BasicKeys.BUTTON_IS_BUTTON, PersistentDataType.BOOLEAN)) {
                return;
            }
        }

        boolean isUlnocked = itemData.has(BasicKeys.BUTTON_TYPE_UNLOCKED, PersistentDataType.BOOLEAN);

        if(!isUlnocked) {
            e.setCancelled(true);
        }

        if(itemData.has(BasicKeys.BUTTON_TYPE_CLOSE, PersistentDataType.BOOLEAN)) {
            p.closeInventory();
            return;
        }

        if(itemData.has(BasicKeys.BUTTON_TYPE_PREV, PersistentDataType.BOOLEAN)) {

            Menu prev = MenuManager.getPlayerMenu(p).getPrevMenu();

            if(prev != null) prev.open(p);

            return;
        }

        if(isValidSlot(rawSlot, menu)) {
            menu.onClick(p, rawSlot, e.getAction());
        }
    }

    private boolean isValidSlot(int rawSlot, Menu menu) {
        return rawSlot >= 0 && rawSlot <= menu.getSize() - 1;
    }

    private boolean isInventoryClick(int slot, int rawSlot, Menu menu) {
        if(!isValidSlot(rawSlot, menu)) return false;
        if(slot < 0 || slot > 35) return false;
        if(rawSlot == slot) return false;
        return true;
    }
}
