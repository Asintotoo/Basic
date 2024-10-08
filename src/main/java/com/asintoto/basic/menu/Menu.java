package com.asintoto.basic.menu;

import com.asintoto.basic.enums.ButtonType;
import com.asintoto.basic.utils.BasicKeys;
import com.asintoto.colorlib.ColorLib;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public abstract class Menu {

    private Inventory inventory;

    private int size;
    private String title;
    private Menu prevMenu;
    private Map<Integer, Button> buttonSlotMap;
    private boolean playerInventoryProtection;

    public Menu(int size, String title) {
        this.title = ColorLib.setColors(title);
        this.size = size;

        this.buttonSlotMap = new HashMap<>();

        if(size <= 0 || size > 54 || size % 9 != 0) size = 54;

        this.inventory = Bukkit.createInventory(null, this.size, this.title);

        this.prevMenu = null;

        this.playerInventoryProtection = true;

    }

    public boolean hasPlayerInventoryProtection() {
        return playerInventoryProtection;
    }

    public void setPlayerInventoryProtection(boolean playerInventoryProtection) {
        this.playerInventoryProtection = playerInventoryProtection;
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
        this.title = ColorLib.setColors(title);
    }

    @Deprecated
    public void addButton(Button button, int slot) {
        setButton(button, slot);
    }

    public void setButton(Button button, int slot) {
        this.inventory.setItem(slot, button.getItem());
        this.buttonSlotMap.put(slot, button);
    }

    public Button getButtonAtSlot(int slot) {
        if(buttonSlotMap.containsKey(slot)) {
            return buttonSlotMap.get(slot);
        }

        return null;
    }

    public ItemStack getRawItem(int slot) {
        ItemStack temp = inventory.getItem(slot);
        if(temp != null) return new ItemStack(temp.getType());
        return null;
    }

    public void onClick(Player p, int slot, InventoryAction action) {

    }

    public void onClose(Player p) {

    }

}
