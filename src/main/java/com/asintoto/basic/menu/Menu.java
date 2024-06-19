package com.asintoto.basic.menu;

import com.asintoto.basic.enums.ButtonType;
import com.asintoto.basic.utils.BasicKeys;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;


public class Menu {

    private Inventory inventory;

    private int size;
    private String title;
    private Menu prevMenu;

    public Menu(int size, String title) {
        this.title = title;
        this.size = size;

        if(size <= 0 || size > 54 || size % 9 != 0) size = 54;

        this.inventory = Bukkit.createInventory(null, this.size, this.title);

        this.prevMenu = null;

    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Menu getPrevMenu() {
        return prevMenu;
    }

    public void setPrevMenu(Menu prevMenu) {
        this.prevMenu = prevMenu;
    }

    public void open(Player p) {
        p.openInventory(inventory);
        p.getPersistentDataContainer().set(BasicKeys.BASIC_MENU_OPEN, PersistentDataType.BOOLEAN, true);
    }

    public void addButton(Button button, int slot) {
        if(button.getButtonType() == ButtonType.PREVIUS) {
            ItemMeta meta = button.getItem().getItemMeta();

            button.getItem().getItemMeta().getPersistentDataContainer().set(BasicKeys.BASIC_MENU_HOLDER,
                    MenuDataType.getInstance(), prevMenu);

            button.getItem().setItemMeta(meta);
        }
        this.inventory.setItem(slot, button.getItem());
    }

}
