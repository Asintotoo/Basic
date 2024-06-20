package com.asintoto.basic.menu;

import com.asintoto.basic.enums.ButtonType;
import com.asintoto.basic.items.ItemCreator;
import com.asintoto.basic.utils.BasicKeys;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class Button {
    private ItemStack item;
    private ButtonType buttonType;

    public Button(ItemStack item, ButtonType buttonType) {
        this.buttonType = buttonType;
        this.item = item;

        if(this.buttonType == ButtonType.CLOSE) {
            ItemMeta meta = this.item.getItemMeta();

            meta.getPersistentDataContainer().set(BasicKeys.BUTTON_TYPE_CLOSE,
                    PersistentDataType.BOOLEAN, true);

            this.item.setItemMeta(meta);

        } else if (this.buttonType == ButtonType.UNLOCKED) {
            ItemMeta meta = this.item.getItemMeta();

            meta.getPersistentDataContainer().set(BasicKeys.BUTTON_TYPE_UNLOCKED,
                    PersistentDataType.BOOLEAN, true);

            this.item.setItemMeta(meta);
        } else if (this.buttonType == ButtonType.PREVIUS) {
            ItemMeta meta = this.item.getItemMeta();

            meta.getPersistentDataContainer().set(BasicKeys.BUTTON_TYPE_PREV,
                    PersistentDataType.BOOLEAN, true);

            this.item.setItemMeta(meta);
        }
    }

    public Button(ItemCreator item, ButtonType buttonType) {
        this(item.make(), buttonType);
    }

    public Button(Material material) {
        this(new ItemStack(material));
    }

    public Button(Material material, ButtonType type) {
        this(new ItemStack(material), type);
    }

    public Button(ItemStack item) {
        this(item, ButtonType.LOCKED);
    }

    public ItemStack getItem() {
        return item;
    }

    public ButtonType getButtonType() {
        return buttonType;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public void setButtonType(ButtonType buttonType) {
        this.buttonType = buttonType;
    }
}
