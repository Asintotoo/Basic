package com.asintoto.basic;

import com.asintoto.basic.commands.BasicCommand;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
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


    protected <T extends BasicCommand> void registerCommand(String cmd, T commandClass) {
        Basic.registerCommand(cmd, commandClass);
    }

    protected <T extends BasicCommand> void registerCommand(T commandClass) {
        Basic.registerCommand(commandClass);
    }

    protected <T extends Listener> void registerListener(T listenerClass) {
        Basic.registerListener(listenerClass);
    }

    protected <T extends Listener> void registerListener(T listenerClass, JavaPlugin pl) {
        Basic.registerListener(listenerClass, pl);
    }

    protected <T extends CommandExecutor> void registerCommand(String cmd, T commandClass) {
        Basic.registerCommand(cmd, commandClass);
    }

    public <T extends TabCompleter> void registerTabCompleter(String cmd, T commandClass) {
        Basic.registerTabCompleter(cmd, commandClass);
    }
}
