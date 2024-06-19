package com.asintoto.basic.menu;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;

public class MenuDataType <T extends Menu> implements PersistentDataType<T, T> {

    @Override
    public Class<T> getPrimitiveType() {
        return null;
    }

    @Override
    public Class<T> getComplexType() {
        return null;
    }

    @Override
    public T toPrimitive(T t, PersistentDataAdapterContext persistentDataAdapterContext) {
        return null;
    }

    @Override
    public T fromPrimitive(T t, PersistentDataAdapterContext persistentDataAdapterContext) {
        return null;
    }

    private static final MenuDataType INSTANCE = new MenuDataType();

    private MenuDataType() {
    }

    public static MenuDataType getInstance() {
        return MenuDataType.INSTANCE;
    }
}
