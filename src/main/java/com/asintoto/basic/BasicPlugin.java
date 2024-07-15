package com.asintoto.basic;

import org.bukkit.plugin.java.JavaPlugin;

public abstract class BasicPlugin extends JavaPlugin {
    @Override
    public final void onLoad() {
        onPluginLoad();
    }

    @Override
    public final void onEnable() {
        Basic.init(this);
        onPluginEnable();
    }

    @Override
    public final void onDisable() {
        Basic.terminate();
        onPluginDisable();
    }

    protected abstract void onPluginLoad();
    protected abstract void onPluginEnable();
    protected abstract void onPluginDisable();
}
