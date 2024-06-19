package com.asintoto.basic.menu;

import com.asintoto.basic.enums.ButtonType;
import com.asintoto.basic.items.ItemCreator;
import com.asintoto.basic.utils.BasicKeys;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class Button {
    private ItemStack item;
    private ButtonType buttonType;

    public Button(ItemStack item, ButtonType buttonType) {
        this.buttonType = buttonType;
        this.item = item;

        if(this.buttonType == ButtonType.CLOSE) {
            this.item.getItemMeta().getPersistentDataContainer().set(BasicKeys.BUTTON_TYPE_CLOSE,
                    PersistentDataType.BOOLEAN, true);
        } else if (this.buttonType == ButtonType.UNLOCKED) {
            this.item.getItemMeta().getPersistentDataContainer().set(BasicKeys.BUTTON_TYPE_UNLOCKED,
                    PersistentDataType.BOOLEAN, true);
        }
    }

    public Button(ItemCreator item, ButtonType buttonType) {
        this(item.make(), buttonType);
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
