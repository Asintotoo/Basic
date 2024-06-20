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

        /*if(p.getPersistentDataContainer().has(BasicKeys.BASIC_MENU_OPEN, PersistentDataType.BOOLEAN)
                && !itemData.has(BasicKeys.BUTTON_TYPE_UNLOCKED, PersistentDataType.BOOLEAN)) {
            e.setCancelled(true);
        }*/

        boolean isUnocked = itemData.has(BasicKeys.BUTTON_TYPE_UNLOCKED, PersistentDataType.BOOLEAN);

        if(!isUnocked) {
            e.setCancelled(true);
        }

        if(itemData.has(BasicKeys.BUTTON_TYPE_CLOSE, PersistentDataType.BOOLEAN)) {
            p.closeInventory();
            return;
        }

        if(itemData.has(BasicKeys.BUTTON_TYPE_PREV, PersistentDataType.BOOLEAN)) {
            /*Menu prev = (Menu) itemData.get(BasicKeys.BASIC_MENU_HOLDER, MenuDataType.getInstance());
            if(prev != null) prev.open(p);*/

            Menu prev = MenuManager.getPlayerMenu(p).getPrevMenu();

            if(prev != null) prev.open(p);

            return;
        }

        int slot = e.getRawSlot();

        Menu menu = MenuManager.getPlayerMenu(p);

        if(slot >= 0 && slot <= menu.getSize() - 1) {
            menu.onClick(p, slot);
        }
    }
}
