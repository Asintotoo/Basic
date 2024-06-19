package com.asintoto.basic.menu;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;

public class MenuDataType implements PersistentDataType<Menu, Menu> {

    @Override
    public Class<Menu> getPrimitiveType() {
        return Menu.class;
    }

    @Override
    public Class<Menu> getComplexType() {
        return Menu.class;
    }

    @Override
    public Menu toPrimitive(Menu t, PersistentDataAdapterContext persistentDataAdapterContext) {
        return t;
    }

    @Override
    public Menu fromPrimitive(Menu t, PersistentDataAdapterContext persistentDataAdapterContext) {
        return t;
    }

    private static final MenuDataType INSTANCE = new MenuDataType();

    private MenuDataType() {
    }

    public static MenuDataType getInstance() {
        return MenuDataType.INSTANCE;
    }
}
