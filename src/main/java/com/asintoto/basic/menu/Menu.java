package com.asintoto.basic.menu;

import com.asintoto.basic.enums.ButtonType;
import com.asintoto.basic.utils.BasicKeys;
import com.asintoto.colorlib.ColorLib;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Menu {

    private Inventory inventory;

    private int size;
    private String title;
    private Menu prevMenu;
    private Map<Integer, Button> buttonSlotMap;

    public Menu(int size, String title) {
        this.title = title;
        this.size = size;

        this.buttonSlotMap = new HashMap<>();

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
        //p.getPersistentDataContainer().set(BasicKeys.BASIC_MENU_OPEN, PersistentDataType.BOOLEAN, true);
        MenuManager.addPlayer(p, this);
    }

    public int getSize() {
        return size;
    }

    public String getTitle() {
        return title;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void addButton(Button button, int slot) {
        /*if(button.getButtonType() == ButtonType.PREVIUS) {
            try {
                ItemMeta meta = button.getItem().getItemMeta();

                meta.getPersistentDataContainer().set(BasicKeys.BASIC_MENU_HOLDER,
                        MenuDataType.getInstance(), getPrevMenu());

                button.getItem().setItemMeta(meta);
            } catch (Exception e) {
                Bukkit.getServer().getConsoleSender().sendMessage(ColorLib
                        .setColors("&cThis feature is still work in progress, it might not work. If you see this message it means something went wrong. DO NOT REPORT THIS ERROR"));
            }
        }*/
        this.inventory.setItem(slot, button.getItem());
        this.buttonSlotMap.put(slot, button);
    }

    public Button getButtonAtSlot(int slot) {
        if(buttonSlotMap.containsKey(slot)) {
            return buttonSlotMap.get(slot);
        }

        return null;
    }

    public void onClick(Player p, int slot) {

    }

}
