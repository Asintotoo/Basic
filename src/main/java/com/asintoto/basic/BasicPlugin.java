package com.asintoto.basic;

import com.asintoto.basic.commands.BasicCommand;
import org.bukkit.event.Listener;
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

    public void onPluginLoad() {}
    public void onPluginEnable() {}
    public void onPluginDisable() {}

    protected <T extends BasicCommand> void registerCommmand(String cmd, T commandClass) {
        Basic.registerCommmand(cmd, commandClass);
    }

    protected <T extends Listener> void registerListener(T listenerClass) {
        Basic.registerListener(listenerClass);
    }
}
